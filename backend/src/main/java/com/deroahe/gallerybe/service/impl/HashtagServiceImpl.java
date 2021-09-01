package com.deroahe.gallerybe.service.impl;

import com.deroahe.gallerybe.model.Hashtag;
import com.deroahe.gallerybe.model.Image;
import com.deroahe.gallerybe.repository.HashtagRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.Map.Entry;

@Service
public class HashtagServiceImpl {

    private HashtagRepository hashtagRepository;
    private ImageServiceImpl imageService;

    private Logger logger = LoggerFactory.getLogger(HashtagServiceImpl.class);

    @Autowired
    public HashtagServiceImpl(HashtagRepository hashtagRepository, ImageServiceImpl imageService) {
        this.hashtagRepository = hashtagRepository;
        this.imageService = imageService;
    }

    public boolean existsById(int id) {
        return hashtagRepository.existsById(id);
    }

    public boolean existsByName(String hashtagName) {
        return hashtagRepository.existsByHashtagName(hashtagName);
    }

    public Hashtag findHashtagById(int id) {
        return hashtagRepository.findByHashtagId(id);
    }

    public Hashtag findHashtagByName(String name){
        return hashtagRepository.findByHashtagName(name);
    }

    public List<Hashtag> findAllHashtags() {
        return hashtagRepository.findAll();
    }

//    public List<Hashtag> saveHashtagsWithImage(String hashtagsNames, int imageId) {
//        Image image = imageService.findImageById(imageId);
//        if (hashtagsNames == null || image == null) {
//            logger.error("Hashtags names missing or image id not found in DB");
//            return null;
//        }
//        List<String> hashtagsNamesList = Arrays.asList(hashtagsNames.split("\\s*,\\s*"));
//        List<Hashtag> hashtagList = new ArrayList<>();
//        for (String hashtagName : hashtagsNamesList) {
//            Hashtag hashtagToAdd = findHashtagByName(hashtagName);
//            List<Image> hashtagImages = new ArrayList<>();
//            if (hashtagToAdd == null) {
//                hashtagToAdd = new Hashtag(hashtagName);
//                hashtagRepository.save(hashtagToAdd);
//            } else {
//                hashtagImages = hashtagToAdd.getHashtagImages();
//                hashtagToAdd.setHashtagImages(hashtagImages);
//            }
//            hashtagImages.add(image);
//            hashtagToAdd.setHashtagImages(hashtagImages);
//            hashtagList.add(hashtagToAdd);
//            hashtagRepository.save(hashtagToAdd);
//        }
//
//        return hashtagList;
//    }

    public Hashtag saveHashtag(String hashtagName) {
        if (!existsByName(hashtagName)) {
            Hashtag hashtag = new Hashtag(hashtagName);
            return hashtagRepository.save(hashtag);
        }
        logger.error("Hashtag name already in DB");
        return null;
    }

    public Hashtag saveHashtagObject(Hashtag hashtag) {
        if (!existsByName(hashtag.getHashtagName())) {
            return hashtagRepository.save(hashtag);
        }
        logger.error("Hashtag name already in DB");
        return null;
    }

    public void saveAllHashtags(List<Hashtag> hashtags){
        for (Hashtag hashtag : hashtags) {
            hashtagRepository.save(hashtag);
        }
    }

    public Hashtag updateHashtag(Hashtag hashtag) {
        if (!hashtagRepository.existsByHashtagId(hashtag.getHashtagId())) {
            logger.error("Hashtag id not in DB");
            return null;
        }

        return hashtagRepository.save(hashtag);
    }

    public boolean deleteHashtagById(int id) {
        if (!hashtagRepository.existsByHashtagId(id)) {
            logger.error("Hashtag id not in DB");
            return false;
        }
        hashtagRepository.deleteByHashtagId(id);
        return true;
    }

    public void deleteAllHashtags() {
        hashtagRepository.deleteAll();
    }

    public List<Hashtag> findMostUsedHashtags() {
        Map<Hashtag, Integer> map = new HashMap<Hashtag, Integer>();
        List<Hashtag> topHastags = new ArrayList<>();
        List<Image> allImages = imageService.findAllImages();
        List<Hashtag> allHashtags = hashtagRepository.findAll();

        for (Hashtag hashtag : allHashtags) {
            int nr = 0;
            for (Image image : allImages) {
                if (image.getImageHashtags().contains(hashtag)) {
                    nr++;
                }
            }
            if (map.size() < 9) {
                map.put(hashtag,nr);
            } else {
                boolean needUpdate = false;
                Hashtag hashtagPop = new Hashtag();
                sortByValue(false,map);
                List<Entry<Hashtag, Integer>> list = new LinkedList<Entry<Hashtag, Integer>>(map.entrySet());
                for(Entry<Hashtag,Integer>  entry: list){
                    if(entry.getValue() < nr){
                        hashtagPop = entry.getKey();
                        needUpdate = true;
                        break;
                    }
                }
                if(needUpdate){
                    map.remove(hashtagPop);
                    map.put(hashtag,nr);
                }
            }
        }
        //sortByValue(true,map);
        List<Entry<Hashtag, Integer>> listFinal = new LinkedList<Entry<Hashtag, Integer>>(map.entrySet());
        for(Entry<Hashtag,Integer>  entry: listFinal){
            topHastags.add(entry.getKey());
            System.out.println("Hashtag " + entry.getKey().getHashtagName() + " cu urmatoarele aparitii " + entry.getValue());
        }

        return  topHastags;
    }

    void sortByValue(boolean order, Map<Hashtag, Integer> map) {
        //convert HashMap into List
        List<Entry<Hashtag, Integer>> list = new LinkedList<Entry<Hashtag, Integer>>(map.entrySet());
        //sorting the list elements
        Collections.sort(list, new Comparator<Entry<Hashtag, Integer>>() {
            public int compare(Entry<Hashtag, Integer> h1, Entry<Hashtag, Integer> h2) {
                if (order) {
                    //compare two object and return an integer
                    return h1.getValue().compareTo(h2.getValue());
                } else {
                    return h2.getValue().compareTo(h1.getValue());
                }
            }
        });
    }

    public int hashtagCount(String hashtagName) {
        Hashtag hashtag = hashtagRepository.findByHashtagName(hashtagName);
        int nr = 0;
        if(hashtag != null){
            List<Image> imageList = imageService.findAllImages();
            for(Image image : imageList){
                if(image.getImageHashtags().size() > 0 ){
                    if(image.getImageHashtags().contains(hashtag)){
                        nr ++;
                    }
                }
            }
        }

        return nr;
    }
}
