package com.kkini.search.repository;

import com.kkini.search.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByNameContainingOrderByAverageRatingDesc(String name);

    @Query("SELECT i FROM Item i WHERE " +
            "(LOCATE(:name, i.name) > 0 AND LENGTH(:name) >= 2 OR :name IS NULL) AND " +
            "i.category.categoryId = :categoryId ORDER BY i.averageRating DESC")
    List<Item> findByNameAndCategoryCategoryId(@Param("name") String name,
                                               @Param("categoryId") Long categoryId);

    List<Item> findByCategory_CategoryId(Long categoryId);
}