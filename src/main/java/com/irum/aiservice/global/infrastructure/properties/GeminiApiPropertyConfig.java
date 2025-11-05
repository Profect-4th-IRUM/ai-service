package com.irum.aiservice.global.infrastructure.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "gemini.api")
@Getter
@Setter
@EnableConfigurationProperties({
    com.irum.come2us.global.infrastructure.properties.JwtProperties.class,
    RedisProperties.class,
    TossProperties.class,
    com.irum.come2us.global.infrastructure.properties.FileProperties.class
})
public class GeminiApiPropertyConfig {
    private String url;
    private String key;
}
