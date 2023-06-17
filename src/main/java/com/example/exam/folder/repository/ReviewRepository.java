package com.example.exam.folder.repository;

import com.example.exam.folder.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
