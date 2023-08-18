package com.kkini.search.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity@NoArgsConstructor@AllArgsConstructor@Builder@Getter@ToString@Setter
public class Ratings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ratingId;

    @Column(name = "item_id")
    private Long itemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users users;

    @Column(name = "rating_value")
    private int ratingValue;

    @Column(name = "rating_text")
    private String ratingText;

    @Column(name = "rating_image1", nullable = true)
    private String ratingImage1;
    @Column(name = "rating_image2", nullable = true)
    private String ratingImage2;
    @Column(name = "rating_image3", nullable = true)
    private String ratingImage3;
    @Column(name = "rating_image4", nullable = true)
    private String ratingImage4;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();
}