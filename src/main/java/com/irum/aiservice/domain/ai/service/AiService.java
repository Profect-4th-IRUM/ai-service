package com.irum.aiservice.domain.ai.service;

import com.irum.aiservice.domain.ai.client.AiClient;
import com.irum.aiservice.domain.ai.domain.entity.Ai;
import com.irum.aiservice.domain.ai.domain.repository.AiRepository;
import com.irum.come2us.domain.category.domain.entity.Category;
import com.irum.come2us.domain.category.domain.repository.CategoryRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AiService {

    private final AiRepository aiRepository;
    private final AiClient aiClient;
    private final CategoryRepository categoryRepository;

    // private final ProductFeignClient productFeignClient; // Feign

    public Ai generateProductDescription(
            UUID productId, String productName, UUID categoryId, String tags) {

        Category category =
                categoryRepository
                        .findById(categoryId)
                        .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다."));
        // 이것도 공통 예외?로 해야 하는지..?

        String fullCategoryPath = buildCategoryPath(category);
        log.info("상품 설명 생성 시작 - productId: {}, categoryPath: {}", productId, fullCategoryPath);

        String prompt = buildDetailedPrompt(productName, fullCategoryPath, tags);
        String generatedDescription = aiClient.generateText(prompt);

        Ai ai = Ai.create(prompt, generatedDescription, productId);
        return aiRepository.save(ai);
    }

    private String buildCategoryPath(Category category) {
        StringBuilder sb = new StringBuilder();
        Category current = category;
        while (current != null) {
            sb.insert(0, current.getName());
            if (current.getParent() != null) sb.insert(0, " > ");
            current = current.getParent();
        }
        return sb.toString();
    }

    private String buildDetailedPrompt(String productName, String categoryPath, String tags) {
        StringBuilder promptBuilder = new StringBuilder();
        promptBuilder.append("다음 상품의 매력적인 설명을 작성해주세요:\n\n");
        promptBuilder.append("상품명: ").append(productName).append("\n");
        promptBuilder.append("카테고리: ").append(categoryPath).append("\n");
        if (tags != null && !tags.isBlank()) {
            promptBuilder.append("관련 태그: ").append(tags).append("\n");
        }
        promptBuilder.append("\n설명은 자연스럽고 마케팅에 적합하게 작성해주세요.");
        return promptBuilder.toString();
    }
}
