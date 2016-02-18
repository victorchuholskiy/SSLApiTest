package com.knn.apitest.model;

/**
 * Created by Никита on 13.02.2016.
 */
public class Charity {
    private int id;
    private String title;
    private Category[] category;
    private String purpose;
    private int required_amount;
    private int current_amount;
    private String final_date;
    private String slug;
    private boolean is_ended;
    private String small_image;
    private String image_140x140;
    private String image_64x64;
    private String description;
    private String location;
    private int last_bid;
    private int likes_amount;

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

    public Category[] getCategory() {
        return category;
    }

    public void setCategory(Category[] category) {
        this.category = category;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public int getRequired_amount() {
        return required_amount;
    }

    public void setRequired_amount(int required_amount) {
        this.required_amount = required_amount;
    }

    public int getCurrent_amount() {
        return current_amount;
    }

    public void setCurrent_amount(int current_amount) {
        this.current_amount = current_amount;
    }

    public String getFinal_date() {
        return final_date;
    }

    public void setFinal_date(String final_date) {
        this.final_date = final_date;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public boolean is_ended() {
        return is_ended;
    }

    public void setIs_ended(boolean is_ended) {
        this.is_ended = is_ended;
    }

    public String getSmall_image() {
        return small_image;
    }

    public void setSmall_image(String small_image) {
        this.small_image = small_image;
    }

    public String getImage_140x140() {
        return image_140x140;
    }

    public void setImage_140x140(String image_140x140) {
        this.image_140x140 = image_140x140;
    }

    public String getImage_64x64() {
        return image_64x64;
    }

    public void setImage_64x64(String image_64x64) {
        this.image_64x64 = image_64x64;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getLast_bid() {
        return last_bid;
    }

    public void setLast_bid(int last_bid) {
        this.last_bid = last_bid;
    }

    public int getLikes_amount() {
        return likes_amount;
    }

    public void setLikes_amount(int likes_amount) {
        this.likes_amount = likes_amount;
    }
}
