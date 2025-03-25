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
            UserService userService,
            ModelService modelService
    ) {
        this.questionService = questionService; this.answerService = answerService;
        this.evaluationService = evaluationService; this.userService = userService; this.modelService = modelService;
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
        if (userDto.username() == null ||
                userDto.username().isEmpty() ||
                userDto.username().equals("null")) throw new ResourceNotFoundException();
        User user = userService.findByUsername(userDto.username())
                               .orElseGet(() -> userService.login(userDto.toUser())
                                                           .orElseThrow(ResourceNotFoundException::new));

        Question questionToEvaluate = questionService.findQuestionToEvaluate(user)
                                                     .orElseThrow(ResourceNotFoundException::new);

        Answer answerForQuestion = modelService.findById(modelId)
                                               .map(model -> answerService.findByQuestionAndModel(
                                                       questionToEvaluate,
                                                       model
                                               ).orElseThrow(ResourceNotFoundException::new))
                                               .orElseGet(() -> answerService.findNextAnswerToEvaluate(
                                                                                     questionToEvaluate,
                                                                                     user
                                                                             )
                                                                             .orElseThrow(ResourceNotFoundException::new));

        List<Model> evaluatedModels = findEvaluatedModelsForQuestionAndUser(questionToEvaluate, user);
        Evaluation evaluation = evaluationService.findEvaluationForAnswerAndUser(answerForQuestion, user).orElse(null);

        return new QuestionDetailsDto(
                QuestionDto.from(questionToEvaluate),
                AnswerDto.from(answerForQuestion),
                ModelDto.from(evaluatedModels),
                questionService.countEvaluatedQuestions(user),
                questionService.countRemainingQuestions(user),
                EvaluationDto.fromEvaluation(evaluation)
        );
    }

    private List<Model> findEvaluatedModelsForQuestionAndUser(Question questionToEvaluate, User user) {
        return evaluationService.findAllEvaluationsForQuestionAndUser(questionToEvaluate, user)
                                .stream()
                                .map(evaluation -> evaluation.getAnswer().getModel())
                                .toList();
    }

    @Override
    public QuestionDetailsDto setPreviousQuestionToEvaluate(UserDto userDto) {
        User user = userService.findByUsername(userDto.username()).orElseThrow(ResourceNotFoundException::new);
        Question currentQuestion = user.getCurrentQuestion();
        Question previousQuestion = questionService.findFirstQuestion()
                                                   .isPresent() && questionService.findFirstQuestion()
                                                                                  .get()
                                                                                  .getId()
                                                                                  .equals(currentQuestion.getId()) ?
                currentQuestion : questionService.findPreviousQuestion(
                user.getCurrentQuestion().getId()).orElseThrow(ResourceNotFoundException::new);

        userService.updateCurrentQuestion(user, previousQuestion);
        return setQuestionForUser(user, previousQuestion, currentQuestion);
    }

    @Override
    public QuestionDetailsDto setNextQuestionToEvaluate(UserDto userDto) {
        User user = userService.findByUsername(userDto.username()).orElseThrow(ResourceNotFoundException::new);
        Question currentQuestion = user.getCurrentQuestion(); Question nextToEvaluateQuestion = user.getNextQuestion();

        Question nextQuestion = currentQuestion.getId() < nextToEvaluateQuestion.getId() ?
                questionService.findNextQuestion(
                                       user.getCurrentQuestion().getId())
                               .orElseThrow(ResourceNotFoundException::new) : currentQuestion;

        userService.updateCurrentQuestion(user, nextQuestion);
        return setQuestionForUser(user, nextQuestion, currentQuestion);
    }

    private QuestionDetailsDto setQuestionForUser(User user, Question newQuestion, Question oldQuestion) {
        Answer answerForQuestion = modelService.findFirstModel()
                                               .map(model -> answerService.findByQuestionAndModel(newQuestion, model)
                                                                          .orElseThrow(ResourceNotFoundException::new))
                                               .orElseGet(() -> null);
        List<Model> evaluatedModels = findEvaluatedModelsForQuestionAndUser(newQuestion, user);
        Evaluation evaluation = evaluationService.findEvaluationForAnswerAndUser(answerForQuestion, user).orElse(null);

        return new QuestionDetailsDto(
                QuestionDto.from(newQuestion),
                AnswerDto.from(answerForQuestion),
                ModelDto.from(evaluatedModels),
                questionService.countEvaluatedQuestions(user),
                questionService.countRemainingQuestions(user),
                EvaluationDto.fromEvaluation(evaluation)
        );
    }

}
