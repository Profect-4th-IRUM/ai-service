package com.irum.aiservice.domain.ai.controller;

import com.irum.aiservice.domain.ai.domain.entity.Ai;
import com.irum.aiservice.domain.ai.dto.ProductDescriptionRequest;
import com.irum.aiservice.domain.ai.dto.ProductDescriptionResponse;
import com.irum.aiservice.domain.ai.service.AiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/ai")
public class AiController {

    private final AiService aiService;

    @PostMapping("/product-description")
    public ProductDescriptionResponse generateProductDescription(
            @RequestBody ProductDescriptionRequest request) {

        log.info(
                "[AI 상품설명 요청] productId={}, productName={}",
                request.getProductId(),
                request.getProductName());

        Ai ai =
                aiService.generateProductDescription(
                        request.getProductId(),
                        request.getProductName(),
                        request.getCategoryId(),
                        request.getTags());

        return new ProductDescriptionResponse(ai.getAnswer());
    }
}
