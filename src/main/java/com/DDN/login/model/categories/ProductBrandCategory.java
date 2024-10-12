package com.DDN.login.model.categories;

import com.DDN.login.model.images.BrandImages;
import com.DDN.login.model.info.ProductInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
public class ProductBrandCategory implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String type;

    private Date createdDate;
    private Date updatedDate;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "productBrandCategory")
    @JsonIgnore
    private List<ProductInfo> productInfo;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "productBrandCategory")
    @JsonIgnore
    private List<BrandImages> brandImages;

    public ProductBrandCategory() {}

    public ProductBrandCategory(String type) {
        this.type = type;
    }

    public List<ProductInfo> getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(List<ProductInfo> productInfo) {
        this.productInfo = productInfo;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<ProductInfo> getProductInfos() {
        return productInfo;
    }

    public void setProductInfos(List<ProductInfo> productInfos) {
        this.productInfo = productInfos;
    }

    public List<BrandImages> getBrandImages() {
        return brandImages;
    }

    public void setBrandImages(List<BrandImages> brandImages) {
        this.brandImages = brandImages;
    }
}
