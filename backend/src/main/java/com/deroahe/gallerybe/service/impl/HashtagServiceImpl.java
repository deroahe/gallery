package com.deroahe.gallerybe.service.impl;

import com.deroahe.gallerybe.model.Hashtag;
import com.deroahe.gallerybe.model.Image;
import com.deroahe.gallerybe.repository.HashtagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.Map.Entry;

@Service
public class HashtagServiceImpl {

    private HashtagRepository hashtagRepository;
    private ImageServiceImpl imageService;

    @Autowired
    public HashtagServiceImpl(HashtagRepository hashtagRepository, ImageServiceImpl imageService) {
        this.hashtagRepository = hashtagRepository;
        this.imageService = imageService;
    }

    public Hashtag findHashtagById(int id) {
        return hashtagRepository.findByHashtagId(id);
    }

    public Hashtag findByName(String name){
        return hashtagRepository.findByHashtagName(name);
    }

    public List<Hashtag> findAllHashtags() {
        return hashtagRepository.findAll();
    }

    public void saveHashtag(Hashtag hashtag) {
        if (hashtagRepository.findByHashtagName(hashtag.getHashtagName()) == null) {
            hashtagRepository.save(hashtag);
        }
    }

    public void saveAllHashtags(List<Hashtag> hashtagList){
        hashtagList.stream().forEach(h -> {
            if(hashtagRepository.findByHashtagName(h.getHashtagName()) == null) {
                hashtagRepository.save(h);
            }
        });
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
}
