package com.deroahe.gallerybe.service.impl;

import com.deroahe.gallerybe.model.Comment;
import com.deroahe.gallerybe.model.Image;
import com.deroahe.gallerybe.model.User;
import com.deroahe.gallerybe.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl {

    private UserRepository userRepository;
    private ImageServiceImpl imageService;

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

//    @Resource(name = "encryptionService")
//    private EncryptionService encryptionService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ImageServiceImpl imageService) {
        this.userRepository = userRepository;
        this.imageService = imageService;
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
        User updatedUser = userRepository.findByUserId(user.getUserId());
        if (updatedUser == null) {
            logger.error("User id not in DB");
            return null;
        }

        updatedUser.setUserUsername(user.getUserUsername());
        updatedUser.setUserEmail(user.getUserEmail());

        return userRepository.save(updatedUser);
    }

    public boolean deleteUserById(int id) {
        User user = userRepository.findById(id).get();
        if (user == null) {
            logger.error("User id not in DB");
            return false;
        }
        List<Comment> userComments = user.getUserComments();
        List<Image> userImages = user.getUserImages();
        for (Image image : userImages) {
            imageService.deleteById(image.getImageId());
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
