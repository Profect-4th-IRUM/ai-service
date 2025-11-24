package com.irum.aiservice.domain.ai.domain.repository;

import com.irum.aiservice.domain.ai.domain.entity.Ai;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AiRepository extends JpaRepository<Ai, UUID> {}
