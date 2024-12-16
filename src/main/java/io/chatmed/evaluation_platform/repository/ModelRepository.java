package io.chatmed.evaluation_platform.repository;

import io.chatmed.evaluation_platform.model.Model;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ModelRepository extends JpaRepository<Model, Long> {
    Optional<Model> findByName(String chatgpt);
}
