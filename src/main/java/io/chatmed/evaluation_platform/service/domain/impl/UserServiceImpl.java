package io.chatmed.evaluation_platform.service.domain.impl;

import io.chatmed.evaluation_platform.domain.Question;
import io.chatmed.evaluation_platform.domain.User;
import io.chatmed.evaluation_platform.repository.UserRepository;
import io.chatmed.evaluation_platform.service.domain.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> login(User user) {
        if (findByUsername(user.getUsername()).isPresent()) {
            return Optional.of(user);
        }
        return Optional.of(userRepository.save(user));
    }

    @Override
    public Optional<User> updateNextQuestion(User user, Question question) {
        if (findByUsername(user.getUsername()).isPresent()) {
            user.setNextQuestion(question);
            return Optional.of(userRepository.save(user));
        }
        return Optional.of(user);
    }
}
