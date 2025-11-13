package com.irum.aiservice.global.infrastructure.config;

import com.irum.aiservice.global.infrastructure.properties.GeminiApiProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({GeminiApiProperty.class})
public class PropertiesConfig {}
