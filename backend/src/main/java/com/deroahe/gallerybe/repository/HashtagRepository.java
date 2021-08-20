package com.deroahe.gallerybe.repository;

import com.deroahe.gallerybe.model.Hashtag;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface HashtagRepository extends CrudRepository<Hashtag, Integer> {

    Hashtag findByHashtagId(int id);

    Hashtag findByHashtagName(String name);

    List<Hashtag> findAll();

    Boolean existsByHashtagName(String name);

    void deleteByHashtagId(int id);

    void deleteByHashtagName(String name);
}
