package com.deroahe.gallerybe.payload.request;

import javax.validation.constraints.NotBlank;

public class CommentAddRequest {
    @NotBlank
    private String commentString;

    @NotBlank
    private int userId;

    @NotBlank
    private int imageId;

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getCommentString() {
        return commentString;
    }

    public void setCommentString(String commentString) {
        this.commentString = commentString;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
