package com.deroahe.gallerybe.repository;

import com.deroahe.gallerybe.model.Hashtag;
import org.springframework.data.repository.CrudRepository;

public interface HashtagRepository extends CrudRepository<Hashtag, Integer> {

    Hashtag findByName(String name);

    Hashtag findById(int id);
}
