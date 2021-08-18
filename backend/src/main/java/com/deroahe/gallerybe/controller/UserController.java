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
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/users")
public class UserController {

    private UserServiceImpl userService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping
    public Iterable<User> getAllUsers() {
        logger.info("Get at users");
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        User user = userService.findUserById(id);

        if (user != null) {
            return user;
        }
        return null;
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity.BodyBuilder updateUser(@PathVariable("id") Long id, @RequestBody User user) {
        User oldUser = userService.findUserById(id);
        userService.update(id, user);
        boolean OK = true;

        if (!StringUtils.equals(user.getUsername(), oldUser.getUsername())) {
            OK = false;
        }

        if (!StringUtils.equals(user.getEmail(), oldUser.getEmail())) {
            OK = false;
        }
        if (OK) {
            logger.info("ok");
            return ResponseEntity.status(HttpStatus.OK);
        }

        logger.info("Not ok");
        return ResponseEntity.status(HttpStatus.NOT_MODIFIED);
    }
}