package io.chatmed.evaluation_platform.domain;

import io.chatmed.evaluation_platform.domain.valueobject.EvaluationMetrics;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "evaluations")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Evaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10000)
    private String comment;

    @Embedded
    private EvaluationMetrics evaluationMetrics;

    @ManyToOne
    private User user;

    @ManyToOne
    private Question question;

    @ManyToOne
    private Answer answer;
}
