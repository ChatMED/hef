package io.chatmed.evaluation_platform.repository;

import io.chatmed.evaluation_platform.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    Long countAllBy();

    Optional<Question> findByText(String question);

    Optional<Question> findByQuestionKey(Long questionKey);

    Optional<Question> findFirstByOrderById();

    Optional<Question> findFirstByIdGreaterThan(Long id);

    Optional<Question> findFirstByIdLessThanOrderByIdDesc(Long id);
}

