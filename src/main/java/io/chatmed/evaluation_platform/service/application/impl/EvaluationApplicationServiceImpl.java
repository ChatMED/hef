package io.chatmed.evaluation_platform.service.application.impl;

import io.chatmed.evaluation_platform.domain.*;
import io.chatmed.evaluation_platform.dto.EvaluationDto;
import io.chatmed.evaluation_platform.exceptions.ResourceNotFoundException;
import io.chatmed.evaluation_platform.service.application.EvaluationApplicationService;
import io.chatmed.evaluation_platform.service.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EvaluationApplicationServiceImpl implements EvaluationApplicationService {

    private final EvaluationService evaluationService;
    private final AnswerService answerService;
    private final UserService userService;
    private final ModelService modelService;
    private final QuestionService questionService;
    private final MembershipService membershipService;
    private final WorkspaceService workspaceService;

    public EvaluationApplicationServiceImpl(
            EvaluationService evaluationService,
            AnswerService answerService,
            UserService userService,
            ModelService modelService,
            QuestionService questionService,
            MembershipService membershipService,
            WorkspaceService workspaceService
    ) {
        this.evaluationService = evaluationService;
        this.answerService = answerService;
        this.userService = userService;
        this.modelService = modelService;
        this.questionService = questionService;
        this.membershipService = membershipService;
        this.workspaceService = workspaceService;
    }

    @Override
    public void createOrUpdateEvaluation(EvaluationDto evaluationDto, boolean goToNextUnevaluatedQuestion) {
        Answer answer = answerService.findById(evaluationDto.answer())
                                     .orElseThrow(ResourceNotFoundException::new);
        User user = userService.findByUsername(evaluationDto.username())
                               .orElseThrow(ResourceNotFoundException::new);


        Evaluation newEvaluation = evaluationDto.toEvaluation(user, answer);
        evaluationService.findEvaluationForAnswerAndUser(answer, user)
                         .ifPresent(existingEvaluation -> newEvaluation.setId(existingEvaluation.getId()));
        evaluationService.save(newEvaluation);

        if (goToNextUnevaluatedQuestion) {
            Membership membership = membershipService.findMembership(user, answer.getWorkspace())
                    .orElseThrow(ResourceNotFoundException::new);
            updateNextQuestionForUserIfAllModelsEvaluated(answer.getQuestion(), membership);
        }
    }

    @Override
    public EvaluationDto getEvaluationForAnswerAndUser(Long answerId, String username) {
        Answer answer = answerService.findById(answerId).orElseThrow(ResourceNotFoundException::new);
        User user = userService.findByUsername(username).orElseThrow(ResourceNotFoundException::new);
        return evaluationService.findEvaluationForAnswerAndUser(answer, user)
                                .map(EvaluationDto::fromEvaluation)
                                .orElseThrow(ResourceNotFoundException::new);
    }

    private void updateNextQuestionForUserIfAllModelsEvaluated(Question question, Membership membership) {
        List<Evaluation> evaluations = evaluationService.findAllEvaluationsForQuestionAndUser(
                question,
                membership.getUser()
        );
        boolean allModelsEvaluated = evaluations.size() == modelService.countAllModelsByWorkspaceId(
                membership.getWorkspace().getId()
        );

        if (allModelsEvaluated) {
            questionService.findNextQuestion(question.getId(), membership.getWorkspace().getId())
                           .ifPresent(nextQuestion -> membershipService.updateCurrentAndNextQuestion(
                                   membership,
                                   nextQuestion.getId() >= membership.getNextQuestion().getId()
                                           ? nextQuestion
                                           : membership.getNextQuestion()
                           )
            );
        }
    }
}
