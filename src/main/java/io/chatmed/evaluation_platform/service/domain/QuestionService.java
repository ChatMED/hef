package io.chatmed.evaluation_platform.service.domain;

import io.chatmed.evaluation_platform.domain.Question;
import io.chatmed.evaluation_platform.domain.User;

import java.util.List;
import java.util.Optional;

public interface QuestionService {

    Optional<Question> findById(Long id);

    List<Question> findAll();

    Optional<Question> findFirstQuestion();

    Optional<Question> findQuestionToEvaluate(User user);

    Optional<Question> findPreviousQuestion(Long id);

    Optional<Question> findNextQuestion(Long id);

    Optional<Question> findByQuestionKey(Long questionKey);

    Optional<Question> findByText(String text);

    Optional<Question> save(Question question);

    Long countEvaluatedQuestions(User user);

    Long countRemainingQuestions(User user);
}
