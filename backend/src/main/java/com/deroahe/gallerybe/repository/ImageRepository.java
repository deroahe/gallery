package com.deroahe.gallerybe.repository;

import com.deroahe.gallerybe.model.Image;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends CrudRepository<Image, Integer> {

    Image findByImageId(int id);

    Image findByImageUrl(String url);

    List<Image> findAll();

    Boolean existsByImageUrl(String url);

    void deleteByImageId(int id);
}
