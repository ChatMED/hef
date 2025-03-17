package io.chatmed.evaluation_platform.service.application.impl;

import io.chatmed.evaluation_platform.domain.Answer;
import io.chatmed.evaluation_platform.domain.Model;
import io.chatmed.evaluation_platform.domain.Question;
import io.chatmed.evaluation_platform.domain.User;
import io.chatmed.evaluation_platform.dto.*;
import io.chatmed.evaluation_platform.exceptions.ResourceNotFoundException;
import io.chatmed.evaluation_platform.service.application.QuestionApplicationService;
import io.chatmed.evaluation_platform.service.domain.AnswerService;
import io.chatmed.evaluation_platform.service.domain.EvaluationService;
import io.chatmed.evaluation_platform.service.domain.QuestionService;
import io.chatmed.evaluation_platform.service.domain.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionApplicationServiceImpl implements QuestionApplicationService {

    private final QuestionService questionService;
    private final AnswerService answerService;
    private final EvaluationService evaluationService;
    private final UserService userService;

    public QuestionApplicationServiceImpl(
            QuestionService questionService,
            AnswerService answerService,
            EvaluationService evaluationService,
            UserService userService
    ) {
        this.questionService = questionService;
        this.answerService = answerService;
        this.evaluationService = evaluationService;
        this.userService = userService;
    }

    @Override
    public List<QuestionDto> findAll() {
        return QuestionDto.from(questionService.findAll());
    }

    @Override
    public Optional<QuestionDto> findByQuestionKey(Long questionKey) {

        return Optional.of(QuestionDto.from(questionService.findByQuestionKey(questionKey)
                                                           .orElseThrow(ResourceNotFoundException::new)));
    }

    @Override
    public Optional<QuestionDto> findByText(String text) {
        return Optional.of(QuestionDto.from(questionService.findByText(text)
                                                           .orElseThrow(ResourceNotFoundException::new)));
    }

    @Override
    public Long getQuestionsCount() {
        return (long) questionService.findAll().size();
    }

    @Override
    public QuestionAnswerPairDto findQuestionToEvaluate(UserDto userDto) {
        User user = userService.findByUsername(userDto.username())
                               .orElseThrow(ResourceNotFoundException::new);
        Question questionToEvaluate = questionService.findQuestionToEvaluate(user)
                                                     .orElseThrow(ResourceNotFoundException::new);
        Answer answerForQuestion = answerService.findNextAnswerToEvaluate(questionToEvaluate, user)
                                                .orElseThrow(ResourceNotFoundException::new);

        List<Model> evaluatedModels =
                evaluationService.findAllEvaluationsForQuestionAndUser(
                                         questionToEvaluate,
                                         user
                                 )
                                 .stream()
                                 .map(evaluation -> evaluation.getAnswer().getModel())
                                 .toList();

        return new QuestionAnswerPairDto(
                QuestionDto.from(questionToEvaluate),
                AnswerDto.from(answerForQuestion),
                ModelDto.from(evaluatedModels),
                questionService.countEvaluatedQuestions(questionToEvaluate.getId()),
                questionService.countRemainingQuestions(questionToEvaluate.getId())
        );
    }
}
