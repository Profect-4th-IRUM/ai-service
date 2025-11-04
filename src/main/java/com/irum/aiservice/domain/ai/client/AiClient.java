package com.irum.aiservice.domain.ai.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Component
@RequiredArgsConstructor
public class AiClient {

    private final WebClient webClient;

    @Value("${gemini.api.url}")
    private String apiUrl;

    @Value("${gemini.api.key}")
    private String apiKey;

    public String generateText(String prompt) {
        try {
            AiRequest request = new AiRequest(prompt);

            AiResponse response =
                    webClient
                            .post()
                            .uri(apiUrl + "?key=" + apiKey)
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(request)
                            .retrieve()
                            .bodyToMono(AiResponse.class)
                            .block();

            if (response != null
                    && response.candidates() != null
                    && !response.candidates().isEmpty()
                    && response.candidates().get(0).content() != null
                    && !response.candidates().get(0).content().parts().isEmpty()) {
                return response.candidates().get(0).content().parts().get(0).text();
            }

            return "AI 응답을 생성하지 못했습니다.";

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
