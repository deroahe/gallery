package com.deroahe.gallerybe.controller;

import com.deroahe.gallerybe.model.User;
import com.deroahe.gallerybe.payload.response.MessageResponse;
import com.deroahe.gallerybe.service.impl.UserServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id) {
        return userService.findUserById(id);
    }

    @GetMapping
    public Iterable<User> getAllUsers() {
        return userService.findAllUsers();
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        if (userService.updateUser(user) != null) {
            return ResponseEntity.ok().body(new MessageResponse("User updated successfully"));
        }

        return ResponseEntity.badRequest().body(new MessageResponse("User not updated"));
    }

    @Transactional
    @PostMapping("/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUserById(@RequestBody User user) {
        if (!userService.deleteUserById(user.getUserId())) {
            return ResponseEntity.badRequest().body(new MessageResponse("User not deleted"));
        }
        return ResponseEntity.ok().body(new MessageResponse("User deleted successfully"));
    }
}