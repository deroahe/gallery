package com.deroahe.gallerybe.service.impl;

import com.deroahe.gallerybe.model.User;
import com.deroahe.gallerybe.repository.UserRepository;
import com.deroahe.gallerybe.service.EncryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl {

    private UserRepository userRepository;

    @Resource(name = "encryptionService")
    private EncryptionService encryptionService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user) {
        user.setPassword(encryptionService.encrypt(user.getPassword()));
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

    public void deleteUserById(String id) {
        userRepository.deleteById(id);
    }

    public User findUserById(String id) {
        return userRepository.findUserById(id);
    }
}
