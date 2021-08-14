package com.deroahe.gallerybe.repository;

import com.deroahe.gallerybe.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

    List<User> findAll();

    User findUserById(String id);
}
