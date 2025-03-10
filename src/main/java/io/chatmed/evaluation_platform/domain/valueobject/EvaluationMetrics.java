package io.chatmed.evaluation_platform.domain.valueobject;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public record EvaluationMetrics(
        Double accuracy,
        Double comprehensiveness,
        Double clarity,
        Double empathy,
        Double bias,
        Double harm,
        Double trust,
        Double relevance,
        Double currency,
        Double securityAndPrivacy,
        Double perceivedUsefulness
) implements Serializable {
    public EvaluationMetrics() {
        this(null, null, null, null, null, null, null, null, null, null, null);
    }
}

