package io.chatmed.evaluation_platform.service.domain;

import io.chatmed.evaluation_platform.domain.Answer;
import io.chatmed.evaluation_platform.domain.Evaluation;
import io.chatmed.evaluation_platform.domain.Question;
import io.chatmed.evaluation_platform.domain.User;

import java.util.List;
import java.util.Optional;

public interface EvaluationService {

    void save(Evaluation evaluation);

    Optional<Evaluation> findEvaluationForAnswerAndUser(Answer answer, User user);

    List<Evaluation> findAllEvaluationsForQuestionAndUser(Question question, User user);

    List<Evaluation> findAllEvaluationsForUser(User user);
}
