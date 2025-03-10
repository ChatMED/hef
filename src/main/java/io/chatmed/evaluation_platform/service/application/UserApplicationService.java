package io.chatmed.evaluation_platform.service.application;

import io.chatmed.evaluation_platform.dto.UserDto;

import java.util.Optional;

public interface UserApplicationService {

    Optional<UserDto> login(UserDto userDto);
}
