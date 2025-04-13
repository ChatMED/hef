package io.chatmed.evaluation_platform.service.domain.impl;

import io.chatmed.evaluation_platform.domain.User;
import io.chatmed.evaluation_platform.service.domain.MembershipService;
import io.chatmed.evaluation_platform.service.domain.QuestionService;
import io.chatmed.evaluation_platform.service.domain.UserCreationHandler;
import io.chatmed.evaluation_platform.service.domain.WorkspaceService;
import org.springframework.stereotype.Component;

@Component
public class UserCreationHandlerMemberships implements UserCreationHandler {

    private final WorkspaceService workspaceService;
    private final MembershipService membershipService;
    private final QuestionService questionService;

    public UserCreationHandlerMemberships(
            WorkspaceService workspaceService, MembershipService membershipService,
            QuestionService questionService
    ) {
        this.workspaceService = workspaceService;
        this.membershipService = membershipService;
        this.questionService = questionService;
    }

    @Override
    public void handle(User user) {
        workspaceService.findAll().forEach(workspace -> {
            membershipService.save(user, workspace, questionService.findFirstQuestion(workspace.getId()).orElse(null));
        });
    }
}
