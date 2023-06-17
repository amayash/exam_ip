package com.example.exam.folder.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Good {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, unique = true, length = 64)
    @NotBlank(message = "name can't be null or empty")
    @Size(min = 3, max = 64)
    protected String name;
    @NotNull(message = "price can't be null or empty")
    protected Double price;
    @NotNull(message = "maxCount can't be null or empty")
    @Column
    protected Integer maxCount;
    @OneToMany(mappedBy = "good", fetch = FetchType.EAGER)
    private List<CartGood> carts;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_fk")
    protected Category category;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "good", cascade = {CascadeType.MERGE,CascadeType.REMOVE})
    private List<Review> reviews;

    public Good() {
    }

    public Good(String name, Double price, Integer maxCount) {
        this.name = name;
        this.price = price;
        this.reviews = new ArrayList<>();
        this.maxCount = maxCount;
    }

    public Good(Good good) {
        this.id = good.getId();
        this.name = good.getName();
        this.price = good.getPrice();
        this.carts = good.getCarts();
        this.reviews = good.getReviews();
        this.category = good.getCategory();
        this.maxCount = good.getMaxCount();
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReview(Review review) {
        if (review.getGood().equals(this)) {
            this.reviews.add(review);
        }
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
        if (!category.getGoods().contains(this)) {
            category.setGood(this);
        }
    }

    public List<CartGood> getCarts() {
        return carts;
    }

    public void setCarts(List<CartGood> carts) {
        this.carts = carts;
    }

    public void addCart(CartGood cartGood){
        if (carts == null){
            carts = new ArrayList<>();
        }
        if (!carts.contains(cartGood)) {
            this.carts.add(cartGood);
        }
    }

    public void removeCart(CartGood cartGood){
        if (carts.contains(cartGood))
            this.carts.remove(cartGood);
    }

    public void setMaxCount(Integer maxCount) {
        this.maxCount = maxCount;
    }

    public Integer getMaxCount() {
        return maxCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
