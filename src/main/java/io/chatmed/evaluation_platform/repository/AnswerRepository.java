package io.chatmed.evaluation_platform.repository;

import io.chatmed.evaluation_platform.model.Answer;
import io.chatmed.evaluation_platform.model.Model;
import io.chatmed.evaluation_platform.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    Optional<Answer> findByQuestion(Question question);

    Optional<Answer> findByQuestionAndModel(Question q, Model m);
}
