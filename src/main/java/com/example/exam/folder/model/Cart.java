package com.example.exam.folder.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToMany(mappedBy = "cart", fetch = FetchType.EAGER, cascade =
            {
                    CascadeType.REMOVE,
                    CascadeType.MERGE,
                    CascadeType.PERSIST
            }, orphanRemoval = true)
    private List<CartGood> goods;

    @OneToOne
    @JoinColumn(name = "user_fk")
    private User user;

    public Cart() {
    }

    public void addGood(CartGood cartGood) {
        if (goods == null) {
            goods = new ArrayList<>();
        }
        if (!goods.contains(cartGood))
            this.goods.add(cartGood);
    }

    public void removeGood(CartGood cartGood){
        if (goods.contains(cartGood))
            this.goods.remove(cartGood);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart r = (Cart) o;
        return Objects.equals(id, r.id);
    }

    public List<CartGood> getGoods() {
        return goods;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
