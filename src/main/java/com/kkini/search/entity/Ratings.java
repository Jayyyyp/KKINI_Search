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

    @Column(name = "tempUserId")
    private String tempUserId;

    @Column(name = "rating_value")
    private int ratingValue;

    @Column(name = "rating_text")
    private String ratingText;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();
}
