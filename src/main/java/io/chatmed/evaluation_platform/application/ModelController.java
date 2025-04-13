package io.chatmed.evaluation_platform.application;

import io.chatmed.evaluation_platform.dto.ModelDto;
import io.chatmed.evaluation_platform.service.application.ModelApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/models")
@CrossOrigin(value = "*")
@Tag(name = "Model Management", description = "APIs related to model management")
public class ModelController {

    private final ModelApplicationService modelApplicationService;

    public ModelController(ModelApplicationService modelApplicationService) {
        this.modelApplicationService = modelApplicationService;
    }

    @GetMapping("/count")
    @Operation(
            summary = "Get the number of models in a given workspace",
            description = "Returns the total count of models in a given workspace."
    )
    public ResponseEntity<Long> getModelsCount(@RequestParam Long workspaceId) {
        return ResponseEntity.ok(modelApplicationService.getModelsCount(workspaceId));
    }

    @GetMapping
    @Operation(
            summary = "Get all models from a given workspace",
            description = "Returns all models from a given workspace."
    )
    public List<ModelDto> findAll(@RequestParam Long workspaceId) {
        return modelApplicationService.findAllByWorkspace(workspaceId);
    }
}
