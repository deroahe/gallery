package com.deroahe.gallerybe.service.impl;

import com.deroahe.gallerybe.model.Comment;
import com.deroahe.gallerybe.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl {

    CommentRepository commentRepository;
    UserServiceImpl userService;
    ImageServiceImpl imageService;

    @Autowired CommentServiceImpl(CommentRepository commentRepository, UserServiceImpl userService, ImageServiceImpl imageService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.imageService = imageService;
    }

    public Comment findCommentById(int id) {
        return commentRepository.findByCommentId(id);
    }

    public List<Comment> findAllComments() {
        return commentRepository.findAll();
    }

    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public Comment saveComment(Comment comment, int imageId, int userId) {
        return commentRepository.save(comment);
    }

    public void saveAllComments(List<Comment> comments) {
        for (Comment comment : comments) {
            saveComment(comment);
        }
    }

    public void deleteCommentById(int id) {
        commentRepository.deleteByCommentId(id);
    }
}
