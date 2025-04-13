package io.chatmed.evaluation_platform.repository;

import io.chatmed.evaluation_platform.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    Long countAllByWorkspaceId(Long workspaceId);

    Optional<Question> findByText(String question);

    Optional<Question> findByWorkspaceIdAndQuestionKey(Long workspaceId, Long questionKey);

    Optional<Question> findFirstByOrderById();

    Optional<Question> findFirstByWorkspaceIdOrderById(Long workspaceId);

    Optional<Question> findFirstByWorkspaceIdAndIdGreaterThan(Long workspaceId, Long id);

    Optional<Question> findFirstByWorkspaceIdAndIdLessThanOrderByIdDesc(Long workspaceId, Long id);
}

