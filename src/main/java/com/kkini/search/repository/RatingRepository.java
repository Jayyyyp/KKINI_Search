package com.kkini.search.repository;

import com.kkini.search.entity.Ratings;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

    @Modifying
    @Transactional
    @Query("UPDATE Ratings r SET r.ratingImage1 = NULL WHERE r.ratingImage1 = ?1")
    void deleteRatingImage1ByPath(String imagePath);

    @Modifying
    @Transactional
    @Query("UPDATE Ratings r SET r.ratingImage2 = NULL WHERE r.ratingImage2 = ?1")
    void deleteRatingImage2ByPath(String imagePath);

    @Modifying
    @Transactional
    @Query("UPDATE Ratings r SET r.ratingImage3 = NULL WHERE r.ratingImage3 = ?1")
    void deleteRatingImage3ByPath(String imagePath);

    @Modifying
    @Transactional
    @Query("UPDATE Ratings r SET r.ratingImage4 = NULL WHERE r.ratingImage4 = ?1")
    void deleteRatingImage4ByPath(String imagePath);
}
