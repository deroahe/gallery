package com.deroahe.gallerybe.repository;

import com.deroahe.gallerybe.model.Comment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Integer> {

    Comment findByCommentId(int id);

    List<Comment> findAll();

//    List<Comment> findAllByCommentUser(User user);

    void deleteByCommentId(int id);
}
