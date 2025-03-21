package io.chatmed.evaluation_platform.repository;

import io.chatmed.evaluation_platform.domain.Model;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ModelRepository extends JpaRepository<Model, Long> {

    Optional<Model> findByName(String name);

    List<Model> findByNameIn(List<String> names);

    List<Model> findAllByOrderByIdAsc();

    Optional<Model> findFirstByOrderByIdAsc();
}
