package io.chatmed.evaluation_platform.repository;

import io.chatmed.evaluation_platform.model.Answer;
import io.chatmed.evaluation_platform.model.Score;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScoreRepository extends JpaRepository<Score,Long> {

    List<Score> findAllByAnswer(Answer answer);
}
