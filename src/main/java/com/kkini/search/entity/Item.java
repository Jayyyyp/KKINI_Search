package com.kkini.search.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/* NoArgsConstructor, @AllArgsConstructor, @Builder, @Getter, @ToString 등의 어노테이션은
   @Data로 대체할 수 있지만, @EqualsAndHashCode를 포함하므로, 연관관계에 있는 엔티티에는 주의가 필요하여
   그대로 유지
*/

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString(exclude = {"category"}) // 순환 참조 문제 방지
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long itemId;

    @ManyToOne // category 엔터티와의 관계, 한 상품이 하나의 카테고리에만 속할 수 있음
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(nullable = false)
    private Double averageRating;

    @Column(nullable = false)
    private String productImage;

    @Column(nullable = false)
    private long lowestPrice;

    @CreationTimestamp // 엔터티가 처음 저장될 떄 날짜 및 시간으로 자동 설정
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @CreationTimestamp // 엔터티가 업데이트될 때마다 현재 날짜 및 시간으로 자동 설정
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
