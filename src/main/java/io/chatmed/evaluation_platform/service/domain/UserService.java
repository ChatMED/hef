package io.chatmed.evaluation_platform.service.domain;

import io.chatmed.evaluation_platform.domain.Question;
import io.chatmed.evaluation_platform.domain.User;

import java.util.Optional;

public interface UserService {

    Optional<User> findByUsername(String username);

    Optional<User> login(User user);

    Optional<User> updateCurrentAndNextQuestion(User user, Question question);

    Optional<User> updateCurrentQuestion(User user, Question question);
}
