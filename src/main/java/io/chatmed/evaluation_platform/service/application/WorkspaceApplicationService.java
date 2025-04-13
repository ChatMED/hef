package io.chatmed.evaluation_platform.service.application;

import io.chatmed.evaluation_platform.dto.DisplayWorkspaceDto;

import java.util.List;

public interface WorkspaceApplicationService {

    List<DisplayWorkspaceDto> findAll();
}
