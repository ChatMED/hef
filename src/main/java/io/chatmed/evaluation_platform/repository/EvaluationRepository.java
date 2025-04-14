package io.chatmed.evaluation_platform.repository;

import io.chatmed.evaluation_platform.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {

    Optional<Evaluation> findByAnswerAndUser(Answer answer, User user);

    List<Evaluation> findAllByQuestionAndUser(Question question, User user);

    List<Evaluation> findAllByUser(User user);

    List<Evaluation> findAllByWorkspaceAndUser(Workspace workspace, User user);
}
