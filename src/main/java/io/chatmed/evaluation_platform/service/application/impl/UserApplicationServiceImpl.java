package io.chatmed.evaluation_platform.service.application.impl;

import io.chatmed.evaluation_platform.dto.UserDto;
import io.chatmed.evaluation_platform.service.application.UserApplicationService;
import io.chatmed.evaluation_platform.service.domain.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserApplicationServiceImpl implements UserApplicationService {

    private final UserService userService;

    public UserApplicationServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Optional<UserDto> login(UserDto loginUserDto) {
        return userService.login(loginUserDto.toUser()).map(UserDto::of);
    }
}
