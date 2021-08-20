//package com.deroahe.gallerybe.controller;
//
//import com.deroahe.gallerybe.model.User;
//import com.deroahe.gallerybe.service.EncryptionService;
//import com.deroahe.gallerybe.service.impl.UserServiceImpl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//
//import javax.annotation.Resource;
//import javax.validation.Valid;
//import java.util.Optional;
//
//@Controller
//public class UserUiController {
//
//    private UserServiceImpl userService;
//
//    @Resource
//    private EncryptionService encryptionService;
//
//    @Autowired
//    public UserUiController(UserServiceImpl userService) {
//        this.userService = userService;
//    }
//
//    @GetMapping("/users-ui")
//    public String users(Model model) {
//        model.addAttribute("users", userService.getAllUsers());
//        return "users";
//    }
//
//    @GetMapping("/delete-user/{id}")
//    public String removeUser(@PathVariable("id") int id, Model model) {
//        userService.deleteUserById(id);
//        model.addAttribute("users", userService.getAllUsers());
//        return "users";
//    }
//
////    @PostMapping({"/save-user", "/save-user/{id}"})
////    public String editUser(@PathVariable("id") Optional<String> id, @ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
////        if (bindingResult.hasErrors()) {
////            return "add-edit";
////        }
////
////        if (id.isPresent()) {
////            user.setId(Long.parseLong(id.get()));
////        }
////
////        userService.saveUser(user);
////        return "redirect:/users-ui";
////    }
////
////    @GetMapping(value = {"/edit-add-user/{id}", "/edit-add-user"})
////    public String editUser(@PathVariable("id") Long id, Model model) {
////        User user;
////        if (id != null) {
////            user = userService.findUserById(id);
////            user.setPassword(encryptionService.decrypt(user.getPassword()));
////        } else {
////            user = new User();
////        }
////        model.addAttribute("user", user);
////        return "add-edit";
////    }
//}
