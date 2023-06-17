package com.example.exam.folder.repository;

import com.example.exam.folder.model.Good;
import com.example.exam.folder.model.GoodExtension;
import com.example.exam.folder.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GoodRepository  extends JpaRepository<Good, Long> {
    @Query("Select sum(os.count) from CartGood os where os.good.id = :goodId")
    Optional<Integer> getCapacity(@Param("goodId") Long goodId);
    @Query("Select new com.example.exam.folder.model.GoodExtension(s, sum(os.count)) from Good s " +
            "left join CartGood os on s.id = os.good.id " +
            "group by s")
    List<GoodExtension> getGoodsWithCapacity();
    @Query("Select new com.example.exam.folder.model.GoodExtension(s, sum(os.count)) from Good s " +
            "left join CartGood os on s.id = os.good.id " +
            "where s.id = :goodId " +
            "group by s")
    Optional<GoodExtension> getGoodWithCapacity(@Param("goodId") Long goodId);

    @Query("select p from Good p where p.name like CONCAT('%',:search,'%')")
    List<Good> findAll(@Param("search") String search);

    @Query("select p from Good p where p.category.id=:search")
    List<Good> findAll(@Param("search") Integer search);
}
