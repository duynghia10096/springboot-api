package com.DDN.login.model.images;

import com.DDN.login.model.categories.ProductBrandCategory;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
public class BrandImages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    private String imageLocalPath;
    private String imageURL;

    private Date createdDate;
    private Date updatedDate;


    @ManyToOne(cascade = CascadeType.ALL, fetch =FetchType.LAZY)
    @JoinColumn(name="brand_id")
    @JsonIgnore
    private ProductBrandCategory productBrandCategory;

    public BrandImages() {}

    public BrandImages(String title, String imageLocalPath, String imageURL, ProductBrandCategory brandCategory) {
        this.title = title;
        this.imageLocalPath = imageLocalPath;
        this.imageURL = imageURL;
        this.productBrandCategory = brandCategory;
    }

    public BrandImages(String title, String imageLocalPath, String imageURL) {
        this.title = title;
        this.imageLocalPath = imageLocalPath;
        this.imageURL = imageURL;
    }

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

    public ProductBrandCategory getProductBrandCategory() {
        return productBrandCategory;
    }

    public void setProductBrandCategory(ProductBrandCategory productBrandCategory) {
        this.productBrandCategory = productBrandCategory;
    }
}
