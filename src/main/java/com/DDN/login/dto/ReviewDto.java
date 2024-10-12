package com.DDN.login.dto;

import com.DDN.login.model.Reviews;

import javax.validation.constraints.NotNull;

public class ReviewDto {
    private Integer id;
    private @NotNull String comment;
    private @NotNull Double ratings;
    private @NotNull Integer productId;
    private @NotNull Long userId;


    public ReviewDto() {}


    public Double getRatings() {
        return ratings;
    }

    public void setRatings(Double ratings) {
        this.ratings = ratings;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
