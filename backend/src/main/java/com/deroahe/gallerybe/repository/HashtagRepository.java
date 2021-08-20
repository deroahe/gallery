package com.deroahe.gallerybe.repository;

import com.deroahe.gallerybe.model.Hashtag;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface HashtagRepository extends CrudRepository<Hashtag, Integer> {

    Boolean existsByHashtagName(String name);

    Boolean existsByHashtagId(int id);

    Hashtag findByHashtagId(int id);

    Hashtag findByHashtagName(String name);

    List<Hashtag> findAll();

    void deleteByHashtagId(int id);

    void deleteByHashtagName(String name);
}
