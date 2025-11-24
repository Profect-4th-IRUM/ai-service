package com.irum.aiservice.domain.ai.service;

import com.irum.aiservice.domain.ai.client.AiClient;
import com.irum.aiservice.domain.ai.domain.entity.Ai;
import com.irum.aiservice.domain.ai.domain.repository.AiRepository;
import java.util.List;
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

    public Ai generateProductDescription(
            String productName,
            String categoryName,
            List<String> categoryParents,
            List<String> categoryChildren) {

        // 프롬프트 생성
        String prompt =
                buildDetailedPrompt(productName, categoryName, categoryParents, categoryChildren);
        log.info("상품 설명 생성 시작 - productName: {}, category: {}", productName, categoryName);

        // AI 서비스 호출
        String generatedDescription = aiClient.generateText(prompt);

        String cleanedDescription =
                generatedDescription.replace("\n", " ").replace("\r", " ").trim();

        // Ai 엔티티 생성 및 저장
        Ai aiGeneratedDescription =
                Ai.create(prompt, cleanedDescription, null); // productId 없으면 null
        return aiRepository.save(aiGeneratedDescription);
    }

    private String buildDetailedPrompt(
            String productName,
            String categoryName,
            List<String> categoryParents,
            List<String> categoryChildren) {

        StringBuilder promptBuilder = new StringBuilder();
        promptBuilder.append("당신은 전문 전자상거래 마케터입니다.\n");
        promptBuilder.append("아래 정보를 참고하여 고객의 구매를 유도할 수 있는 상품 설명을 작성하세요.\n");
        promptBuilder.append("설명은 간결하면서도 감성적으로 표현하여, 상품의 장점을 부각시켜 작성해주세요:\n\n");
        promptBuilder.append("상품명: ").append(productName).append("\n");
        promptBuilder.append("카테고리: ").append(categoryName).append("\n");

        if (categoryParents != null && !categoryParents.isEmpty()) {
            promptBuilder
                    .append("상위 카테고리: ")
                    .append(String.join(", ", categoryParents))
                    .append("\n");
        }
        if (categoryChildren != null && !categoryChildren.isEmpty()) {
            promptBuilder
                    .append("하위 카테고리: ")
                    .append(String.join(", ", categoryChildren))
                    .append("\n");
        }

        promptBuilder.append(
                "\n출력 규칙:\n"
                        + "1. 줄바꿈 없이 한 문단으로 작성하세요.\n"
                        + "2. 이모지, 특수기호, 마크다운은 사용하지 마세요.\n"
                        + "3. 결과는 JSON 형태로 파싱될 수 있도록 순수 텍스트만 포함하세요.\n"
                        + "4. 설명은 한국어로 작성하고, 3문장 이내로 요약하세요.");

        return promptBuilder.toString();
    }
}
