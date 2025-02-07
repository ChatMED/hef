package io.chatmed.evaluation_platform.web;

import io.chatmed.evaluation_platform.model.dto.QuestionDto;
import io.chatmed.evaluation_platform.model.dto.QuestionResultsDto;
import io.chatmed.evaluation_platform.service.QuestionService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
@CrossOrigin(value = "*")
public class QuestionController {
    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping
    List<QuestionDto> getQuestions() {
        return questionService.getQuestions();
    }

    @GetMapping("/results")
    List<QuestionResultsDto> getResults() {
        return questionService.getResults();
    }
}
