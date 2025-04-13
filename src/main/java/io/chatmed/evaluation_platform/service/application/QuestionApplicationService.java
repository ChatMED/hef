package io.chatmed.evaluation_platform.service.application;

import io.chatmed.evaluation_platform.dto.QuestionDetailsDto;
import io.chatmed.evaluation_platform.dto.QuestionDto;
import io.chatmed.evaluation_platform.dto.UserDto;

import java.util.List;
import java.util.Optional;

public interface QuestionApplicationService {

    Long getQuestionsCount(Long workspaceId);

    QuestionDetailsDto findQuestionToEvaluate(UserDto userDto, Long workspaceId, Long modelId);

    QuestionDetailsDto setPreviousQuestionToEvaluate(UserDto userDto, Long workspaceId);

    QuestionDetailsDto setNextQuestionToEvaluate(UserDto userDto, Long workspaceId);
}
