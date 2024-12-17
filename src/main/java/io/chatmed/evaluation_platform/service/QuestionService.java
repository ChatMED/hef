package io.chatmed.evaluation_platform.service;

import io.chatmed.evaluation_platform.model.Question;
import io.chatmed.evaluation_platform.model.dto.QuestionDto;
import io.chatmed.evaluation_platform.model.dto.QuestionResultsDto;

import java.util.List;

public interface QuestionService {

    List<QuestionDto> getQuestions();

    List<QuestionResultsDto> getResults();
}
