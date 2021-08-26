package com.deroahe.gallerybe.payload.request;

import javax.validation.constraints.NotBlank;

public class HashtagAddRequest {
    @NotBlank
    private String hashtagsNames;

    @NotBlank
    private int imageId;

    public String getHashtagsNames() {
        return hashtagsNames;
    }

    public void setHashtagsNames(String hashtagsNames) {
        this.hashtagsNames = hashtagsNames;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
