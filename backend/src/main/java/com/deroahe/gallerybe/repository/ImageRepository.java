package com.deroahe.gallerybe.repository;

import com.deroahe.gallerybe.model.Image;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends CrudRepository<Image, String> {

    Image findImageByImageId(String id);

    Image findImageByImageUrl(String url);

    List<Image> findAll();
}
