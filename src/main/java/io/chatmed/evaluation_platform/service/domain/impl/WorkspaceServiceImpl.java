package io.chatmed.evaluation_platform.service.domain.impl;

import io.chatmed.evaluation_platform.domain.Workspace;
import io.chatmed.evaluation_platform.repository.WorkspaceRepository;
import io.chatmed.evaluation_platform.service.domain.WorkspaceService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkspaceServiceImpl implements WorkspaceService {

    private final WorkspaceRepository workspaceRepository;

    public WorkspaceServiceImpl(WorkspaceRepository workspaceRepository) {
        this.workspaceRepository = workspaceRepository;
    }

    @Override
    public Workspace save(Workspace workspace) {
        return workspaceRepository.save(workspace);
    }

    @Override
    public List<Workspace> findAll() {
        return workspaceRepository.findAll();
    }

    @Override
    public Optional<Workspace> findById(Long id) {
        return workspaceRepository.findById(id);
    }
}
