package io.chatmed.evaluation_platform.service.application.impl;

import io.chatmed.evaluation_platform.domain.Answer;
import io.chatmed.evaluation_platform.domain.Evaluation;
import io.chatmed.evaluation_platform.domain.Question;
import io.chatmed.evaluation_platform.domain.User;
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

    public EvaluationApplicationServiceImpl(
            EvaluationService evaluationService,
            AnswerService answerService,
            UserService userService, ModelService modelService,
            QuestionService questionService
    ) {
        this.evaluationService = evaluationService;
        this.answerService = answerService;
        this.userService = userService;
        this.modelService = modelService;
        this.questionService = questionService;
    }

    @Override
    public void createOrUpdateEvaluation(EvaluationDto evaluationDto) {
        Answer answer = answerService.findById(evaluationDto.answer())
                                     .orElseThrow(ResourceNotFoundException::new);
        User user = userService.findByUsername(evaluationDto.username())
                               .orElseThrow(ResourceNotFoundException::new);

        Evaluation newEvaluation = evaluationDto.toEvaluation(user, answer);
        evaluationService.findEvaluationForAnswerAndUser(answer, user)
                         .ifPresent(existingEvaluation -> newEvaluation.setId(existingEvaluation.getId()));

        evaluationService.save(newEvaluation);
        updateNextQuestionForUserIfAllModelsEvaluated(answer.getQuestion(), user);
    }

    private void updateNextQuestionForUserIfAllModelsEvaluated(Question question, User user) {
        List<Evaluation> evaluations = evaluationService.findAllEvaluationsForQuestionAndUser(question, user);
        boolean allModelsEvaluated = evaluations.size() == modelService.countAllModels();

        if (allModelsEvaluated) {
            questionService.findNextQuestion(question.getId())
                           .ifPresent(nextQuestion -> userService.updateNextQuestion(user, nextQuestion));
        }
    }



//        Evaluation evaluation = new Evaluation();
//        evaluation.setAnswer(answer);
//
//        evaluation.setAccuracy(answerDto.getAccuracy());
//        evaluation.setComprehensiveness(answerDto.getComprehensiveness());
//        evaluation.setClarity(answerDto.getClarity());
//        evaluation.setEmpathy(answerDto.getEmpathy());
//        evaluation.setBias(answerDto.getBias());
//        evaluation.setHarm(answerDto.getHarm());
//        evaluation.setTrust(answerDto.getTrust());
//
//        evaluation.setComment(answerDto.getComment());
//        evaluation.setUser(answerDto.getUser());
//
//
//        return scoreRepository.save(evaluation);
//    }
}
