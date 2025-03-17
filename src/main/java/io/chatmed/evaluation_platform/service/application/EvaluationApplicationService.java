package io.chatmed.evaluation_platform.service.application;

import io.chatmed.evaluation_platform.dto.EvaluationDto;

public interface EvaluationApplicationService {

    void createOrUpdateEvaluation(EvaluationDto evaluation);

    EvaluationDto getEvaluationForAnswerAndUser(Long answer, String user);
}
