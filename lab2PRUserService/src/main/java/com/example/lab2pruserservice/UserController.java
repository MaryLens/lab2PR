package com.example.lab2pruserservice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    private final  String USER_NOT_FOUND_MESSAGE = "User not found";
    private Map<Integer, String> users = new HashMap<>();

    @GetMapping("/{id}")
    public ResponseEntity<String> getUser(@PathVariable int id) {
        if (users.containsKey(id)) {
            return ResponseEntity.ok(users.get(id));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(USER_NOT_FOUND_MESSAGE);
        }
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestParam String name) {
        int id = users.size() + 1;
        users.put(id, name);
        return ResponseEntity.status(HttpStatus.CREATED).body("User created with ID: " + id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable int id, @RequestParam String name) {
        if (users.containsKey(id)) {
            users.put(id, name);
            return ResponseEntity.ok("User updated");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(USER_NOT_FOUND_MESSAGE);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        if (users.containsKey(id)) {
            users.remove(id);
            return ResponseEntity.ok("User deleted");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(USER_NOT_FOUND_MESSAGE);
        }
    }
}
