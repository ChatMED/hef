package io.chatmed.evaluation_platform.service.domain.impl;

import io.chatmed.evaluation_platform.domain.User;
import io.chatmed.evaluation_platform.repository.UserRepository;
import io.chatmed.evaluation_platform.service.domain.UserCreationHandler;
import io.chatmed.evaluation_platform.service.domain.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final List<UserCreationHandler> userCreationHandlers;

    public UserServiceImpl(UserRepository userRepository, List<UserCreationHandler> userCreationHandlers) {
        this.userRepository = userRepository;
        this.userCreationHandlers = userCreationHandlers;
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
        User newUser = userRepository.save(user);
        userCreationHandlers.forEach(userCreationHandler -> userCreationHandler.handle(newUser));
        return Optional.of(userRepository.save(newUser));
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
