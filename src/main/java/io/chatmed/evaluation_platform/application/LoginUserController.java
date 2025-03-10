package io.chatmed.evaluation_platform.application;

import io.chatmed.evaluation_platform.dto.UserDto;
import io.chatmed.evaluation_platform.service.application.UserApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login")
@CrossOrigin(value = "*")
@Tag(name = "User Login", description = "Endpoints for user authentication")
public class LoginUserController {

    private final UserApplicationService userApplicationService;

    public LoginUserController(UserApplicationService userApplicationService) {
        this.userApplicationService = userApplicationService;
    }

    @Operation(summary = "User Login", description = "Authenticates a user and returns user details if valid.")
    @PostMapping
    public ResponseEntity<UserDto> login(@RequestBody UserDto userDto) {
        return userApplicationService.login(userDto)
                                     .map(ResponseEntity::ok)
                                     .orElse(ResponseEntity.notFound().build());
    }
}
