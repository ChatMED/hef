package io.chatmed.evaluation_platform.repository;

import io.chatmed.evaluation_platform.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    Optional<Question> findByText(String question);

    Optional<Question> findByQuestionKey(Long questionKey);

    Optional<Question> findFirstByOrderById();

    Optional<Question> findFirstByIdGreaterThan(Long id);

    @Query("SELECT COUNT(q) FROM Question q WHERE q.id < :id")
    Long countQuestionsEvaluated(@Param("id") Long currentQuestionId);

    @Query("SELECT COUNT(q) FROM Question q WHERE q.id >= :id")
    Long countRemainingQuestions(@Param("id") Long currentQuestionId);
}

