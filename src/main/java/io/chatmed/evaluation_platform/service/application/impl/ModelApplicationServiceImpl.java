package io.chatmed.evaluation_platform.service.application.impl;

import io.chatmed.evaluation_platform.dto.ModelDto;
import io.chatmed.evaluation_platform.service.application.ModelApplicationService;
import io.chatmed.evaluation_platform.service.domain.ModelService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModelApplicationServiceImpl implements ModelApplicationService {

    private final ModelService modelService;

    public ModelApplicationServiceImpl(ModelService modelService) {
        this.modelService = modelService;
    }

    @Override
    public Long getModelsCount() {
        return (long) modelService.findAll().size();
    }

    @Override
    public List<ModelDto> findAll() {
        return ModelDto.from(modelService.findAll());
    }
}
