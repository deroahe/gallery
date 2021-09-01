package com.deroahe.gallerybe.service.impl;

import com.deroahe.gallerybe.model.Comment;
import com.deroahe.gallerybe.model.Image;
import com.deroahe.gallerybe.model.User;
import com.deroahe.gallerybe.repository.CommentRepository;
import com.deroahe.gallerybe.repository.ImageRepository;
import com.deroahe.gallerybe.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl {

    CommentRepository commentRepository;
    UserRepository userRepository;
    ImageRepository imageRepository;

    Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);

    @Autowired CommentServiceImpl(CommentRepository commentRepository, UserRepository userRepository, ImageRepository imageRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.imageRepository = imageRepository;
    }

    public Comment findCommentById(int id) {
        return commentRepository.findByCommentId(id);
    }

    public List<Comment> findCommentsByUser(User user) {
        return commentRepository.findAllByCommentUser(user);
    }

    public List<Comment> findCommentsByImage(Image image) {
        return commentRepository.findAllByCommentImage(image);
    }

    public List<Comment> findAllComments() {
        return commentRepository.findAll();
    }

    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public Comment saveComment(String commentString, int userId, int imageId) {
        User user = userRepository.findByUserId(userId);
        Image image = imageRepository.findByImageId(imageId);
        if (user != null && image != null) {
            Comment comment = new Comment(commentString, user, image);
            return commentRepository.save(comment);
        }
        logger.error("User id or image id not in DB: userId: " + userId + " imageId: " + imageId);
        return null;
    }

    public void saveAllComments(List<Comment> comments) {
        for (Comment comment : comments) {
            saveComment(comment);
        }
    }

    public Comment updateComment(Comment comment) {
        if (!commentRepository.existsById(comment.getCommentId())) {
            logger.error("Comment id not in DB");
            return null;
        }
        return commentRepository.save(comment);
    }

    public boolean deleteCommentById(int id) {
        Comment comment = findCommentById(id);
        if (comment == null) {
            logger.error("Comment id not in DB");
            return false;
        }
        commentRepository.deleteByCommentId(id);
        return true;
    }

    public void deleteCommentByUser(User user) {
        commentRepository.deleteByCommentUser(user);
    }

    public void deleteCommentByImage(Image image) {
        commentRepository.findAllByCommentImage(image);
    }
}
