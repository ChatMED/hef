package io.chatmed.evaluation_platform.application;

import io.chatmed.evaluation_platform.dto.QuestionDetailsDto;
import io.chatmed.evaluation_platform.dto.QuestionDto;
import io.chatmed.evaluation_platform.dto.UserDto;
import io.chatmed.evaluation_platform.service.application.QuestionApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
@CrossOrigin(value = "*")
@Tag(name = "Question Management", description = "APIs related to managing questions")
public class QuestionController {

    private final QuestionApplicationService questionApplicationService;

    public QuestionController(QuestionApplicationService questionApplicationService) {
        this.questionApplicationService = questionApplicationService;
    }

    @GetMapping("/overview")
    @Operation(
            summary = "Get all questions overview", description = "Retrieves a list of all available questions."
    )
    public List<QuestionDto> getQuestions() {
        return questionApplicationService.findAll();
    }

    @GetMapping("/{username}")
    @Operation(
            summary = "Get current question for user to evaluate",
            description = "Retrieves the current question with answer for user to evaluate."
    )
    public ResponseEntity<QuestionDetailsDto> getQuestionToEvaluate(
            @PathVariable String username,
            @RequestParam(required = false) Long modelId
    ) {
        return ResponseEntity.ok(questionApplicationService.findQuestionToEvaluate(UserDto.of(username), modelId));
    }

    @PostMapping("/prev/{username}")
    @Operation(
            summary = "Set previous question for user to re-evaluate",
            description = "Retrieves the previous question with answer for user to re-evaluate."
    )
    public ResponseEntity<QuestionDetailsDto> setAndGetPreviousQuestion(
            @PathVariable String username
    ) {
        return ResponseEntity.ok(questionApplicationService.setPreviousQuestionToEvaluate(UserDto.of(username)));
    }

    @PostMapping("/next/{username}")
    @Operation(
            summary = "Set next question for user to re-evaluate",
            description = "Retrieves the next question with answer for user to re-evaluate."
    )
    public ResponseEntity<QuestionDetailsDto> setAndGetNextQuestion(
            @PathVariable String username
    ) {
        return ResponseEntity.ok(questionApplicationService.setNextQuestionToEvaluate(UserDto.of(username)));
    }

    @GetMapping("/count")
    @Operation(
            summary = "Get total question count",
            description = "Returns the total count of questions stored in the system."
    )
    public ResponseEntity<Long> getQuestionsCount() {
        return ResponseEntity.ok(questionApplicationService.getQuestionsCount());
    }
}
