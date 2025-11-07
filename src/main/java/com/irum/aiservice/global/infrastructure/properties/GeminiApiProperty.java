package com.irum.aiservice.global.infrastructure.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "gemini.api")
public class GeminiApiProperty {
    private String url;
    private String key;
}
