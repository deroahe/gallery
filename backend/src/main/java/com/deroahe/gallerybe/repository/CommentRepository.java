package com.deroahe.gallerybe.repository;

import com.deroahe.gallerybe.model.Comment;
import com.deroahe.gallerybe.model.Image;
import com.deroahe.gallerybe.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Integer> {

    Comment findByCommentId(int id);

    List<Comment> findAllByCommentUserAndCommentImage(User user, Image image);

    List<Comment> findAllByCommentUser(User user);

    List<Comment> findAllByCommentImage(Image image);

    List<Comment> findAll();

    void deleteByCommentId(int id);

    void deleteByCommentUser(User user);

    void deleteByCommentImage(Image image);
}
