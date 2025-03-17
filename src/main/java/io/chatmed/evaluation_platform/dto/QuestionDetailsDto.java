package io.chatmed.evaluation_platform.dto;

import java.util.List;

public record QuestionDetailsDto(
        QuestionDto question,
        AnswerDto answer,
        List<ModelDto> evaluatedModels,
        long evaluatedQuestions,
        long remainingQuestions,
        EvaluationDto evaluation
) {

}
