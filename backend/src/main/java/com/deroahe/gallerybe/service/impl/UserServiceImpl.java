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
            logger.info("Username already in database");
            return null;
        }
        if (userRepository.existsByUserEmail(user.getUserEmail())) {
            logger.info("Email already in database");
            return null;
        }
        return userRepository.save(user);
    }

    public void saveAllUsers(List<User> users) {
        for (User user : users) {
            saveUser(user);
        }
    }

    public void deleteUserById(int id) {
        userRepository.deleteByUserId(id);
    }

    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

    public List<Image> getAllUserImages(int id) {
        User user = findUserById(id);
        return user.getUserImages();
    }

    public User update(int id, User user) {
        User newUser = userRepository.findByUserId(id);

        if (StringUtils.isNotEmpty(user.getUserUsername())) {
            newUser.setUserUsername(user.getUserUsername());
        }

        if (StringUtils.isNotEmpty(user.getUserEmail())) {
            newUser.setUserEmail(user.getUserEmail());
        }

        return userRepository.save(newUser);
    }
}
