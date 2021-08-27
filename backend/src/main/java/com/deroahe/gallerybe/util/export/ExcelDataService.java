package com.deroahe.gallerybe.util.export;

import com.deroahe.gallerybe.model.Hashtag;
import com.deroahe.gallerybe.model.Image;
import com.deroahe.gallerybe.model.User;
import com.deroahe.gallerybe.service.impl.HashtagServiceImpl;
import com.deroahe.gallerybe.service.impl.ImageServiceImpl;
import com.deroahe.gallerybe.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class ExcelDataService {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private ImageServiceImpl imageService;

    @Autowired
    private HashtagServiceImpl hashtagService;

    public ExcelData getExportDataElements(boolean order) {
        List<User> allUsers = userService.findAllUsers();
        List<User> finalUserList = new ArrayList<>();
        for (User u : allUsers) {
            User userHelper = new User();
            System.out.println(finalUserList.size());
            if (finalUserList.size() < 4) {
                finalUserList.add(u);
            } else {
                Collections.sort(finalUserList, new Comparator<User>() {
                    public int compare(User u1, User u2) {
                        if (order) {
                            //compare two object and return an integer
                            return u1.getUserId() - (u2.getUserId());
                        } else {
                            return u2.getUserId() - (u1.getUserId());
                        }
                    }
                });
                for(User uu : finalUserList){
                    System.out.println("used id -> " + uu.getUserId() + " usr name " + uu.getUserUsername());
                }
                if (u.getUserId() > finalUserList.get(0).getUserId()) {
                    finalUserList.remove(0);
                    finalUserList.add(u);
                }
            }
            for(User uu : finalUserList){
                System.out.println("used id -> " + uu.getUserId() + " usr name " + uu.getUserUsername());
            }
        }

        List<Image> allImages = imageService.findAllImages();
        List<Image> finalImages = new ArrayList<>();

        for (Image i : allImages) {
            Image imageHelper = new Image();
            if (finalImages.size() < 5) {
                finalImages.add(i);
            } else {
                Collections.sort(finalImages, new Comparator<Image>() {
                    public int compare(Image i1, Image i2) {
                        if (order) {
                            //compare two object and return an integer
                            return i1.getImageScore() - (i2.getImageScore());
                        } else {
                            return i2.getImageScore() - (i1.getImageScore());
                        }
                    }
                });
                for(Image ii : finalImages){
                    System.out.println("imd url  -> " + ii.getImageUrl() + " imd vote " + ii.getImageScore());
                }
                if (i.getImageScore() > finalImages.get(0).getImageScore()) {
                    finalImages.remove(0);
                    finalImages.add(i);
                }
            }
        }

        List<Hashtag> topHashtags = hashtagService.findMostUsedHashtags();

        HashMap<String,Integer> map = new HashMap<>();
        for(Hashtag hashtag : topHashtags){
            int nr = hashtagService.hashtagCount(hashtag.getHashtagName());
            map.put(hashtag.getHashtagName(), nr);
        }

        ExcelData excelData = new ExcelData(topHashtags, finalUserList, map);

        return excelData;
    }

}
