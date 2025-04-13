package io.chatmed.evaluation_platform.service.application;

import io.chatmed.evaluation_platform.dto.ModelDto;

import java.util.List;

public interface ModelApplicationService {

    Long getModelsCount(Long workspaceId);

    List<ModelDto> findAllByWorkspace(Long workspaceId);
}
