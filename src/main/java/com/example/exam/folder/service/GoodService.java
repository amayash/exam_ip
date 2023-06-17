package com.example.exam.folder.service;

import com.example.exam.folder.model.Good;
import com.example.exam.folder.model.GoodExtension;
import com.example.exam.folder.model.Category;
import com.example.exam.folder.repository.GoodRepository;
import com.example.exam.util.validation.ValidatorUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class GoodService {
    private final GoodRepository goodRepository;
    private final CategoryService categoryService;
    private final ValidatorUtil validatorUtil;
    public GoodService(GoodRepository goodRepository,
                          CategoryService categoryService, ValidatorUtil validatorUtil) {
        this.goodRepository = goodRepository;
        this.categoryService = categoryService;
        this.validatorUtil = validatorUtil;
    }

    @Transactional
    public Good addGood(Double price, String name, Long categoryId, Integer capacity) {
        final Good good = new Good(name, price, capacity);
        final Category category = categoryService.findCategory(categoryId);
        good.setCategory(category);
        validatorUtil.validate(good);
        return goodRepository.save(good);
    }

    @Transactional(readOnly = true)
    public GoodExtension findGood(Long id) {
        return goodRepository.getGoodWithCapacity(id)
                .orElseThrow(() -> new GoodNotFoundException(id));
    }

    @Transactional
    public List<Good> findAllGoods(String search) {
        return goodRepository.findAll(search);
    }

    @Transactional
    public List<Good> findAllGoods(Integer search) {
        return goodRepository.findAll(search);
    }

    @Transactional(readOnly = true)
    public Good findBaseGood(Long id) {
        return goodRepository.findById(id)
                .orElseThrow(() -> new GoodNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public List<GoodExtension> findAllGoods() {
        List<GoodExtension> goods = goodRepository.getGoodsWithCapacity();
        return goods;
    }

    @Transactional
    public Good updateGood(Long id, String name, Double price) {
        final Good currentGood = findBaseGood(id);
        currentGood.setPrice(price);
        currentGood.setName(name);
        validatorUtil.validate(currentGood);
        return goodRepository.save(currentGood);
    }

    @Transactional
    public Good deleteGood(Long id) {
        final Good currentGood = findBaseGood(id);
        // все равно сеанс не удалился бы, который участвует в заказах
        // для отслеживания операции с ошибкой
        if (currentGood.getCarts().size() > 0)
            throw new IllegalArgumentException();
        goodRepository.delete(currentGood);
        return currentGood;
    }

    @Transactional
    public void deleteAllGoods() {
        goodRepository.deleteAll();
    }

    @Transactional
    public Integer getCapacity(Long goodId) {
        return goodRepository.getCapacity(goodId).orElse(0);
    }
}
