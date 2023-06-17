package com.example.exam.folder.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 200)
    @NotBlank(message = "text can't be null or empty")
    @Size(min = 3, max = 200)
    private String text;
    @NotNull(message = "timestamp can't be null or empty")
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    protected LocalDateTime timestamp;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "good_fk")
    private Good good;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_fk")
    private User user;

    public Review(String text) {
        this.timestamp = LocalDateTime.now();
        this.text = text;
    }

    public Review(Review item) {
        this.id = item.getId();
        this.timestamp = LocalDateTime.now();
        this.text = item.getText();
        this.good = item.getGood();
        this.user = item.getUser();
    }

    public Review() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return Objects.equals(id, review.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Good getGood() {
        return good;
    }

    public void setGood(Good good) {
        this.good = good;
        if (!good.getReviews().contains(this)) {
            good.setReview(this);
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        if (!user.getReviews().contains(this)) {
            user.setReview(this);
        }
    }
}
