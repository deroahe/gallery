package com.deroahe.gallerybe.service.impl;

import com.deroahe.gallerybe.model.Hashtag;
import com.deroahe.gallerybe.repository.HashtagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HashtagServiceImpl {

    @Autowired
    private HashtagRepository hashtagRepository;

    public void save(Hashtag hashtag) {
        if (hashtagRepository.findByName(hashtag.getName()) == null) {
            hashtagRepository.save(hashtag);
        }
    }

    public void saveAll(List<Hashtag> hashtagList){
        hashtagList.stream().forEach(h -> {
            if(hashtagRepository.findByName(h.getName()) == null) {
                hashtagRepository.save(h);
            }
        });
    }

    public Hashtag findByName(String name){
        return hashtagRepository.findByName(name);
    }
}
