package com.deroahe.gallerybe.controller;

import com.deroahe.gallerybe.model.Image;
import com.deroahe.gallerybe.service.impl.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/images")
public class ImageController {

    private ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping(value = "/")
    public Collection<Image> getAll() {
        return imageService.getAll();
    }

    @GetMapping(value = "/urls")
    public List<String> getAllUrls() {
        return imageService.getAllUrls();
    }

    @GetMapping("/{id}")
    public Image getById(@PathVariable String id) {
        return imageService.getById(id);
    }

//    @GetMapping("/{url}")
//    public Optional<Image> getByUrl(@PathVariable String url) {
//        return imageService.getByUrl(url);
//    }

    @PostMapping
    public ResponseEntity<Image> create(@RequestBody Image image) throws URISyntaxException {
        Image savedImage = imageService.save(image);
        System.out.println("IN POST IMAGE" + savedImage.getImageUrl());
        return ResponseEntity.created(new URI("/" + savedImage.getImageId())).body(savedImage);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Image> deleteById(@PathVariable String id) {
        imageService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/")
    public ResponseEntity<Image> deleteAll() {
        imageService.deleteAll();
        return ResponseEntity.ok().build();
    }
}
