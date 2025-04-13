package io.chatmed.evaluation_platform.service.domain;

import io.chatmed.evaluation_platform.domain.User;

public interface UserCreationHandler {

    void handle(User user);
}
