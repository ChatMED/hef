package io.chatmed.evaluation_platform.dto;

import io.chatmed.evaluation_platform.domain.Question;

import java.util.List;
import java.util.stream.Collectors;

public record QuestionDto(Long id, String text) {

    public static QuestionDto from(Question question) {
        return new QuestionDto(question.getId(), question.getText());
    }

    public Question to(QuestionDto questionDto) {
        return Question.builder().text(questionDto.text()).build();
    }

    public static List<QuestionDto> from(List<Question> questions) {
        return questions.stream().map(QuestionDto::from).collect(Collectors.toList());
    }
}
