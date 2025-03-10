package io.chatmed.evaluation_platform.application;

import io.chatmed.evaluation_platform.dto.QuestionDto;
import io.chatmed.evaluation_platform.dto.QuestionAnswerPairDto;
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
            summary = "Get all questions overview",
            description = "Retrieves a list of all available questions."
    )
    public List<QuestionDto> getQuestions() {
        return questionApplicationService.findAll();
    }

    @GetMapping("/{username}")
    @Operation(
            summary = "Get next question for user to evaluate",
            description = "Retrieves the next question with answers for user to evaluate."
    )
    public ResponseEntity<QuestionAnswerPairDto> getQuestionToEvaluate(@PathVariable String username) {
        return ResponseEntity.ok(questionApplicationService.findQuestionToEvaluate(UserDto.of(username)));
    }

    @GetMapping("/count")
    @Operation(
            summary = "Get total question count",
            description = "Returns the total count of questions stored in the system."
    )
    public ResponseEntity<Long> getQuestionsCount() {
        return ResponseEntity.ok(questionApplicationService.getQuestionsCount());
    }

//    @GetMapping("/results")
//    @Operation(summary = "Get question results", description = "Retrieves the results for all questions.")
//    public List<QuestionResultsDto> getResults() {
//        return questionApplicationService.getResults();
//    }
}
