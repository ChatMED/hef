package io.chatmed.evaluation_platform.service.domain.impl;

import io.chatmed.evaluation_platform.domain.Model;
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
    public Optional<Model> findByName(String name) {
        return modelRepository.findByName(name);
    }

    @Override
    public List<Model> findAll() {
        return modelRepository.findAllByOrderByIdAsc();
    }

    @Override
    public List<Model> findByNameIn(List<String> names) {
        return modelRepository.findByNameIn(names);
    }

    @Override
    public Optional<Model> save(Model model) {
        return Optional.of(modelRepository.save(model));
    }

    @Override
    public void createNewModels(List<String> modelNames) {
        Set<String> existingModels = findByNameIn(modelNames).stream()
                                                             .map(Model::getName)
                                                             .collect(Collectors.toSet());

        List<Model> newModels = modelNames.stream()
                                          .filter(modelName -> !existingModels.contains(modelName))
                                          .map(Model::new)
                                          .toList();
        modelRepository.saveAll(newModels);
    }

    @Override
    public Long countAllModels() {
        return modelRepository.count();
    }
}
