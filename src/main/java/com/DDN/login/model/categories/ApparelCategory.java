package com.DDN.login.model.categories;

import com.DDN.login.model.images.ApparelImages;
import com.DDN.login.model.info.ProductInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
public class ApparelCategory implements Serializable {
    @Id
    private int id;

    private String type;

    private Date createdDate;

    private Date updateDate;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "apparelCategory")
    @JsonIgnore
    private List<ProductInfo> productInfos;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "apparelCategory")
    @JsonIgnore
    private List<ApparelImages> apparelImages;

    public ApparelCategory() {}

    public ApparelCategory(String type) {
        this.type = type;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
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
        return productInfos;
    }

    public void setProductInfos(List<ProductInfo> productInfos) {
        this.productInfos = productInfos;
    }

    public List<ApparelImages> getApparelImages() {
        return apparelImages;
    }

    public void setApparelImages(List<ApparelImages> apparelImages) {
        this.apparelImages = apparelImages;
    }
}
