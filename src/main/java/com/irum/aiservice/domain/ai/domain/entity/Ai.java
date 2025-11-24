package com.irum.aiservice.domain.ai.domain.entity;

import com.irum.global.domain.BaseTimeEntity;
import jakarta.persistence.*;
import java.util.UUID;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.annotations.Where;

@Getter
@Entity
@Table(name = "p_ai") // p_ai_description
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Where(clause = "deleted_at IS NULL")
public class Ai extends BaseTimeEntity {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    @Column(name = "ai_id", updatable = false, nullable = false)
    private UUID aiId;

    @Lob
    @Column(nullable = false)
    private String question;

    @Lob
    @Column(nullable = false)
    private String answer;

    @Column(name = "product_id", nullable = false)
    private UUID productId;

    public static Ai create(String question, String answer, UUID productId) {
        return Ai.builder().question(question).answer(answer).productId(productId).build();
    }
}
