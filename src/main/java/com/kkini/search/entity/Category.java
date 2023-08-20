package com.kkini.search.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@ToString(exclude = {"parent", "subCategories", "items"}) // 순환 참조 문제 방지
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId; // 1 - KKINI, 2 - KKINI GREEN

    @ManyToOne(fetch = FetchType.LAZY) // 지연 로딩 사용하여 성능 향상
    @JoinColumn(name = "parent_id")
    private Category parent; // 상위 카테고리

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    // orphanRemoval=true는 부모 카테고리에서 하위 카테고리를 삭제할때 DB에서 해당 하위 카테고리가 삭제되게 하기
    private List<Category> subCategories = new ArrayList<>();

    @Column(nullable = false)
    private String categoryName; // 소분류 카테고리 이름

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Item> items = new ArrayList<>();
}
