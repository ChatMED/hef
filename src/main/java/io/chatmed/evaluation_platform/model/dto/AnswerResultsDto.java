package io.chatmed.evaluation_platform.model.dto;

import io.chatmed.evaluation_platform.model.Answer;
import jakarta.persistence.Column;

import java.util.ArrayList;
import java.util.List;

public class AnswerResultsDto {
    private Long id;
    private String model;

    @Column(length = 50000)
    private String text;

    @Column(length = 50000)
    private String comment;

    private Double accuracy;
    private Double relevance;
    private Double completeness;
    private Double safety;
    private Double bias;

    private String user;

    private List<AnswerDto> results = new ArrayList<>();

    public static AnswerResultsDto from(Answer answer) {
        AnswerResultsDto dto = new AnswerResultsDto();
        dto.setId(answer.getId());
        dto.setText(answer.getText());
        dto.setModel(answer.getModel().getName());

        return dto;
    }

    public AnswerResultsDto() {
    }

    public AnswerResultsDto(Long id, String model, String text, String comment, Double accuracy, Double relevance, Double completeness, Double safety, Double bias, String user, List<AnswerDto> results) {
        this.id = id;
        this.model = model;
        this.text = text;
        this.comment = comment;
        this.accuracy = accuracy;
        this.relevance = relevance;
        this.completeness = completeness;
        this.safety = safety;
        this.bias = bias;
        this.user = user;
        this.results = results;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(Double accuracy) {
        this.accuracy = accuracy;
    }

    public Double getRelevance() {
        return relevance;
    }

    public void setRelevance(Double relevance) {
        this.relevance = relevance;
    }

    public Double getCompleteness() {
        return completeness;
    }

    public void setCompleteness(Double completeness) {
        this.completeness = completeness;
    }

    public Double getSafety() {
        return safety;
    }

    public void setSafety(Double safety) {
        this.safety = safety;
    }

    public Double getBias() {
        return bias;
    }

    public void setBias(Double bias) {
        this.bias = bias;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public List<AnswerDto> getResults() {
        return results;
    }

    public void setResults(List<AnswerDto> results) {
        this.results = results;
    }
}
