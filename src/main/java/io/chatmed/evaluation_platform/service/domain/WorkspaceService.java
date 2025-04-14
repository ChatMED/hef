package io.chatmed.evaluation_platform.service.domain;

import io.chatmed.evaluation_platform.domain.Workspace;

import java.util.List;
import java.util.Optional;

public interface WorkspaceService {

    Workspace save(Workspace workspace);

    List<Workspace> findAll();

    Optional<Workspace> findById(Long id);
}
