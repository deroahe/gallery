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
import java.util.Collection;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/images")
public class ImageController {

    private ImageServiceImpl imageService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public ImageController(ImageServiceImpl imageService) {
        this.imageService = imageService;
    }

    @GetMapping
    public Collection<Image> getAll() {
        return imageService.getAll();
    }

    @GetMapping(value = "/urls")
    public List<String> getAllUrls() {
        return imageService.getAllUrls();
    }

    @GetMapping("/{id}")
    public Image getById(@PathVariable int id) {
        return imageService.getById(id);
    }

    @PostMapping(consumes = { "multipart/form-data" })
    public ResponseEntity<Image> create(@RequestPart("file") MultipartFile multipartFile, @RequestPart("user") User user) throws URISyntaxException {
        Image savedImage = imageService.save(user.getId(), multipartFile);
        return ResponseEntity.created(new URI("/" + savedImage.getImageId())).body(savedImage);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Image> deleteById(@PathVariable int id) {
        imageService.deleteById(id);
        logger.info("In delete by id");
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Image> deleteAll() {
        imageService.deleteAll();
        return ResponseEntity.ok().build();
    }
}
