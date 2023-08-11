package com.kkini.search.repository;

import com.kkini.search.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByCategory_CategoryIdOrderByAverageRatingDesc(Long categoryId);
    List<Item> findByCategory_CategoryName(String categoryName);
    List<Item> findByNameContainingOrderByAverageRatingDesc(String name);
    List<Item> findAllByNameLike(String name);
    @Query("SELECT i FROM Item i WHERE (i.name LIKE :name OR :name IS NULL) AND " +
            "(i.category.categoryId = :categoryId OR :categoryId IS NULL) ORDER BY i.averageRating DESC")
    List<Item> findByNameAndCategoryCategoryId(@Param("name") String name,
                                               @Param("categoryId") Long categoryId);



}
