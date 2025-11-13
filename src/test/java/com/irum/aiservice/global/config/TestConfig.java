package com.irum.aiservice.global.config;

import com.irum.global.infrastructure.config.GlobalAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import(GlobalAutoConfiguration.class)
public class TestConfig {}
