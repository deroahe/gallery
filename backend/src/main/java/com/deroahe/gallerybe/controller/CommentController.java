package com.deroahe.gallerybe.controller;

import com.deroahe.gallerybe.model.Comment;
import com.deroahe.gallerybe.payload.request.CommentRequest;
import com.deroahe.gallerybe.payload.response.MessageResponse;
import com.deroahe.gallerybe.service.impl.CommentServiceImpl;
import com.deroahe.gallerybe.service.impl.ImageServiceImpl;
import com.deroahe.gallerybe.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private CommentServiceImpl commentService;
    private UserServiceImpl userService;
    private ImageServiceImpl imageService;

    @Autowired
    public CommentController(CommentServiceImpl commentService, UserServiceImpl userService, ImageServiceImpl imageService) {
        this.commentService = commentService;
        this.userService = userService;
        this.imageService = imageService;
    }

    @GetMapping("{id}")
    public Comment findCommentById(@PathVariable int id) {
        return commentService.findCommentById(id);
    }

    @GetMapping("/user-comments/{userId}")
    public List<Comment> findCommentsByUser(@PathVariable int userId) {
        return commentService.findCommentsByUser(userService.findUserById(userId));
    }

    @GetMapping("/image-comments/{imageId}")
    public List<Comment> findCommentsByImage(@PathVariable int imageId) {
        return commentService.findCommentsByImage(imageService.findImageById(imageId));
    }

    @GetMapping
    public List<Comment> findAllComments() {
        return commentService.findAllComments();
    }

    @PostMapping
    public ResponseEntity<?> saveComment(@RequestBody CommentRequest commentRequest) {
        Comment comment = commentService.saveComment(commentRequest.getCommentString(), commentRequest.getUserId(), commentRequest.getImageId());

        if(comment == null) {
            return ResponseEntity.badRequest().body(new MessageResponse("Comment not saved"));
        }

        return ResponseEntity.ok().body(new MessageResponse("Comment saved successfully"));
    }
//
//    @PostMapping
//    public ResponseEntity<?> saveComment(@RequestBody Comment comment) {
//        if (commentService.saveComment(comment) == null) {
//            return ResponseEntity.badRequest().body(new MessageResponse("Comment not saved"));
//        }
//
//        return ResponseEntity.ok().body(new MessageResponse("Comment saved successfully"));
//    }

    @PostMapping("/update")
    public ResponseEntity<?> updateComment(@RequestBody Comment comment) {
        if (commentService.updateComment(comment) != null) {
            return ResponseEntity.ok().body(new MessageResponse("Comment updated successfully"));
        }

        return ResponseEntity.badRequest().body(new MessageResponse("Comment not updated"));
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<?> deleteCommentById(@PathVariable int id) {
        if (!commentService.deleteCommentById(id)) {
            return ResponseEntity.badRequest().body(new MessageResponse("Comment id not found"));
        }

        return ResponseEntity.ok().body(new MessageResponse("Comment deleted successfully"));
    }

    @PostMapping("/delete-user-comments/{userId}")
    public ResponseEntity<?> deleteCommentsByUser(@PathVariable int userId) {
        commentService.findCommentsByUser(userService.findUserById(userId));
        return ResponseEntity.ok().body(new MessageResponse("All comments by user id: " + userId + " have been deleted"));
    }

    @PostMapping("/delete-image-comments/{imageId}")
    public ResponseEntity<?> deleteCommentsByImage(@PathVariable int imageId) {
        commentService.deleteCommentByImage(imageService.findImageById(imageId));
        return ResponseEntity.ok().body(new MessageResponse("All comments by image id: " + imageId + " have been deleted"));
    }
}
