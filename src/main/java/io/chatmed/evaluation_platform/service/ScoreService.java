package io.chatmed.evaluation_platform.service;

import io.chatmed.evaluation_platform.model.Score;
import io.chatmed.evaluation_platform.model.dto.AnswerDto;

public interface ScoreService {

    Score evaluate(AnswerDto answerDto);
}
