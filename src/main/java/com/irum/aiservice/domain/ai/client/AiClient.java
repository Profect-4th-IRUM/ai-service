package com.irum.aiservice.domain.ai.client;

import com.irum.aiservice.domain.ai.dto.*;
import com.irum.aiservice.global.infrastructure.properties.GeminiApiProperty;
import com.irum.aiservice.global.advice.exception.CommonException;
import com.irum.aiservice.global.advice.exception.errorcode.AiErrorCode;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class AiClient {

    private final GeminiApiProperty geminiApiProperty;
    private final RestTemplate restTemplate;

    public String generateText(String prompt) {
        AiRequest request = new AiRequest(prompt);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<AiRequest> entity = new HttpEntity<>(request, headers);
        String apiUrl = geminiApiProperty.getUrl();
        String apiKey = geminiApiProperty.getKey();

        try {
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
                    .orElseThrow(() -> new CommonException(AiErrorCode.AI_RESPONSE_EMPTY));

        } catch (ResourceAccessException e) {
            log.error("AI API 호출 시간 초과: {}", e.getMessage(), e);
            throw new CommonException(AiErrorCode.AI_TIMEOUT);

        } catch (HttpClientErrorException | HttpServerErrorException e) {
            log.error("AI API 호출 실패 (HTTP 에러): {}", e.getMessage(), e);
            throw new CommonException(AiErrorCode.AI_API_UNAVAILABLE);

        } catch (Exception e) {
            log.error("AI 설명 생성 중 예기치 못한 오류: {}", e.getMessage(), e);
            throw new CommonException(AiErrorCode.AI_GENERATION_FAILED);
        }
    }
}
