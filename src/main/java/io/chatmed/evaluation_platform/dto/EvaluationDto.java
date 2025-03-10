package io.chatmed.evaluation_platform.dto;

import io.chatmed.evaluation_platform.domain.Answer;
import io.chatmed.evaluation_platform.domain.Evaluation;
import io.chatmed.evaluation_platform.domain.User;
import io.chatmed.evaluation_platform.domain.valueobject.EvaluationMetrics;

public record EvaluationDto(
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
        Double perceivedUsefulness,
        String comment,
        String username,
        Long answer
) {

    public Evaluation toEvaluation(User user, Answer answer) {
        return Evaluation.builder().evaluationMetrics(new EvaluationMetrics(
                accuracy,
                comprehensiveness,
                clarity,
                empathy,
                bias,
                harm,
                trust,
                relevance,
                currency,
                securityAndPrivacy,
                perceivedUsefulness
        )).comment(comment).user(user).answer(answer).question(answer.getQuestion()).build();
    }
}
