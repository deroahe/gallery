package com.deroahe.gallerybe.repository;

import com.deroahe.gallerybe.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    User findByUserId(int id);

    Optional<User> findByUserUsername(String username);

    List<User> findAll();

    Boolean existsByUserUsername(String username);

    Boolean existsByUserEmail(String email);

    void deleteByUserId(int id);
}
