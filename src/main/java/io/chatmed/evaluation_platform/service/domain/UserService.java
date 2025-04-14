package io.chatmed.evaluation_platform.service.domain;

import io.chatmed.evaluation_platform.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> findByUsername(String username);

    Optional<User> login(User user);

    List<User> findAll();
}
