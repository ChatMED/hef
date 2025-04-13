package io.chatmed.evaluation_platform.service.domain.impl;

import io.chatmed.evaluation_platform.domain.Evaluation;
import io.chatmed.evaluation_platform.domain.Membership;
import io.chatmed.evaluation_platform.domain.Question;
import io.chatmed.evaluation_platform.domain.Workspace;
import io.chatmed.evaluation_platform.repository.QuestionRepository;
import io.chatmed.evaluation_platform.service.domain.EvaluationService;
import io.chatmed.evaluation_platform.service.domain.ModelService;
import io.chatmed.evaluation_platform.service.domain.QuestionService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final ModelService modelService;
    private final EvaluationService evaluationService;

    public QuestionServiceImpl(
            QuestionRepository questionRepository,
            ModelService modelService,
            EvaluationService evaluationService
    ) {
        this.questionRepository = questionRepository;
        this.modelService = modelService;
        this.evaluationService = evaluationService;
    }

    @Override
    public Optional<Question> findById(Long id) {
        return questionRepository.findById(id);
    }

    @Override
    public Optional<Question> findFirstQuestion(Long workspaceId) {
        return questionRepository.findFirstByWorkspaceIdOrderById(workspaceId);
    }

    @Override
    public Optional<Question> findPreviousQuestion(Long id, Long workspaceId) {
        return questionRepository.findFirstByWorkspaceIdAndIdLessThanOrderByIdDesc(workspaceId, id);
    }

    @Override
    public Optional<Question> findNextQuestion(Long id, Long workspaceId) {
        return questionRepository.findFirstByWorkspaceIdAndIdGreaterThan(workspaceId, id);
    }

    @Override
    public Optional<Question> findByWorkspaceAndQuestionKey(Workspace workspace, Long questionKey) {
        return questionRepository.findByWorkspaceIdAndQuestionKey(workspace.getId(), questionKey);
    }

    @Override
    public Optional<Question> findByText(String text) {
        return questionRepository.findByText(text);
    }

    @Transactional
    @Override
    public Question save(Question question) {
        return questionRepository.save(question);
    }

    @Override
    public Long countEvaluatedQuestions(Membership membership) {
        List<Evaluation> evaluations = evaluationService.findAllEvaluationsForMembership(membership);
        Map<Question, List<Evaluation>> evaluatedQuestions = evaluations.stream()
                                                                        .collect(Collectors.groupingBy(
                                                                                Evaluation::getQuestion,
                                                                                Collectors.toList()
                                                                        ));

        return evaluatedQuestions.entrySet()
                                 .stream()
                                 .filter(entry -> entry.getValue().size() ==
                                         modelService.countAllModelsByWorkspaceId(membership.getWorkspace().getId()))
                                 .count();
    }

    @Override
    public Long countRemainingQuestions(Membership membership) {
        return questionRepository.countAllByWorkspaceId(membership.getWorkspace().getId()) - countEvaluatedQuestions(
                membership);
    }

    @Override
    public Long countByWorkspaceId(Long workspaceId) {
        return questionRepository.countAllByWorkspaceId(workspaceId);
    }
}
