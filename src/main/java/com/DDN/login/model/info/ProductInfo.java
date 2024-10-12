package com.DDN.login.model.info;

import com.DDN.login.model.OrderItem;
import com.DDN.login.model.Reviews;
import com.DDN.login.model.User;
import com.DDN.login.model.categories.ApparelCategory;
import com.DDN.login.model.categories.GenderCategory;
import com.DDN.login.model.categories.PriceRangeCategory;
import com.DDN.login.model.categories.ProductBrandCategory;
import com.DDN.login.model.images.ProductImages;
import com.fasterxml.jackson.annotation.JsonIgnore;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(indexes={@Index(columnList = "gender_id, apparel_id, brand_id, price")})
public class ProductInfo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int sellerId;

    private String name;

    @Temporal(TemporalType.DATE)
    private Date publicationDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="brand_id")
    @JsonIgnore
    private ProductBrandCategory productBrandCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="gender_id")
    @JsonIgnore
    private GenderCategory genderCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="price_id")
    @JsonIgnore
    private PriceRangeCategory priceRangeCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="apparel_id")
    @JsonIgnore
    private ApparelCategory apparelCategory;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true,mappedBy = "productInfo")
    @JsonIgnore
    private List<Reviews> reviews;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true,mappedBy = "productInfo")
    @JsonIgnore
    private List<OrderItem> orderItems;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "productInfo")
    @JsonIgnore
    private List<ProductImages> productImages;

    private double price;
    private int availableQuantity;
    private int deliveryTime;
    private float ratings;
    private boolean verificationStatus;
    private String imageLocalPath;

    private String description;

    @OneToMany(mappedBy = "orderInfo")
    @JsonIgnore
    private List<OrderInfo> orders;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User addedByUser;

    private String imageURL;

    private String color;

    public ProductInfo() {

    }
    public ProductInfo(int sellerId, String name, Date publicationDate, ProductBrandCategory productBrandCategory,
                       GenderCategory genderCategory, ApparelCategory apparelCategory,
                       PriceRangeCategory priceRangeCategory,
                       double price, int availableQuantity, int deliveryTime, float ratings,
                       boolean verificationStatus, String imageURL, String description, User addedByUser, String color) {
        this.sellerId = sellerId;
        this.name = name;
        this.publicationDate = publicationDate;
        this.productBrandCategory = productBrandCategory;
        this.genderCategory = genderCategory;
        this.apparelCategory = apparelCategory;
        this.priceRangeCategory = priceRangeCategory;
        this.price = price;
        this.availableQuantity = availableQuantity;
        this.deliveryTime = deliveryTime;
        this.ratings = ratings;
        this.verificationStatus = verificationStatus;
        this.imageURL = imageURL;
        this.description = description;
        this.addedByUser = addedByUser;    
        this.color = color;           

    }

    public List<ProductImages> getProductImages() {
        return productImages;
    }

    public void setProductImages(List<ProductImages> productImages) {
        this.productImages = productImages;
    }

    public List<Reviews> getReviews() {
        return reviews;
    }

    public void setReviews(List<Reviews> reviews) {
        this.reviews = reviews;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public ProductBrandCategory getProductBrandCategory() {
        return productBrandCategory;
    }

    public void setProductBrandCategory(ProductBrandCategory productBrandCategory) {
        this.productBrandCategory = productBrandCategory;
    }

    public GenderCategory getGenderCategory() {
        return genderCategory;
    }

    public void setGenderCategory(GenderCategory genderCategory) {
        this.genderCategory = genderCategory;
    }

    public PriceRangeCategory getPriceRangeCategory() {
        return priceRangeCategory;
    }

    public void setPriceRangeCategory(PriceRangeCategory priceRangeCategory) {
        this.priceRangeCategory = priceRangeCategory;
    }

    public ApparelCategory getApparelCategory() {
        return apparelCategory;
    }

    public void setApparelCategory(ApparelCategory apparelCategory) {
        this.apparelCategory = apparelCategory;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public int getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(int deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public float getRatings() {
        return ratings;
    }

    public void setRatings(float ratings) {
        this.ratings = ratings;
    }

    public boolean isVerificationStatus() {
        return verificationStatus;
    }

    public void setVerificationStatus(boolean verificationStatus) {
        this.verificationStatus = verificationStatus;
    }

    public String getImageLocalPath() {
        return imageLocalPath;
    }

    public void setImageLocalPath(String imageLocalPath) {
        this.imageLocalPath = imageLocalPath;
    }

    public List<OrderInfo> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderInfo> orders) {
        this.orders = orders;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getAddedByUser() {
        return addedByUser;
    }

    public void setAddedByUser(User addedByUser) {
        this.addedByUser = addedByUser;
    }

}
