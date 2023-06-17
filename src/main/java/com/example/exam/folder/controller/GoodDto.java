package com.example.exam.folder.controller;

import com.example.exam.folder.model.Good;
import com.example.exam.folder.model.GoodExtension;
import com.example.exam.folder.model.Review;

import java.util.List;

public class GoodDto {
    private long id;
    private String name;
    private Double price;
    private CategoryDto category;
    private Long capacity;
    private int maxCount;
    private List<ReviewDto> reviews;

    public GoodDto() {
    }

    public GoodDto(GoodExtension good) {
        this.id = good.getId();
        this.name = good.getName();
        this.price = good.getPrice();
        this.capacity = good.getCapacity();
        this.maxCount = good.getMaxCount();
        if (good.getCategory() != null) {
            this.category = new CategoryDto(good.getCategory());
        } else this.category = null;
        if (good.getReviews() != null) {
            reviews = good.getReviews().stream()
                    .map(ReviewDto::new).toList();
        }
    }

    public GoodDto(Good good) {
        this.id = good.getId();
        this.name = good.getName();
        this.price = good.getPrice();
        this.maxCount = good.getMaxCount();
        if (good.getCategory() != null) {
            this.category = new CategoryDto(good.getCategory());
        } else this.category = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ReviewDto> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewDto> reviews) {
        this.reviews = reviews;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public CategoryDto getCategory() {
        return category;
    }

    public void setCategory(CategoryDto category) {
        this.category = category;
    }

    public Long getCapacity() {
        return capacity;
    }

    public void setCapacity(Long capacity) {
        this.capacity = capacity;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }
}
