package io.chatmed.evaluation_platform.service.application;

import io.chatmed.evaluation_platform.dto.QuestionDetailsDto;
import io.chatmed.evaluation_platform.dto.QuestionDto;
import io.chatmed.evaluation_platform.dto.UserDto;

import java.util.List;
import java.util.Optional;

public interface QuestionApplicationService {

    List<QuestionDto> findAll();

    Optional<QuestionDto> findByQuestionKey(Long questionKey);

    Optional<QuestionDto> findByText(String text);

    Long getQuestionsCount();

    QuestionDetailsDto findQuestionToEvaluate(UserDto userDto, Long modelId);

    QuestionDetailsDto setPreviousQuestionToEvaluate(UserDto userDto);

    QuestionDetailsDto setNextQuestionToEvaluate(UserDto userDto);
}
