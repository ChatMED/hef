package io.chatmed.evaluation_platform.service.application.impl;

import io.chatmed.evaluation_platform.dto.DisplayWorkspaceDto;
import io.chatmed.evaluation_platform.service.application.WorkspaceApplicationService;
import io.chatmed.evaluation_platform.service.domain.WorkspaceService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkspaceApplicationServiceImpl implements WorkspaceApplicationService {

    private final WorkspaceService workspaceService;

    public WorkspaceApplicationServiceImpl(WorkspaceService workspaceService) {
        this.workspaceService = workspaceService;
    }

    @Override
    public List<DisplayWorkspaceDto> findAll() {
        return DisplayWorkspaceDto.from(workspaceService.findAll());
    }
}
