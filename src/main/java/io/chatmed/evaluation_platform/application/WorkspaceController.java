package io.chatmed.evaluation_platform.application;

import io.chatmed.evaluation_platform.dto.DisplayWorkspaceDto;
import io.chatmed.evaluation_platform.service.application.WorkspaceApplicationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/workspaces")
@CrossOrigin(value = "*")
@Tag(name = "Workspace Management", description = "APIs related to managing workspaces")
public class WorkspaceController {

    private final WorkspaceApplicationService workspaceApplicationService;

    public WorkspaceController(WorkspaceApplicationService workspaceApplicationService) {
        this.workspaceApplicationService = workspaceApplicationService;
    }

    @GetMapping
    public List<DisplayWorkspaceDto> findAll() {
        return workspaceApplicationService.findAll();
    }
}
