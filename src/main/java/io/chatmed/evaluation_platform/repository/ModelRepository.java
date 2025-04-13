package io.chatmed.evaluation_platform.repository;

import io.chatmed.evaluation_platform.domain.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ModelRepository extends JpaRepository<Model, Long> {

    Optional<Model> findByWorkspaceIdAndName(Long workspaceId, String name);

    List<Model> findByWorkspaceIdAndNameIn(long workSpaceId, List<String> names);

    List<Model> findAllByOrderByIdAsc();

    Optional<Model> findFirstByWorkspaceIdOrderByIdAsc(Long workspaceId);

    long countByWorkspaceId(Long workspaceId);

    List<Model> findByWorkspaceId(Long workspaceId);
}
