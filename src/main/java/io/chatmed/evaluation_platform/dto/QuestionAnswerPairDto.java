package io.chatmed.evaluation_platform.dto;


import java.util.List;

public record QuestionAnswerPairDto(
        QuestionDto question,
        AnswerDto answer,
        List<ModelDto> evaluatedModels,
        long evaluatedQuestions,
        long remainingQuestions
) {

}
