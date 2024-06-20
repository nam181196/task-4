package com.example.demo2;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(user -> ResponseEntity.ok().body(user))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody User user) {
        userRepository.save(user);
        return new ResponseEntity<>(new ApiResponse(true, "User created successfully", 201), HttpStatus.CREATED);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@Valid @RequestBody User user) {
        Optional<User> existingUser = userRepository.findById(user.getId());
        if (existingUser.isPresent()) {
            return new ResponseEntity<>(new ApiResponse(false, "User already exists", 409), HttpStatus.CONFLICT);
        }
        userRepository.save(user);
        return new ResponseEntity<>(new ApiResponse(true, "User added successfully", 201), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            return new ResponseEntity<>(new ApiResponse(false, "User not found", 404), HttpStatus.NOT_FOUND);
        }
        userRepository.delete(user.get());
        return new ResponseEntity<>(new ApiResponse(true, "User deleted successfully", 200), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@Valid @RequestBody User user) {
        Optional<User> existingUser = userRepository.findById(user.getId());
        if (!existingUser.isPresent()) {
            return new ResponseEntity<>(new ApiResponse(false, "User not found", 404), HttpStatus.NOT_FOUND);
        }
        userRepository.save(user);
        return new ResponseEntity<>(new ApiResponse(true, "User updated successfully", 200), HttpStatus.OK);
    }

    @GetMapping("/findByName/{name}")
    public ResponseEntity<?> findByName(@PathVariable String name) {
        List<User> users = userRepository.findByName(name);
        if (users.isEmpty()) {
            return new ResponseEntity<>(new ApiResponse(false, "User not found", 404), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new ApiResponse(true, "User information", 200, users), HttpStatus.OK);
    }

    @GetMapping("/findByAddress/{address}")
    public ResponseEntity<?> findByAddress(@PathVariable String address) {
        List<User> users = userRepository.findByAddress(address);
        if (users.isEmpty()) {
            return new ResponseEntity<>(new ApiResponse(false, "User not found", 404), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new ApiResponse(true, "User information", 200, users), HttpStatus.OK);
    }

    @GetMapping("/sortByName")
    public ResponseEntity<?> sortByName() {
        List<User> users = userRepository.findAll();
        users.sort(Comparator.comparing(User::getName));
        return new ResponseEntity<>(new ApiResponse(true, "Sorted user list", 200, users), HttpStatus.OK);
    }
}
