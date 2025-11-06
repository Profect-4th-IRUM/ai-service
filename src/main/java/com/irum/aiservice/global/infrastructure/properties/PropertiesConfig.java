package com.irum.aiservice.global.infrastructure.properties;

import com.irum.aiservice.domain.ai.property.GeminiApiProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({
    //        JwtProperties.class,
    RedisProperties.class,
    TossProperties.class,
    //        FileProperties.class,
    GeminiApiProperty.class
})
public class PropertiesConfig {}
