package io.chatmed.evaluation_platform.service.domain;

import io.chatmed.evaluation_platform.domain.Model;

import java.util.List;
import java.util.Optional;

public interface ModelService {

    Optional<Model> findByName(String name);

    List<Model> findAll();

    List<Model> findByNameIn(List<String> names);

    Optional<Model> save(Model model);

    void createNewModels(List<String> modelNames);

    Long countAllModels();
}
