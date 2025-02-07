package io.chatmed.evaluation_platform.model;

import jakarta.persistence.*;

@Entity
@Table(name="score")
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Answer answer;

    private Double accuracy;
    private Double comprehensiveness;
    private Double clarity;
    private Double empathy;
    private Double bias;
    private Double harm;
    private Double trust;

    private String user;

    @Column(length = 10000)
    private String feedback;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public Double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(Double accuracy) {
        this.accuracy = accuracy;
    }

    public Double getBias() {
        return bias;
    }

    public void setBias(Double bias) {
        this.bias = bias;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
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
}
