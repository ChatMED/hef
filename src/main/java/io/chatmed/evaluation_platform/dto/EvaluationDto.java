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
        Double understanding,
        Double relevance,
        Double currency,
        Double reasoning,
        Double factualityVerification,
        Double fabrication,
        Double falsification,
        Double plagiarism,
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
                understanding,
                relevance,
                currency,
                reasoning,
                factualityVerification,
                fabrication,
                falsification,
                plagiarism
        )).comment(comment).user(user).answer(answer).question(answer.getQuestion()).build();
    }

    public static EvaluationDto fromEvaluation(Evaluation evaluation) {
        if (evaluation == null) return null;

        return new EvaluationDto(
                evaluation.getEvaluationMetrics().accuracy(),
                evaluation.getEvaluationMetrics().comprehensiveness(),
                evaluation.getEvaluationMetrics().clarity(),
                evaluation.getEvaluationMetrics().empathy(),
                evaluation.getEvaluationMetrics().bias(),
                evaluation.getEvaluationMetrics().harm(),
                evaluation.getEvaluationMetrics().understanding(),
                evaluation.getEvaluationMetrics().relevance(),
                evaluation.getEvaluationMetrics().currency(),
                evaluation.getEvaluationMetrics().reasoning(),
                evaluation.getEvaluationMetrics().factualityVerification(),
                evaluation.getEvaluationMetrics().fabrication(),
                evaluation.getEvaluationMetrics().falsification(),
                evaluation.getEvaluationMetrics().plagiarism(),
                evaluation.getComment(),
                evaluation.getUser().getUsername(),
                evaluation.getAnswer().getId()
        );
    }
}
