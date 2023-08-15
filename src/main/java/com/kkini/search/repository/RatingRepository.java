package com.kkini.search.repository;

import com.kkini.search.entity.Ratings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Ratings, Long> {
    List<Ratings> findByItemId(Long itemId);
    List<Ratings> findByItemIdOrderByUpdatedAtDesc(Long itemId);
}
