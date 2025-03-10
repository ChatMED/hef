package io.chatmed.evaluation_platform.repository;

import io.chatmed.evaluation_platform.domain.Answer;
import io.chatmed.evaluation_platform.domain.Evaluation;
import io.chatmed.evaluation_platform.domain.Question;
import io.chatmed.evaluation_platform.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {

    Optional<Evaluation> findByAnswerAndUser(Answer answer, User user);

    List<Evaluation> findAllByQuestionAndUser(Question question, User user);
}
