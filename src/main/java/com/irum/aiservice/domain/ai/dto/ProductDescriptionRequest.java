package com.irum.aiservice.domain.ai.dto;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDescriptionRequest {
    private UUID productId;
    private String productName;
    private UUID categoryId;
    private String tags;
}
