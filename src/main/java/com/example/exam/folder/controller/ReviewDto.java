package com.example.exam.folder.controller;

import com.example.exam.folder.model.Review;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class ReviewDto {
    private long id;
    private String text;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime timestamp;
    private UserDto user;
    private long goodId;
    public ReviewDto() {
    }

    public ReviewDto(Review review) {
        this.text = review.getText();
        this.id = review.getId();
        this.timestamp = review.getTimestamp();
        this.user = new UserDto(review.getUser());
        this.goodId = review.getGood().getId();
    }

    public long getGoodId() {
        return goodId;
    }

    public void setGoodId(long goodId) {
        this.goodId = goodId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }
}
