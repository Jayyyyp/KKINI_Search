package com.kkini.search.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity@NoArgsConstructor@AllArgsConstructor@Builder@Getter@Setter
@ToString(exclude = "ratings") // 순환 참조를 피하기 위해 ratings 제외
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String userName;

    @OneToMany(mappedBy = "users", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Ratings> ratings; // Ratings 와의 양방향 관계 설정
}
