package com.example.exam.folder.controller;

import com.example.exam.folder.model.Review;
import com.example.exam.folder.model.User;
import com.example.exam.folder.model.UserRole;
import com.example.exam.folder.service.CategoryService;
import com.example.exam.folder.service.GoodService;
import com.example.exam.folder.service.ReviewService;
import com.example.exam.folder.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/good")
public class GoodMvcController {
    private final GoodService goodService;
    private final CategoryService categoryService;
    private final ReviewService reviewService;
    private final UserService userService;

    public GoodMvcController(GoodService goodService, CategoryService categoryService, ReviewService reviewService, UserService userService) {
        this.goodService = goodService;
        this.categoryService = categoryService;
        this.reviewService = reviewService;
        this.userService = userService;
    }

    @GetMapping
    public String getGoods(Model model) {
        List<GoodDto> goods = goodService.findAllGoods().stream()
                .map(GoodDto::new)
                .toList();
        model.addAttribute("goods", goods);
        List<CategoryDto> categories = categoryService.findAllCategories().stream()
                .map(CategoryDto::new)
                .toList();
        model.addAttribute("categories", categories);
        return "good";
    }

    @GetMapping(value = "/search/")
    public String searchGood(@RequestParam String request,
                               Model model) {
        List<GoodDto> goods = goodService.findAllGoods(request)
                .stream().map(GoodDto::new).toList();
        model.addAttribute("goods", goods);
        List<CategoryDto> categories = categoryService.findAllCategories().stream()
                .map(CategoryDto::new)
                .toList();
        model.addAttribute("categories", categories);
        return "good";
    }

    @GetMapping(value = "/search/category/")
    public String searchGoodByCategory(@RequestParam String request,
                               Model model) {
        List<GoodDto> goods = goodService.findAllGoods(Integer.parseInt(request))
                .stream().map(GoodDto::new).toList();
        model.addAttribute("goods", goods);
        List<CategoryDto> categories = categoryService.findAllCategories().stream()
                .map(CategoryDto::new)
                .toList();
        model.addAttribute("categories", categories);
        return "good";
    }

    @GetMapping("/{id}")
    public String getGood(@PathVariable Long id, Model model) {
        List<ReviewDto> reviews = goodService.findGood(id).getReviews().stream()
                .map(ReviewDto::new)
                .toList();
        model.addAttribute("reviews", reviews);
        return "review";
    }

    @GetMapping(value = {"/edit", "/edit/{id}"})
    @Secured({UserRole.AsString.ADMIN})
    public String editGood(@PathVariable(required = false) Long id,
                           Model model) {
        if (id == null || id <= 0) {
            List<CategoryDto> categories = categoryService.findAllCategories().stream()
                    .map(CategoryDto::new)
                    .toList();
            model.addAttribute("goodDto", new GoodDto());
            model.addAttribute("categories", categories);
        } else {
            model.addAttribute("goodId", id);
            model.addAttribute("goodDto", new GoodDto(goodService.findGood(id)));
        }
        return "good-edit";
    }

    @GetMapping(value = {"/{goodId}/review/", "/{goodId}/review/{id}"})
    @Secured({UserRole.AsString.USER})
    public String editGoodReview(@PathVariable Long goodId,
                                 @PathVariable(required = false) Long id,
                                 Model model) {
        if (id == null || id <= 0) {
            ReviewDto reviewDto = new ReviewDto();
            reviewDto.setGoodId(goodId);
            model.addAttribute("reviewDto", reviewDto);
        } else {
            model.addAttribute("reviewDto", new ReviewDto(reviewService.findReview(id)));
        }
        return "review-edit";
    }

    @PostMapping(value = {"/{goodId}/review/{id}", "/{goodId}/review/"})
    @Secured({UserRole.AsString.USER})
    public String editGoodReview(@PathVariable Long goodId,
                                 @PathVariable(required = false) Long id,
                                 @ModelAttribute @Valid ReviewDto reviewDto,
                                 BindingResult bindingResult,
                                 Model model,
                                 Principal principal) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "good-edit";
        }
        User user = userService.findByLogin(principal.getName());
        if (id == null || id <= 0) {
            reviewService.addReview(reviewDto.getText(), goodId, user.getId());
        } else {
            Review review = reviewService.findReview(id);
            if (Objects.equals(review.getUser().getId(), user.getId()) || user.getRole() == UserRole.ADMIN)
                reviewService.updateReview(id, reviewDto.getText());
        }
        return "redirect:/good/" + goodId;
    }

    @PostMapping(value = "/{id}")
    @Secured({UserRole.AsString.ADMIN})
    public String editGood(@PathVariable Long id,
                           @ModelAttribute @Valid GoodDto goodDto,
                           BindingResult bindingResult,
                           Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "good-edit";
        }
        goodService.updateGood(id, goodDto.getName(), goodDto.getPrice());

        return "redirect:/good";
    }

    @PostMapping(value = "/")
    @Secured({UserRole.AsString.ADMIN})
    public String saveGood(@RequestParam("price") String price,
                           @RequestParam("name") String name,
                           @RequestParam("categoryid") Long categoryId,
                           @RequestParam("maxCount") Integer capacity,
                           Model model) {
        goodService.addGood(Double.parseDouble(price), name,
                categoryId, capacity);
        return "redirect:/good";
    }

    @PostMapping("/delete/{id}")
    @Secured({UserRole.AsString.ADMIN})
    public String deleteGood(@PathVariable Long id) {
        goodService.deleteGood(id);
        return "redirect:/good";
    }
}
