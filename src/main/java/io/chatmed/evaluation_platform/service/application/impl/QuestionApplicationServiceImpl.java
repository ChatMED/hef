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
    private final MembershipService membershipService;
    private final WorkspaceService workspaceService;

    public QuestionApplicationServiceImpl(
            QuestionService questionService,
            AnswerService answerService,
            EvaluationService evaluationService,
            UserService userService,
            ModelService modelService,
            MembershipService membershipService,
            WorkspaceService workspaceService
    ) {
        this.questionService = questionService;
        this.answerService = answerService;
        this.evaluationService = evaluationService;
        this.userService = userService;
        this.modelService = modelService;
        this.membershipService = membershipService;
        this.workspaceService = workspaceService;
    }

    @Override
    public Long getQuestionsCount(Long workspaceId) {
        return questionService.countByWorkspaceId(workspaceId);
    }

    @Override
    public QuestionDetailsDto findQuestionToEvaluate(UserDto userDto, Long workspaceId, Long modelId) {
        if (userDto.username() == null || userDto.username().isEmpty() || userDto.username().equals("null"))
            throw new ResourceNotFoundException();

        User user = userService.findByUsername(userDto.username())
                               .orElseGet(() -> userService.login(userDto.toUser())
                                                           .orElseThrow(ResourceNotFoundException::new));

        Workspace workspace = workspaceService.findById(workspaceId).orElseThrow(ResourceNotFoundException::new);

        Membership membership = membershipService.findMembership(user, workspace)
                                                 .orElseThrow(ResourceNotFoundException::new);

        Question questionToEvaluate = membership.getCurrentQuestion();

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
                questionService.countEvaluatedQuestions(membership),
                questionService.countRemainingQuestions(membership),
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
    public QuestionDetailsDto setPreviousQuestionToEvaluate(UserDto userDto, Long workspaceId) {
        User user = userService.findByUsername(userDto.username()).orElseThrow(ResourceNotFoundException::new);
        Workspace workspace = workspaceService.findById(workspaceId).orElseThrow(ResourceNotFoundException::new);
        Membership membership = membershipService.findMembership(user, workspace)
                                                 .orElseThrow(ResourceNotFoundException::new);

        Question currentQuestion = membership.getCurrentQuestion();
        Question previousQuestion = questionService.findFirstQuestion(workspaceId)
                                                   .filter(it -> it.getId().equals(currentQuestion.getId()))
                                                   .map(it -> currentQuestion)
                                                   .orElseGet(() -> questionService.findPreviousQuestion(
                                                           currentQuestion.getId(), workspaceId)
                                                   .orElseThrow(ResourceNotFoundException::new)
                                                   );

        membershipService.updateCurrent(membership, previousQuestion);
        return setQuestionForMembership(membership, previousQuestion);
    }

    @Override
    public QuestionDetailsDto setNextQuestionToEvaluate(UserDto userDto, Long workspaceId) {
        User user = userService.findByUsername(userDto.username()).orElseThrow(ResourceNotFoundException::new);
        Workspace workspace = workspaceService.findById(workspaceId).orElseThrow(ResourceNotFoundException::new);
        Membership membership = membershipService.findMembership(user, workspace)
                                                 .orElseThrow(ResourceNotFoundException::new);
        Question currentQuestion = membership.getCurrentQuestion();
        Question nextToEvaluateQuestion = membership.getNextQuestion();

        Question nextQuestion = currentQuestion.getId() < nextToEvaluateQuestion.getId()
                ? questionService.findNextQuestion(currentQuestion.getId(), workspaceId)
                               .orElseThrow(ResourceNotFoundException::new)
                : currentQuestion;

        membershipService.updateCurrent(membership, nextQuestion);
        return setQuestionForMembership(membership, nextQuestion);
    }

    private QuestionDetailsDto setQuestionForMembership(
            Membership membership,
            Question newQuestion
    ) {
        Answer answerForQuestion = modelService.findFirstModel(membership.getWorkspace().getId())
                                               .map(model -> answerService.findByQuestionAndModel(newQuestion, model)
                                                                          .orElseThrow(ResourceNotFoundException::new))
                                               .orElse(null);

        List<Model> evaluatedModels = findEvaluatedModelsForQuestionAndUser(newQuestion, membership.getUser());

        Evaluation evaluation = evaluationService.findEvaluationForAnswerAndUser(answerForQuestion, membership.getUser())
                                                 .orElse(null);

        return new QuestionDetailsDto(
                QuestionDto.from(newQuestion),
                AnswerDto.from(answerForQuestion),
                ModelDto.from(evaluatedModels),
                questionService.countEvaluatedQuestions(membership),
                questionService.countRemainingQuestions(membership),
                EvaluationDto.fromEvaluation(evaluation)
        );
    }

}
