package io.chatmed.evaluation_platform.service.domain.impl;

import io.chatmed.evaluation_platform.domain.Model;
import io.chatmed.evaluation_platform.domain.Workspace;
import io.chatmed.evaluation_platform.repository.ModelRepository;
import io.chatmed.evaluation_platform.service.domain.ModelService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ModelServiceImpl implements ModelService {

    private final ModelRepository modelRepository;

    public ModelServiceImpl(ModelRepository modelRepository) {
        this.modelRepository = modelRepository;
    }

    @Override
    public List<Model> findByWorkspaceIdAndNameIn(Workspace workspace, List<String> names) {
        return modelRepository.findByWorkspaceIdAndNameIn(workspace.getId(), names);
    }

    @Override
    public Model save(Model model) {
        return modelRepository.save(model);
    }

    private int getModelId(String modelName) {
        return Integer.parseInt(modelName.split("M")[1]);
    }


    @Override
    public void createNewModels(List<String> modelNames, Workspace workspace) {
        Set<String> existingModels = findByWorkspaceIdAndNameIn(workspace, modelNames).stream()
                                                                                      .map(Model::getName)
                                                                                      .collect(Collectors.toSet());

        List<Model> newModels = modelNames.stream()
                                          .filter(modelName -> !existingModels.contains(modelName))
                                          .map(modelName -> Model.builder()
                                                                 .name(modelName)
                                                                 .workspace(workspace)
                                                                 .build())
                                          .sorted((m1, m2) -> getModelId(m1.getName()) - getModelId(m2.getName()))
                                          .toList();
        modelRepository.saveAll(newModels);
    }

    @Override
    public Long countAllModelsByWorkspaceId(Long workspaceId) {
        return modelRepository.countByWorkspaceId(workspaceId);
    }

    @Override
    public List<Model> findAllByWorkspace(Long workspaceId) {
        return modelRepository.findByWorkspaceId(workspaceId);
    }

    @Override
    public Optional<Model> findByWorkspaceAndName(Workspace workspace, String name) {
        return modelRepository.findByWorkspaceIdAndName(workspace.getId(), name);
    }

    @Override
    public Optional<Model> findById(Long modelId) {
        return Optional.ofNullable(modelId).flatMap(modelRepository::findById);
    }

    @Override
    public Optional<Model> findFirstModel(Long workspaceId) {
        return modelRepository.findFirstByWorkspaceIdOrderByIdAsc(workspaceId);
    }
}
