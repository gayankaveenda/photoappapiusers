package com.developer.photoapp.api.users.controller;

import com.developer.photoapp.api.users.model.UserDTO;
import com.developer.photoapp.api.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private Environment environment;
    @Autowired
    private UserService userService;

    @GetMapping("/status/check")
    public String status() {
        return "UsersController wokring" + environment.getProperty("local" +
                ".server.port");
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable String userId) {
        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(userService.create(userDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable String userId, @Valid @RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(userService.update(userId, userDTO), HttpStatus.ACCEPTED);
    }
}
