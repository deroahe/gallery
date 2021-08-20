package com.deroahe.gallerybe.service.impl;

import com.deroahe.gallerybe.model.Image;
import com.deroahe.gallerybe.model.User;
import com.deroahe.gallerybe.repository.UserRepository;
import com.deroahe.gallerybe.service.EncryptionService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl {

    private UserRepository userRepository;
    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Resource(name = "encryptionService")
    private EncryptionService encryptionService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUserById(int id) {
        return userRepository.findByUserId(id);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User saveUser(User user) {
        if (userRepository.existsByUserUsername(user.getUserUsername())) {
            logger.error("Username already in database");
            return null;
        }
        if (userRepository.existsByUserEmail(user.getUserEmail())) {
            logger.error("Email already in database");
            return null;
        }
        return userRepository.save(user);
    }

    public void saveAllUsers(List<User> users) {
        for (User user : users) {
            saveUser(user);
        }
    }

    public User updateUser(User user) {
        if (!userRepository.existsById(user.getUserId())) {
            logger.error("User id not in DB");
            return null;
        }

        return userRepository.save(user);
    }

    public boolean deleteUserById(int id) {
        if (!userRepository.existsById(id)) {
            logger.error("User id not in DB");
            return false;
        }
        userRepository.deleteByUserId(id);
        return true;
    }

    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

    public List<Image> getAllUserImages(int id) {
        User user = findUserById(id);
        return user.getUserImages();
    }
}
