package com.deroahe.gallerybe.controller;

import com.deroahe.gallerybe.model.Image;
import com.deroahe.gallerybe.model.User;
import com.deroahe.gallerybe.service.impl.ImageServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/images")
public class ImageController {

    private ImageServiceImpl imageService;

    @Autowired
    public ImageController(ImageServiceImpl imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/{id}")
    public Image findByImageId(@PathVariable int id) {
        return imageService.findImageById(id);
    }

    @GetMapping(value = "/urls")
    public List<String> findAllImageUrls() {
        return imageService.findAllImageUrls();
    }

    @GetMapping
    public Iterable<Image> findAllImages() {
        return imageService.findAllImages();
    }

    @PostMapping("/hashtags")
    public List<Image> findAllImagesWithHashtags(@RequestBody List<Integer> hashtagIds) {
        return imageService.findImagesByHashtagIds(hashtagIds);
    }

    @PostMapping(consumes = { "multipart/form-data" })
    public ResponseEntity<Image> saveImage(@RequestPart("file") MultipartFile multipartFile, @RequestPart("user") User user) throws URISyntaxException {
        Image savedImage = imageService.saveAndUpload(user.getUserId(), multipartFile);

        return ResponseEntity.created(new URI("/" + savedImage.getImageId())).body(savedImage);
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateImage(Image image) {
        if (imageService.updateImage(image) != null) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/delete")
    public ResponseEntity<Image> deleteImageById(@PathVariable int id) {
        if (imageService.deleteById(id)) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/delete-all")
    public ResponseEntity<Image> deleteAll() {
        imageService.deleteAll();

        return ResponseEntity.ok().build();
    }
}
