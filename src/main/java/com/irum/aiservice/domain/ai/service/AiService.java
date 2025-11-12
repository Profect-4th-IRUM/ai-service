package com.irum.aiservice.domain.ai.service;

import com.irum.aiservice.domain.ai.client.AiClient;
import com.irum.aiservice.domain.ai.domain.entity.Ai;
import com.irum.aiservice.domain.ai.domain.repository.AiRepository;
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

    // TODO: 추후 ProductFeignClient 실제 구현 완료되면, 해당 부분과 다른 점 없도록 검토!

    //    private final ProductFeignClient productFeignClient;      // Feign

    public Ai generateProductDescription(
            UUID productId, String productName, UUID categoryId, String tags) {

        //        String categoryPath = productFeignClient.getCategoryPath(categoryId);     // Feign
        String categoryPath = "임시/카테고리/경로"; // 임시 카테고리 경로 사용

        log.info("상품 설명 생성 시작 - productId: {}, categoryPath: {}", productId, categoryPath);
        String prompt = buildDetailedPrompt(productName, categoryPath, tags);

        String generatedDescription = aiClient.generateText(prompt);

        String cleanedDescription =
                generatedDescription
                        .replace("\n", " ") // 줄바꿈 제거
                        .replace("\r", " ") // 캐리지리턴 제거 (윈도우 호환)
                        .trim(); // 앞뒤 공백 제거

        Ai aiGeneratedDescription = Ai.create(prompt, generatedDescription, productId);
        return aiRepository.save(aiGeneratedDescription);
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

        promptBuilder.append(
                "\n\n출력 규칙:\n"
                        + "1. 줄바꿈(\\n) 없이 한 문단으로 작성하세요.\n"
                        + "2. 이모지(🎧, 😊 등)와 특수기호(*, #, -, _)는 사용하지 마세요.\n"
                        + "3. 마크다운(**bold**, _italic_ 등) 형식은 사용하지 마세요.\n"
                        + "4. 결과는 JSON 형태로 파싱될 수 있도록 순수 텍스트만 포함하세요.\n"
                        + "5. 설명은 한국어로 작성하고, 3문장 이내로 요약하세요.");

        return promptBuilder.toString();
    }
}
