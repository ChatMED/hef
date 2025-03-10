package io.chatmed.evaluation_platform.service.domain;

import io.chatmed.evaluation_platform.domain.Answer;
import io.chatmed.evaluation_platform.domain.Model;
import io.chatmed.evaluation_platform.domain.Question;
import io.chatmed.evaluation_platform.domain.User;

import java.util.List;
import java.util.Optional;

public interface AnswerService {

    Optional<Answer> findById(Long id);

    Optional<Answer> findByQuestionAndModel(Question question, Model model);

    Optional<Answer> findAnswerToEvaluate(Question question, User user);

    Optional<Answer> save(Answer answer);

}
