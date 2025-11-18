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
@RequestMapping("/internal")
public class AiController {

    private final AiService aiService;

    @PostMapping("/product-descriptions")
    public ProductDescriptionResponse generateProductDescription(
            @RequestBody ProductDescriptionRequest request) {

        log.info(
                "[AI 상품설명 요청] productName={}, categoryName={}",
                request.getProductName(),
                request.getCategoryName());

        Ai ai =
                aiService.generateProductDescription(
                        request.getProductName(),
                        request.getCategoryName(),
                        request.getCategoryParents(),
                        request.getCategoryChildren());

        return new ProductDescriptionResponse(ai.getAnswer());
    }
}
