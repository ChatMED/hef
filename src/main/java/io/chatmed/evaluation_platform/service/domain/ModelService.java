package io.chatmed.evaluation_platform.service.domain;

import io.chatmed.evaluation_platform.domain.Model;
import io.chatmed.evaluation_platform.domain.Workspace;
import io.chatmed.evaluation_platform.dto.ModelDto;

import java.util.List;
import java.util.Optional;

public interface ModelService {

    Optional<Model> findByWorkspaceAndName(Workspace workspace, String name);

    Optional<Model> findById(Long modelId);

    Optional<Model> findFirstModel(Long workspaceId);

    List<Model> findByWorkspaceIdAndNameIn(Workspace workspace, List<String> names);

    Model save(Model model);

    void createNewModels(List<String> modelNames, Workspace workspace);

    Long countAllModelsByWorkspaceId(Long workspaceId);

    List<Model> findAllByWorkspace(Long workspaceId);
}
