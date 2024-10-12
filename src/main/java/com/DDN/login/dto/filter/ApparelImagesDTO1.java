package com.DDN.login.dto.filter;

import org.springframework.web.multipart.MultipartFile;

public class ApparelImagesDTO1 {
    private Integer id;
    private String title;

    private String imageUrl;

    private String genderName;
    private String apparelName;

    private MultipartFile[] images;

    public MultipartFile[] getImages() {
        return images;
    }

    public void setImages(MultipartFile[] images) {
        this.images = images;
    }

    public ApparelImagesDTO1() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getGenderName() {
        return genderName;
    }

    public void setGenderName(String genderName) {
        this.genderName = genderName;
    }

    public String getApparelName() {
        return apparelName;
    }

    public void setApparelName(String apparelName) {
        this.apparelName = apparelName;
    }
}
