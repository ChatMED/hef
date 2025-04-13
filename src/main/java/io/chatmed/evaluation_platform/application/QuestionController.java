package io.chatmed.evaluation_platform.application;

import io.chatmed.evaluation_platform.dto.QuestionDetailsDto;
import io.chatmed.evaluation_platform.dto.UserDto;
import io.chatmed.evaluation_platform.service.application.QuestionApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/questions")
@CrossOrigin(value = "*")
@Tag(name = "Question Management", description = "APIs related to managing questions")
public class QuestionController {

    private final QuestionApplicationService questionApplicationService;

    public QuestionController(QuestionApplicationService questionApplicationService) {
        this.questionApplicationService = questionApplicationService;
    }

    @GetMapping("/{username}")
    @Operation(
            summary = "Get current question for user to evaluate",
            description = "Retrieves the current question with answer for user to evaluate."
    )
    public ResponseEntity<QuestionDetailsDto> getQuestionToEvaluate(
            @PathVariable String username,
            @RequestParam Long workspaceId,
            @RequestParam(required = false) Long modelId
    ) {
        return ResponseEntity.ok(questionApplicationService.findQuestionToEvaluate(
                UserDto.of(username),
                workspaceId,
                modelId
        ));
    }

    @PostMapping("/prev/{username}")
    @Operation(
            summary = "Set previous question for user to re-evaluate",
            description = "Retrieves the previous question with answer for user to re-evaluate."
    )
    public ResponseEntity<QuestionDetailsDto> setAndGetPreviousQuestion(
            @PathVariable String username,
            @RequestParam Long workspaceId
    ) {
        return ResponseEntity.ok(questionApplicationService.setPreviousQuestionToEvaluate(
                UserDto.of(username),
                workspaceId
        ));
    }

    @PostMapping("/next/{username}")
    @Operation(
            summary = "Set next question for user to re-evaluate",
            description = "Retrieves the next question with answer for user to re-evaluate."
    )
    public ResponseEntity<QuestionDetailsDto> setAndGetNextQuestion(
            @PathVariable String username,
            @RequestParam Long workspaceId
    ) {
        return ResponseEntity.ok(questionApplicationService.setNextQuestionToEvaluate(
                UserDto.of(username),
                workspaceId
        ));
    }

    @GetMapping("/count")
    @Operation(
            summary = "Get total question count",
            description = "Returns the total count of questions stored in the system."
    )
    public ResponseEntity<Long> getQuestionsCount(@RequestParam Long workspaceId) {
        return ResponseEntity.ok(questionApplicationService.getQuestionsCount(workspaceId));
    }
}
