package com.irum.aiservice.domain.ai.dto;

import java.util.List;

public record AiRequest(List<Content> contents) {

    public AiRequest(String text) {
        this(List.of(new Content(List.of(new Part(text)))));
    }
}
