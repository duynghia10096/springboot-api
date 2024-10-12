package com.DDN.login.model.images;

import com.DDN.login.model.categories.ApparelCategory;
import com.DDN.login.model.categories.GenderCategory;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
public class ApparelImages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    private String imageLocalPath;

    private String imageURL;

    private Date createdDate;
    private Date updatedDate;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="apparel_id", referencedColumnName = "id")
    @JsonIgnore
    private ApparelCategory apparelCategory;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="gender_id")
    @JsonIgnore
    private GenderCategory genderCategory;

    public ApparelImages(String title, String imageLocalPath, String imageURL,ApparelCategory apparelCategory, GenderCategory genderCategory) {
        this.title = title;
        this.imageLocalPath = imageLocalPath;
        this.imageURL = imageURL;
        this.apparelCategory = apparelCategory;
        this.genderCategory = genderCategory;
    }

    public ApparelImages(String title, String imageLocalPath, String imageURL) {
        this.title = title;
        this.imageLocalPath = imageLocalPath;
        this.imageURL = imageURL;
    }

    public ApparelImages() {}

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageLocalPath() {
        return imageLocalPath;
    }

    public void setImageLocalPath(String imageLocalPath) {
        this.imageLocalPath = imageLocalPath;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public ApparelCategory getApparelCategory() {
        return apparelCategory;
    }

    public void setApparelCategory(ApparelCategory apparelCategory) {
        this.apparelCategory = apparelCategory;
    }

    public GenderCategory getGenderCategory() {
        return genderCategory;
    }

    public void setGenderCategory(GenderCategory genderCategory) {
        this.genderCategory = genderCategory;
    }
}
