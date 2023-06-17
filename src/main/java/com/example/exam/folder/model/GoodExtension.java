package com.example.exam.folder.model;

public class GoodExtension extends Good {
    private Long capacity;
    public GoodExtension(Good good, Long capacity) {
        super(good);
        this.capacity = capacity;
    }

    public Long getCapacity() {
        return capacity;
    }

    public void setCapacity(Long capacity) {
        this.capacity = capacity;
    }
}
