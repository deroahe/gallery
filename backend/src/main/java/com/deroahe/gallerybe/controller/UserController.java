package com.deroahe.gallerybe.controller;

import com.deroahe.gallerybe.model.User;
import com.deroahe.gallerybe.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public Iterable<User> getAllUsers() {
        return userService.getAllUsers();
    }


//    Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
//            "cloud_name", "do70ia20i",
//            "api_key", "472214227375812",
//            "api_secret", "JCtzbo3LOkTTNo4BTYNrLXr4Ku8"));
//
//    Map params = ObjectUtils.asMap(
//            "public_id", "test/image1",
//            "overwrite", true,
//            "notification_url", "",
//            "resource_type", "image"
//    );
//        try {
//        Map uploadResult = cloudinary.uploader().upload("./src/main/resources/images/image1.jpg", params);
//    } catch (IOException e) {
//        e.printStackTrace();
//    }
//        System.out.println(Paths.get(".").toAbsolutePath().normalize().toString());
}