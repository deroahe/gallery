package com.deroahe.gallerybe.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.deroahe.gallerybe.model.Comment;
import com.deroahe.gallerybe.model.Hashtag;
import com.deroahe.gallerybe.model.Image;
import com.deroahe.gallerybe.model.User;
import com.deroahe.gallerybe.repository.HashtagRepository;
import com.deroahe.gallerybe.repository.ImageRepository;
import com.deroahe.gallerybe.repository.UserRepository;
import com.deroahe.gallerybe.util.CategoryUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class ImageServiceImpl {

    private ImageRepository imageRepository;
    private CategoryUtils categoryUtils;
    private HashtagRepository hashtagRepository;
    private UserRepository userRepository;
    private CommentServiceImpl commentService;

    private Cloudinary cloudinary;
    private Map<String, String> params;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public ImageServiceImpl(ImageRepository imageRepository, HashtagRepository hashtagRepository, CategoryUtils categoryUtils, UserRepository userRepository, CommentServiceImpl commentService) {
        this.imageRepository = imageRepository;

        cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "do70ia20i",
                "api_key", "472214227375812",
                "api_secret", "JCtzbo3LOkTTNo4BTYNrLXr4Ku8"));

        params = ObjectUtils.asMap(
                "overwrite", true
        );
        this.hashtagRepository = hashtagRepository;
        this.categoryUtils = categoryUtils;
        this.userRepository = userRepository;
        this.commentService = commentService;
    }

    public Image findImageById(int id) {
        return imageRepository.findByImageId(id);
    }

    public Image findImageByUrl(String url) {
        return imageRepository.findByImageUrl(url);
    }

    public List<Image> findImagesByUserId(int userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user != null) {
            return imageRepository.findImagesByImageUser(user.get());
        }
        logger.error("User id not in DB");
        return null;
    }

    public List<Image> findAllImages() {
        return imageRepository.findAll();
    }

    public List<String> findAllImageUrls() {
        List<Image> allImages = findAllImages();
        List<String> urls = new ArrayList<>();
        for (Image image : allImages) {
            if (image.getImageUrl() != null) {
                urls.add(image.getImageUrl());
            }
        }

        return urls;
    }

    public Image saveImage(Image image) {
        if (imageRepository.existsByImageUrl(image.getImageUrl())) {
            logger.error("Image URL already in database");
            return null;
        }
        return imageRepository.save(image);
    }

    public void saveAllImages(List<Image> images) {
        for (Image image : images) {
            saveImage(image);
        }
    }

    public Image saveAndUpload(User user, MultipartFile file) {
        try {
            params.put("public_id", "test_be/" + file.getOriginalFilename());
            Path path = writeToFile(file, Paths.get("."));
            Map<?,?> uploadResult = cloudinary.uploader().upload(path.toFile(), params);
            logger.info("Saved image to Cloudinary with public_id: " + uploadResult.get("public_id").toString());

            Image image = new Image();
            image.setImageUrl(uploadResult.get("url").toString());
            image.setImageUser(user);
            File tempFile = new File(String.valueOf(path.toFile()));
            boolean deleted = tempFile.delete();
            if (!deleted) {
                logger.error("Temporary file couldn't be deleted. Path: " + path.toString());
            }
            return imageRepository.save(image);
        } catch (IOException e) {
            logger.error("Couldn't upload image to Cloudinary");
            e.printStackTrace();

            return null;
        }
    }

    public Image saveHashtagsToImage(String hashtagsNames, int imageId) {
        Image image = imageRepository.findByImageId(imageId);
        if (hashtagsNames == null || image == null) {
            logger.error("Hashtags names missing or image id not found in DB");
            return null;
        }
        List<String> hashtagsNamesList = Arrays.asList(hashtagsNames.split("\\s*,\\s*"));
        Set<Hashtag> imageHashtags = new HashSet<>(image.getImageHashtags());
        for (String hashtagName : hashtagsNamesList) {
            Hashtag hashtagToAdd = hashtagRepository.findByHashtagName(hashtagName);
            if (hashtagToAdd == null) {
                hashtagToAdd = new Hashtag(hashtagName);
                hashtagRepository.save(hashtagToAdd);
            }
            imageHashtags.add(hashtagToAdd);
        }
        image.setImageHashtags(new ArrayList<>(imageHashtags));
        return imageRepository.save(image);
    }

    public Image updateImage(Image image) {
        if (!imageRepository.existsById(image.getImageId())) {
            logger.error("Image id not in DB");
            return null;
        }

        return imageRepository.save(image);
    }

    @Transactional
    public boolean deleteById(int id) {
        Image image = imageRepository.findByImageId(id);
        if (image == null) {
            logger.error("Image id not in DB");
            return false;
        }
        List<Comment> imageComments = image.getImageComments();
        for (Comment comment : imageComments) {
            comment.setCommentUser(null);
            comment.setCommentImage(null);
            commentService.updateComment(comment);
            commentService.deleteCommentById(comment.getCommentId());
        }
        image.setImageUser(null);
        image.setImageHashtags(new ArrayList<>());
        image.setImageComments(new ArrayList<>());
        imageRepository.save(image);
        imageRepository.deleteByImageId(id);
        return true;
    }

    public void deleteAll() {
        imageRepository.deleteAll();
    }

    public Path writeToFile(MultipartFile file, Path dir) {
        Path filepath = Paths.get(dir.toString(), file.getOriginalFilename());

        try (OutputStream os = Files.newOutputStream(filepath)) {
            os.write(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return filepath;
    }

    public List<Image> findImagesByHashtag(String hashtagName){
        List<Image> existingImages = imageRepository.findAll();
        List<Image> resultingImages = new ArrayList<>();

        for(Image image : existingImages){
            boolean found = false;
            for(Hashtag hs : image.getImageHashtags()){
                if(hs.getHashtagName().equals(hashtagName)){
                    found = true;
                    break;
                }
            }
            if(found){
                resultingImages.add(image);
            }
        }

        return resultingImages;
    }

    public List<Image> findImagesByHashtagIds(List<Integer> hashtagIds){
        List<Image> existingImages = imageRepository.findAll();
        List<Image> resultingImages = new ArrayList<>();

        List<Hashtag> hashtags = new ArrayList<>();
        for (int id : hashtagIds) {
            Hashtag existentHashtag = hashtagRepository.findByHashtagId(id);
            if (existentHashtag != null) {
                hashtags.add(existentHashtag);
            }
        }

        for(Image image : existingImages){
            boolean found = false;
            for(Hashtag hs : image.getImageHashtags()){
                if(hashtags.contains(hs)){
                    found = true;
                    break;
                }
            }
            if(found){
                resultingImages.add(image);
            }
        }

        return resultingImages;
    }

    public Set<Image> randomImagesWithTopHashtags(List<Hashtag> topHashtags){
        List<Image> allImages = imageRepository.findAll();
        List<Image> resultingImagesList = new ArrayList<>();
        Set<Image> imagesSet = new HashSet<>();
        for(Hashtag hashtag : topHashtags){
            for(Image image : allImages){
                if(image.getImageHashtags().contains(hashtag)){
                    resultingImagesList.add(image);
                }
            }
        }

        Collections.shuffle(resultingImagesList);

        for(Image image : resultingImagesList){
            imagesSet.add(image);
        }
        return imagesSet;

    }

    public List<Image> findImagesByCategory(String category){
        List<Image> allImages = imageRepository.findAll();
        List<Image> resultingImages = new ArrayList<>();
        switch (category) {
            case "Space":
                for(Image image : allImages){
                    List<Hashtag> imageHashtags = image.getImageHashtags();
                    for(Hashtag hashtag : imageHashtags){
                        if(categoryUtils.verifyHashtagInSpaceCategory(hashtag.getHashtagName())){
                            resultingImages.add(image);
                            break;
                        }
                    }
                }
                break;
            case "Animals":
                for(Image image : allImages){
                    List<Hashtag> imageHashtags = image.getImageHashtags();
                    for(Hashtag hashtag : imageHashtags){
                        if(categoryUtils.verifyHashtagInAnimalsCategory(hashtag.getHashtagName())){
                            resultingImages.add(image);
                            break;
                        }
                    }
                }
                break;
            case "Nature":
                for(Image image : allImages){
                    List<Hashtag> imageHashtags = image.getImageHashtags();
                    for(Hashtag hashtag : imageHashtags){
                        if(categoryUtils.verifyHashtagInNatureCategory(hashtag.getHashtagName())){
                            resultingImages.add(image);
                            break;
                        }
                    }
                }
                break;
            default:
                logger.error("This category does not exist!");
        }

        return resultingImages;
    }
}
