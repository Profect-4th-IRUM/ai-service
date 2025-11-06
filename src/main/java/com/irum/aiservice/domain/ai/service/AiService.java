package com.irum.aiservice.domain.ai.service;

import com.irum.aiservice.domain.ai.client.AiClient;
import com.irum.aiservice.domain.ai.domain.entity.Ai;
import com.irum.aiservice.domain.ai.domain.repository.AiRepository;
import com.irum.aiservice.global.presentation.advice.exception.CommonException;
import com.irum.aiservice.global.presentation.advice.exception.errorcode.AiErrorCode;
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

    //    private final ProductFeignClient productFeignClient; // Feign

    public Ai generateProductDescription(
            UUID productId, String productName, UUID categoryId, String tags) {

        String categoryPath = productFeignClient.getCategoryPath(categoryId);

        log.info("상품 설명 생성 시작 - productId: {}, categoryPath: {}", productId, categoryPath);
        String prompt = buildDetailedPrompt(productName, categoryPath, tags);

        String generatedDescription = aiClient.generateText(prompt);
        if (generatedDescription == null || generatedDescription.equals("AI 설명 생성 중 오류가 발생했습니다.")) {
            throw new CommonException(AiErrorCode.AI_GENERATION_FAILED);
        }

        Ai ai = Ai.create(prompt, generatedDescription, productId);
        return aiRepository.save(ai);
    }

    private String buildDetailedPrompt(String productName, String categoryPath, String tags) {
        StringBuilder promptBuilder = new StringBuilder();
        promptBuilder.append("당신은 전문 전자상거래 마케터입니다.\n");
        promptBuilder.append("아래 정보를 참고하여 고객의 구매를 유도할 수 있는 상품 설명을 작성하세요.\n");
        promptBuilder.append("설명은 간결하면서도 감성적으로 표현하여, 상품의 장점을 부각시켜 작성해주세요:\n\n");
        promptBuilder.append("상품명: ").append(productName).append("\n");
        promptBuilder.append("카테고리: ").append(categoryPath).append("\n");
        if (tags != null && !tags.isBlank()) {
            promptBuilder.append("관련 태그: ").append(tags).append("\n");
        }
        promptBuilder.append("\n설명은 한국어 작성하고, 3문장 이내 요약된 설명을 작성해주세요.");
        return promptBuilder.toString();
    }
}
