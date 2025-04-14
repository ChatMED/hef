package io.chatmed.evaluation_platform.service.domain;

import io.chatmed.evaluation_platform.domain.Membership;
import io.chatmed.evaluation_platform.domain.Question;
import io.chatmed.evaluation_platform.domain.User;
import io.chatmed.evaluation_platform.domain.Workspace;

import java.util.List;
import java.util.Optional;

public interface QuestionService {

    Optional<Question> findById(Long id);

    Optional<Question> findFirstQuestion(Long workspaceId);

    Optional<Question> findPreviousQuestion(Long id, Long workspaceId);

    Optional<Question> findNextQuestion(Long id, Long workspaceId);

    Optional<Question> findByWorkspaceAndQuestionKey(Workspace workspace, Long questionKey);

    Optional<Question> findByText(String text);

    Question save(Question question);

    Long countEvaluatedQuestions(Membership membership);

    Long countRemainingQuestions(Membership membership);

    Long countByWorkspaceId(Long workspaceId);
}
