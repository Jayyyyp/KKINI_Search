package com.kkini.search.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity@ToString
@NoArgsConstructor@AllArgsConstructor
@Builder@Getter
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    @Column(nullable = false)
    private String category;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private double averageRating;
    @Column(nullable = false)
    private String productImage;
    @Column(nullable = false)
    private long lowestPrice;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    public void setDefaultValue(){
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void setUpdatedValue(){
        this.updatedAt = LocalDateTime.now();
    }
}
