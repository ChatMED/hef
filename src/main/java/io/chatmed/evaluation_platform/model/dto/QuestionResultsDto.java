package io.chatmed.evaluation_platform.model.dto;

import io.chatmed.evaluation_platform.model.Question;
import jakarta.persistence.Column;

import java.util.List;

public class QuestionResultsDto {
    private Long id;
    @Column(length = 50000)
    private String text;

    private List<AnswerResultsDto> answers;

    public static QuestionResultsDto from(Question question, List<AnswerResultsDto> answers) {
        QuestionResultsDto dto = new QuestionResultsDto();
        dto.id = question.getId();
        dto.text = question.getText();
        dto.answers = answers;
        return dto;
    }

    public QuestionResultsDto(Long id, String text, List<AnswerResultsDto> answers) {
        this.id = id;
        this.text = text;
        this.answers = answers;
    }

    public QuestionResultsDto() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<AnswerResultsDto> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerResultsDto> answers) {
        this.answers = answers;
    }
}
