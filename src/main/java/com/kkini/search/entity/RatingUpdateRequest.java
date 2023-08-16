package com.kkini.search.entity;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class RatingUpdateRequest {
    private Long userId;
    private int ratingValue;
    private String ratingText;
}
