package com.kkini.search.controller;

import com.kkini.search.entity.Item;
import com.kkini.search.entity.Ratings;
import com.kkini.search.service.ItemService;
import com.kkini.search.service.RatingService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;


@Controller
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    // HTTP 요청과 같은 웹 관련 로직

    private final ItemService itemService;

    @Value("${app.image-storage-path}")
    private Path imageStroagePath; // 이미지가 저장된 경로 주입
    private static final Logger logger = LoggerFactory.getLogger(ItemController.class);


    // 검색 기능 구현
    @GetMapping("/search")
    public Object searchItems(@RequestParam(required = false) String name,
                              @RequestParam(required = false) Long categoryId,
                              Model model, HttpServletRequest request) {

        if (isAjaxRequest(request)) {
            return itemService.autoCompleteNames(name);
        }

        if (name == null && categoryId == null) {
            model.addAttribute("items", Collections.emptyList());
            return "search";
        }

        List<Item> items = itemService.searchItemsByName(name, categoryId);
        model.addAttribute("items", items);

        return items.isEmpty() ? "noItems" : "searchResults";
    }

    private boolean isAjaxRequest(HttpServletRequest request) {
        String requestedWithHeader = request.getHeader("X-Requested-With");
        return "XMLHttpRequest".equals(requestedWithHeader);
    }

    @GetMapping("/{itemId}")
    public String showItemDetail(@PathVariable Long itemId, Model model) {
        Item item = itemService.getItemById(itemId);
        if (item == null) {
            return "errorPage";
        }
        model.addAttribute("item", item);
        return "itemDetail";
    }

    @GetMapping("/images/{filename:.+}")
    public ResponseEntity<Resource> serveImage(@PathVariable String filename) {
        Path filePath = resolveImagePath(filename);
        Resource resource = getResource(filePath);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    private Path resolveImagePath(String filename) {
        Path file = imageStroagePath.resolve(filename);
        logger.info("이미지 경로 : {}", file.toString());
        return file;
    }

    private Resource getResource(Path filePath) {
        try {
            Resource resource = new UrlResource(filePath.toUri());
            if (!resource.exists() || !resource.isReadable()) {
                throw new RuntimeException("파일을 읽을 수 없습니다: " + filePath.getFileName());
            }
            return resource;
        } catch (MalformedURLException e) {
            throw new RuntimeException("오류: " + e.getMessage());
        }
    }


}