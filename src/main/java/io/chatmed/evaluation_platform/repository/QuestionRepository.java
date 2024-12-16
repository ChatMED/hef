package io.chatmed.evaluation_platform.repository;

import io.chatmed.evaluation_platform.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    Optional<Question> findByText(String question);
}
