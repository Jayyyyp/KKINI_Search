//package com.kkini.search.service;
//
//import org.springframework.http.*;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//@Service
//public class NaverSearchService {
//
//    private final String API_URL = "YOUR_NAVER_CLOUD_SEARCH_API_ENDPOINT";
//    private final String API_KEY = "YOUR_API_KEY";
//
//    public String search(String query) {
//        RestTemplate restTemplate = new RestTemplate();
//
//        // 헤더 설정
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("X-NCP-APIGW-API-KEY-ID", API_KEY);
//        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
//
//        // API 호출
//        ResponseEntity<String> response = restTemplate.exchange(API_URL + "?query=" + query, HttpMethod.GET, entity, String.class);
//
//        if (response.getStatusCode() == HttpStatus.OK) {
//            return response.getBody();
//        } else {
//            throw new RuntimeException("Failed to fetch results from Naver Cloud Search");
//        }
//    }
//}
