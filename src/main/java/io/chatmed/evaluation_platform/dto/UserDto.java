package io.chatmed.evaluation_platform.dto;

import io.chatmed.evaluation_platform.domain.User;

public record UserDto(String username) {

    public static UserDto of(User user) {
        return new UserDto(user.getUsername());
    }

    public static UserDto of(String username) {
        return new UserDto(username);
    }

    public User to(UserDto userDto) {
        return User.builder().username(userDto.username).build();
    }

    public User toUser() {
        return User.builder().username(username).build();
    }
}

