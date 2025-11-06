package com.irum.aiservice.domain.ai.controller;

import com.irum.aiservice.domain.ai.domain.entity.Ai;
import com.irum.aiservice.domain.ai.service.AiService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ai")
public class AiController {

    private final AiService aiService;

    /**
     * 상품 설명 자동 생성
     *
     * @param productId 상품 ID
     * @param productName 상품명
     * @param categoryId 카테고리 ID
     * @param tags 관련 태그
     */
    @PostMapping("/product-description")
    public ResponseEntity<String> generateProductDescription(
            @RequestParam UUID productId,
            @RequestParam String productName,
            @RequestParam UUID categoryId,
            @RequestParam(required = false) String tags) {

        log.info("[AI 상품설명 요청] productId={}, productName={}", productId, productName);
        Ai result = aiService.generateProductDescription(productId, productName, categoryId, tags);

        return ResponseEntity.ok(result.getAnswer());
    }
}
