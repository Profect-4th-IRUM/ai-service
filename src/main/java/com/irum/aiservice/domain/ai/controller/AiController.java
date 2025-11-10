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
@RequestMapping("/ai")
public class AiController {

    private final AiService aiService;

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
