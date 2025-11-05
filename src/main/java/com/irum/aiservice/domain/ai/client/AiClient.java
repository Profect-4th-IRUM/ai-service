package com.irum.aiservice.domain.ai.client;

import com.irum.aiservice.global.infrastructure.properties.GeminiApiPropertyConfig;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class AiClient {

    private final GeminiApiPropertyConfig config;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${gemini.api.url}")
    private String apiUrl;

    @Value("${gemini.api.key}")
    private String apiKey;

    public String generateText(String prompt) {
        try {
            AiRequest request = new AiRequest(prompt);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<AiRequest> entity = new HttpEntity<>(request, headers);

            ResponseEntity<AiResponse> response =
                    restTemplate.exchange(
                            apiUrl + "?key=" + apiKey, HttpMethod.POST, entity, AiResponse.class);

            return Optional.ofNullable(response.getBody())
                    .map(AiResponse::candidates)
                    .filter(candidates -> !candidates.isEmpty())
                    .map(candidates -> candidates.get(0))
                    .map(Candidate::content)
                    .map(Content::parts)
                    .flatMap(parts -> parts.stream().findFirst())
                    .map(Part::text)
                    .orElse("AI 응답을 생성하지 못했습니다.");

        } catch (Exception e) {
            log.error("Ai API 호출 실패: {}", e.getMessage(), e);
            return "AI 설명 생성 중 오류가 발생했습니다.";
        }
    }

    // --- 내부 DTO 클래스 ---
    record AiRequest(java.util.List<Content> contents) {
        AiRequest(String text) {
            this(java.util.List.of(new Content(java.util.List.of(new Part(text)))));
        }
    }

    record Content(java.util.List<Part> parts) {}

    record Part(String text) {}

    record AiResponse(java.util.List<Candidate> candidates) {}

    record Candidate(Content content) {}
}
