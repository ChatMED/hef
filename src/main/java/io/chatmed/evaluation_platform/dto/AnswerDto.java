package io.chatmed.evaluation_platform.dto;

import io.chatmed.evaluation_platform.domain.Answer;

public record AnswerDto(Long id, String text, ModelDto model) {

    public static AnswerDto from(Answer answer) {
        return new AnswerDto(answer.getId(), answer.getText(), ModelDto.from(answer.getModel()));
    }
}
