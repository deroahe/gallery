package com.deroahe.gallerybe.service.impl;

import com.deroahe.gallerybe.model.Image;
import com.deroahe.gallerybe.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImageService {

    ImageRepository imageRepository;

    @Autowired
    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public Image save(Image image) {
        return imageRepository.save(image);
    }

    public List<Image> getAll() {
        return imageRepository.findAll();
    }

    public List<String> getAllUrls() {
        List<Image> allImages = getAll();
        List<String> urls = new ArrayList<>();
        for (Image image : allImages) {
            urls.add(image.getImageUrl());
        }

        return urls;
    }

    public Image getById(String id) {
        return imageRepository.findImageByImageId(id);
    }

    public void deleteById(String id) {
        imageRepository.deleteById(id);
    }

    public Image getByUrl(String url) {
        return imageRepository.findImageByImageUrl(url);
    }

    public void deleteAll() {
        imageRepository.deleteAll();
    }

}
