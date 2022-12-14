package com.deroahe.gallerybe.controller;

import com.deroahe.gallerybe.model.Image;
import com.deroahe.gallerybe.model.User;
import com.deroahe.gallerybe.payload.request.HashtagAddRequest;
import com.deroahe.gallerybe.payload.response.MessageResponse;
import com.deroahe.gallerybe.service.impl.ImageServiceImpl;
import com.deroahe.gallerybe.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
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
    private UserServiceImpl userService;

    @Autowired
    public ImageController(ImageServiceImpl imageService, UserServiceImpl userService) {
        this.imageService = imageService;
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findByImageId(@PathVariable int id) {
        Image image = imageService.findImageById(id);
        if (image != null) {
            return ResponseEntity.ok().body(image);
        }
        return ResponseEntity.badRequest().body(new MessageResponse("Image id not in DB"));
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

    @GetMapping("/images-by-category/{category}")
    public ResponseEntity<?> findImagesByCategory(@PathVariable String category) {
        if (StringUtils.isEmpty(category)) {
            return ResponseEntity.badRequest().body(new MessageResponse("Provide a category"));
        }
        List<Image> images = imageService.findImagesByCategory(category);
        if (images != null) {
            return ResponseEntity.ok().body(images);
        }
        return ResponseEntity.badRequest().body(new MessageResponse("Category not found"));
    }

    @GetMapping("/images-by-user/{userId}")
    public ResponseEntity<?> findImagesByUserId(@PathVariable int userId) {
        List<Image> images = imageService.findImagesByUserId(userId);
        if (images != null) {
            return ResponseEntity.ok().body(images);
        }
        return ResponseEntity.badRequest().body(new MessageResponse("User id not found"));
    }

    @PostMapping(consumes = { "multipart/form-data" })
    public ResponseEntity<?> saveImage(@RequestPart("file") MultipartFile multipartFile, @RequestPart("userId") int userId) throws URISyntaxException {
        User user = userService.findUserById(userId);
        if (user == null) {
            return ResponseEntity.badRequest().body(new MessageResponse("User id not found"));
        }
        Image savedImage = imageService.saveAndUpload(user, multipartFile);

        return ResponseEntity.created(new URI("/" + savedImage.getImageId())).body(savedImage);
    }

    @PostMapping("/save-image-hashtags")
    public ResponseEntity<?> saveHashtagsToImage(@RequestBody HashtagAddRequest hashtagAddRequest) throws URISyntaxException {
        Image image = imageService.saveHashtagsToImage(hashtagAddRequest.getHashtagsNames(), hashtagAddRequest.getImageId());
        if (image == null) {
            return ResponseEntity.badRequest().body(new MessageResponse("Image id not found"));
        }

        return ResponseEntity.created(new URI("/" + image.getImageId())).body(image);
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateImage(Image image) {
        if (imageService.updateImage(image) != null) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/delete/{id}")
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

    @GetMapping("get-image-user/{id}")
    public ResponseEntity<?> getImageUserByImageId(@PathVariable int id) {
        Image image = imageService.findImageById(id);
        if (image == null) {
            return ResponseEntity.badRequest().body(new MessageResponse("Image not found"));
        }
        return ResponseEntity.ok().body(image.getImageUser());
    }
}
