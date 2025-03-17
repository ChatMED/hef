package io.chatmed.evaluation_platform.service.application.impl;

import io.chatmed.evaluation_platform.domain.*;
import io.chatmed.evaluation_platform.dto.*;
import io.chatmed.evaluation_platform.exceptions.ResourceNotFoundException;
import io.chatmed.evaluation_platform.service.application.QuestionApplicationService;
import io.chatmed.evaluation_platform.service.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionApplicationServiceImpl implements QuestionApplicationService {

    private final QuestionService questionService;
    private final AnswerService answerService;
    private final EvaluationService evaluationService;
    private final UserService userService;
    private final ModelService modelService;

    public QuestionApplicationServiceImpl(
            QuestionService questionService,
            AnswerService answerService,
            EvaluationService evaluationService,
            UserService userService, ModelService modelService
    ) {
        this.questionService = questionService;
        this.answerService = answerService;
        this.evaluationService = evaluationService;
        this.userService = userService;
        this.modelService = modelService;
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
    public QuestionDetailsDto findQuestionToEvaluate(UserDto userDto, Long modelId) {
        User user = userService.findByUsername(userDto.username())
                               .orElseThrow(ResourceNotFoundException::new);

        Question questionToEvaluate = questionService.findQuestionToEvaluate(user)
                                                     .orElseThrow(ResourceNotFoundException::new);

        Answer answerForQuestion = modelService.findById(modelId).isPresent() ?
                answerService.findByQuestionAndModel(
                        questionToEvaluate,
                        modelService.findById(modelId).get()
                ).orElseThrow(ResourceNotFoundException::new) :
                answerService.findNextAnswerToEvaluate(questionToEvaluate, user)
                             .orElseThrow(ResourceNotFoundException::new);

        List<Model> evaluatedModels =
                evaluationService.findAllEvaluationsForQuestionAndUser(
                                         questionToEvaluate,
                                         user
                                 )
                                 .stream()
                                 .map(evaluation -> evaluation.getAnswer().getModel())
                                 .toList();

        Evaluation evaluation = evaluationService.findEvaluationForAnswerAndUser(answerForQuestion, user).orElse(null);

        return new QuestionDetailsDto(
                QuestionDto.from(questionToEvaluate),
                AnswerDto.from(answerForQuestion),
                ModelDto.from(evaluatedModels),
                questionService.countEvaluatedQuestions(questionToEvaluate.getId()),
                questionService.countRemainingQuestions(questionToEvaluate.getId()),
                EvaluationDto.fromEvaluation(evaluation)
        );
    }
}
