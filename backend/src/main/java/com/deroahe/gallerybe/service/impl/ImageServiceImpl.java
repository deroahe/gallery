package com.deroahe.gallerybe.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.deroahe.gallerybe.model.Image;
import com.deroahe.gallerybe.model.UploadForm;
import com.deroahe.gallerybe.repository.ImageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ImageServiceImpl {

    private ImageRepository imageRepository;
    private Cloudinary cloudinary;
    private Map<String, String> params;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;

        cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "do70ia20i",
                "api_key", "472214227375812",
                "api_secret", "JCtzbo3LOkTTNo4BTYNrLXr4Ku8"));

        params = ObjectUtils.asMap(
                "overwrite", true
        );
    }

    public Image save(Long userId, MultipartFile file) {
        try {
            params.put("public_id", "test_be/" + file.getOriginalFilename());
            Path path = write(file, Paths.get("./src/main/resources"));
            Map uploadResult = cloudinary.uploader().upload(path.toFile(), params);
            logger.info("Saved image to Cloudinary with public_id: " + uploadResult.get("public_id").toString());

            Image image = new Image();
            image.setImageUploadedBy(userId);
            image.setImageUrl(uploadResult.get("url").toString());
            return imageRepository.save(image);
        } catch (IOException e) {
            logger.error("Couldn't upload image to Cloudinary");
            e.printStackTrace();

            return null;
        }
    }

    public Path write(MultipartFile file, Path dir) {
        Path filepath = Paths.get(dir.toString(), file.getOriginalFilename());

        try (OutputStream os = Files.newOutputStream(filepath)) {
            os.write(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return filepath;
    }

    public List<Image> getAll() {
        return imageRepository.findAll();
    }

    public List<String> getAllUrls() {
        List<Image> allImages = getAll();
        List<String> urls = new ArrayList<>();
        for (Image image : allImages) {
            if (image.getImageUrl() != null) {
                logger.info("In get all urls");
                urls.add(image.getImageUrl());
            }

        }

        return urls;
    }

    public Image getById(int id) {
        return imageRepository.findImageByImageId(id);
    }

    public void deleteById(int id) {
        imageRepository.deleteImageByImageId(id);
    }

    public Image getByUrl(String url) {
        return imageRepository.findImageByImageUrl(url);
    }

    public void deleteAll() {
        imageRepository.deleteAll();
    }

}
