package io.chatmed.evaluation_platform.application;

import io.chatmed.evaluation_platform.dto.EvaluationDto;
import io.chatmed.evaluation_platform.service.application.EvaluationApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/evaluations")
@CrossOrigin(value = "*")
@Tag(name = "Evaluations", description = "Endpoints for saving user evaluations")
public class EvaluationController {

    private final EvaluationApplicationService evaluationApplicationService;

    public EvaluationController(EvaluationApplicationService evaluationApplicationService) {
        this.evaluationApplicationService = evaluationApplicationService;
    }

    @Operation(
            summary = "Create or Update Evaluation",
            description = "Saves an evaluation in the system, or updates if existing."
    )
    @PostMapping
    public ResponseEntity<Void> createOrUpdateEvaluation(@RequestBody EvaluationDto evaluationDto) {
        evaluationApplicationService.createOrUpdateEvaluation(evaluationDto);
        return ResponseEntity.ok().build();
    }


    @Operation(
            summary = "Get evaluation by answer and user",
            description = "Returns an evaluation in the system, using the answer and user as parameters."
    )
    @GetMapping()
    public ResponseEntity<EvaluationDto> getEvaluation(
            @RequestParam Long answer,
            @RequestParam String username
    ) {
        return ResponseEntity.ok(evaluationApplicationService.getEvaluationForAnswerAndUser(
                answer,
                username
        ));
    }
}
