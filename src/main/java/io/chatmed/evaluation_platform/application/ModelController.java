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
            summary = "Get the number of models",
            description = "Returns the total count of models stored in the system."
    )
    public ResponseEntity<Long> getModelsCount() {
        return ResponseEntity.ok(modelApplicationService.getModelsCount());
    }

    @GetMapping("")
    @Operation(
            summary = "Get all models",
            description = "Returns all models stored in the system."
    )
    public List<ModelDto> findAll() {
        return modelApplicationService.findAll();
    }
}
