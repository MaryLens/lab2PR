package com.example.lab2prorderservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private Map<Integer, String> orders = new HashMap<>();
    private final RestTemplate restTemplate;

    public OrderController(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getOrder(@PathVariable int id) {
        if (orders.containsKey(id)) {
            return ResponseEntity.ok(orders.get(id));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
        }
    }

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestParam int userId, @RequestParam String product) {
        String userServiceUrl = "http://user-service:8080/users/";
        ResponseEntity<String> response = restTemplate.getForEntity(userServiceUrl + userId, String.class);

        System.out.println("User service URL: " + userServiceUrl);
        if (response.getStatusCode() == HttpStatus.OK) {
            int id = orders.size() + 1;
            orders.put(id, "Order for user " + userId + ": " + product);
            return ResponseEntity.status(HttpStatus.CREATED).body("Order created with ID: " + id);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User does not exist");
        }
    }

}

