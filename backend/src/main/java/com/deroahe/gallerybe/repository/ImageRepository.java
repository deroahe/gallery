package com.deroahe.gallerybe.repository;

import com.deroahe.gallerybe.model.Hashtag;
import com.deroahe.gallerybe.model.Image;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends CrudRepository<Image, Integer> {

    Image findImageByImageId(int id);

    Image findImageByImageUrl(String url);

    List<Image> findAll();

    void deleteImageByImageId(int id);
}
