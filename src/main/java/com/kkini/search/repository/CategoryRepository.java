package com.kkini.search.repository;

import com.kkini.search.entity.Category;
import com.kkini.search.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByParentIsNull();
    List<Category> findByParentCategoryId(Long parentId);
}
