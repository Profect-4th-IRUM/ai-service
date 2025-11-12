package com.irum.aiservice.domain.ai.domain.repository;

import com.irum.aiservice.domain.ai.domain.entity.Ai;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AiRepository extends JpaRepository<Ai, UUID> {
    //    // 추가 메서드는 필요시에만 구현
    //    List<Ai> findByProductProductId(UUID productId);
}
