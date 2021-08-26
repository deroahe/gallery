package com.deroahe.gallerybe.util.export;


import com.deroahe.gallerybe.model.Hashtag;
import com.deroahe.gallerybe.model.Image;
import com.deroahe.gallerybe.model.User;

import java.util.HashMap;
import java.util.List;

public class ExcelData {

    List<Hashtag> mostUsedHashtags;

    List<Image> mostUpvotedImages;

    List<User> newUsers;

    HashMap<String, Integer> map;


    public ExcelData() {
    }

    public ExcelData(ExcelData excelData) {
        this.mostUpvotedImages = excelData.getMostUpvotedImages();
        this.mostUsedHashtags = excelData.getMostUsedHashtags();
        this.newUsers = excelData.getNewUsers();
        this.map = excelData.getMap();

    }


    public List<Hashtag> getMostUsedHashtags() {
        return mostUsedHashtags;
    }

    public void setMostUsedHashtags(List<Hashtag> mostUsedHashtags) {
        this.mostUsedHashtags = mostUsedHashtags;
    }

    public List<Image> getMostUpvotedImages() {
        return mostUpvotedImages;
    }

    public void setMostUpvotedImages(List<Image> mostUpvotedImages) {
        this.mostUpvotedImages = mostUpvotedImages;
    }

    public List<User> getNewUsers() {
        return newUsers;
    }

    public void setNewUsers(List<User> newUsers) {
        this.newUsers = newUsers;
    }

    public HashMap<String, Integer> getMap() {
        return map;
    }

    public void setMap(HashMap<String, Integer> map) {
        this.map = map;
    }

    public ExcelData(List<Hashtag> mostUsedHashtags, List<Image> mostUpvotedImages, List<User> newUsers, HashMap<String, Integer> map) {
        this.mostUsedHashtags = mostUsedHashtags;
        this.mostUpvotedImages = mostUpvotedImages;
        this.newUsers = newUsers;
        this.map = map;
    }
}