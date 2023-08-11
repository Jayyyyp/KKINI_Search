package com.kkini.search.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity@ToString@NoArgsConstructor@AllArgsConstructor@Builder@Getter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long categoryId;

    @Column(nullable = false)
    private String categoryName;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Item> items;
}
