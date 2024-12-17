package io.chatmed.evaluation_platform.web;

import io.chatmed.evaluation_platform.model.Score;
import io.chatmed.evaluation_platform.model.dto.AnswerDto;
import io.chatmed.evaluation_platform.service.ScoreService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/score")
@CrossOrigin(value = "*")
public class ScoreResource {
    private final ScoreService scoreService;

    public ScoreResource(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @PostMapping("/evaluate")
    public Score evaluate(@RequestBody AnswerDto answerDto) {
        return scoreService.evaluate(answerDto);
    }
}
