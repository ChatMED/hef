package io.chatmed.evaluation_platform.model.dto;

import io.chatmed.evaluation_platform.model.Question;
import jakarta.persistence.Column;
import lombok.Data;

import java.util.List;

public class QuestionDto {
    private Long id;
    @Column(length = 50000)
    private String text;

    private List<AnswerDto> answers;

    public static QuestionDto from(Question question, List<AnswerDto> answers) {
        QuestionDto dto = new QuestionDto();
        dto.setId(question.getId());
        dto.setText(question.getText());
        dto.setAnswers(answers);

        return dto;
    }

    public QuestionDto () {}

    public QuestionDto(Long id, String text, List<AnswerDto> answers) {
        this.id = id;
        this.text = text;
        this.answers = answers;
    }

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

    public List<AnswerDto> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerDto> answers) {
        this.answers = answers;
    }
}
