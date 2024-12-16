package io.chatmed.evaluation_platform.repository;

import io.chatmed.evaluation_platform.model.Score;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScoreRepository extends JpaRepository<Score,Long> {
}
