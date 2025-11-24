package com.irum.aiservice.domain.ai.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDescriptionRequest {
    private String productName;
    private String categoryName;
    private List<String> categoryParents;
    private List<String> categoryChildren;
}
