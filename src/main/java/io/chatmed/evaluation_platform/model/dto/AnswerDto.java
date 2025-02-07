package io.chatmed.evaluation_platform.model.dto;

import io.chatmed.evaluation_platform.model.Answer;
import jakarta.persistence.Column;

public class AnswerDto {
    private Long id;
    private String model;

    @Column(length = 50000)
    private String text;

    @Column(length = 50000)
    private String comment;

    private Double accuracy;
    private Double comprehensiveness;
    private Double clarity;
    private Double empathy;
    private Double bias;
    private Double harm;
    private Double trust;

    private String user;

    public static AnswerDto from(Answer answer) {
        AnswerDto dto = new AnswerDto();
        dto.setId(answer.getId());
        dto.setText(answer.getText());
        dto.setModel(answer.getModel().getName());

        return dto;
    }

    public AnswerDto() {}

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

    public Double getComprehensiveness() {
        return comprehensiveness;
    }

    public void setComprehensiveness(Double comprehensiveness) {
        this.comprehensiveness = comprehensiveness;
    }

    public Double getClarity() {
        return clarity;
    }

    public void setClarity(Double clarity) {
        this.clarity = clarity;
    }

    public Double getEmpathy() {
        return empathy;
    }

    public void setEmpathy(Double empathy) {
        this.empathy = empathy;
    }

    public Double getBias() {
        return bias;
    }

    public void setBias(Double bias) {
        this.bias = bias;
    }

    public Double getHarm() {
        return harm;
    }

    public void setHarm(Double harm) {
        this.harm = harm;
    }

    public Double getTrust() {
        return trust;
    }

    public void setTrust(Double trust) {
        this.trust = trust;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
