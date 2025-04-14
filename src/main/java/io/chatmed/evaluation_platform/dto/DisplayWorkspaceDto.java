package io.chatmed.evaluation_platform.dto;


import io.chatmed.evaluation_platform.domain.Workspace;

import java.util.List;
import java.util.stream.Collectors;

public record DisplayWorkspaceDto(Long id, String name) {

    public static DisplayWorkspaceDto from(Workspace workspace) {
        return new DisplayWorkspaceDto(workspace.getId(), workspace.getName());
    }

    public static List<DisplayWorkspaceDto> from(List<Workspace> workspaces) {
        return workspaces.stream().map(DisplayWorkspaceDto::from).collect(Collectors.toList());
    }

}
