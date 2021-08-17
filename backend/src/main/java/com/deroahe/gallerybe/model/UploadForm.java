package com.deroahe.gallerybe.model;

import org.springframework.web.multipart.MultipartFile;

public class UploadForm {

//    private Image image;
    private int userId;
    private MultipartFile multipartFile;

    public UploadForm(int userId, MultipartFile multipartFile) {
//        this.image = image;
        this.userId = userId;
        this.multipartFile = multipartFile;
    }

//    public Image getImage() {
//        return image;
//    }
//
//    public void setImage(Image image) {
//        this.image = image;
//    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public MultipartFile getMultipartFile() {
        return multipartFile;
    }

    public void setMultipartFile(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }
}
