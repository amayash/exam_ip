package com.example.exam.folder.service;

import com.example.exam.folder.model.Good;
import com.example.exam.folder.model.Review;
import com.example.exam.folder.model.User;
import com.example.exam.folder.repository.ReviewRepository;
import com.example.exam.util.validation.ValidatorUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final GoodService goodService;
    private final UserService userService;
    private final ValidatorUtil validatorUtil;
    public ReviewService(ReviewRepository reviewRepository,
                         GoodService goodService, UserService userService, ValidatorUtil validatorUtil) {
        this.reviewRepository = reviewRepository;
        this.goodService = goodService;
        this.userService = userService;
        this.validatorUtil = validatorUtil;
    }

    @Transactional
    public Review addReview(String text, Long goodId, Long userId) {
        final Review review = new Review(text);
        final Good good = goodService.findGood(goodId);
        final User user = userService.findUser(userId);
        review.setGood(good);
        review.setUser(user);
        validatorUtil.validate(review);
        return reviewRepository.save(review);
    }

    @Transactional(readOnly = true)
    public Review findReview(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public List<Review> findAllReviews() {
        return reviewRepository.findAll();
    }

    @Transactional
    public Review updateReview(Long id, String text) {
        final Review currentReview = findReview(id);
        currentReview.setText(text);
        validatorUtil.validate(currentReview);
        return reviewRepository.save(currentReview);
    }

    @Transactional
    public Review deleteReview(Long id) {
        final Review currentReview = findReview(id);
        reviewRepository.delete(currentReview);
        return currentReview;
    }

    @Transactional
    public void deleteAllReviews() {
        reviewRepository.deleteAll();
    }
}
