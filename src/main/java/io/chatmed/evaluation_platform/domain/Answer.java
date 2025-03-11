package io.chatmed.evaluation_platform.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "answers")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long answerKey;
    @Column(columnDefinition="TEXT")
    private String text;

    @ManyToOne
    private Question question;

    @ManyToOne
    private Model model;
}
