package com.kkini.search.repository;

import com.kkini.search.entity.Ratings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Ratings, Long> {

    // 평점을 최신순으로 정렬
    List<Ratings> findByItemIdOrderByUpdatedAtDesc(Long itemId);

    List<Ratings> findByItemId(Long itemId);
    @Query("SELECT r FROM Ratings r WHERE r.users.userId = :userId")
    List<Ratings> findUser(Long userId);
}
