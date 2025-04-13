package io.chatmed.evaluation_platform.service.domain.impl;

import io.chatmed.evaluation_platform.domain.*;
import io.chatmed.evaluation_platform.repository.EvaluationRepository;
import io.chatmed.evaluation_platform.service.domain.EvaluationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EvaluationServiceImpl implements EvaluationService {

    private final EvaluationRepository evaluationRepository;

    public EvaluationServiceImpl(
            EvaluationRepository evaluationRepository
    ) {
        this.evaluationRepository = evaluationRepository;
    }

    @Override
    public void save(Evaluation evaluation) {
        evaluationRepository.save(evaluation);
    }

    @Override
    public Optional<Evaluation> findEvaluationForAnswerAndUser(Answer answer, User user) {
        return evaluationRepository.findByAnswerAndUser(answer, user);
    }

    @Override
    public List<Evaluation> findAllEvaluationsForQuestionAndUser(Question question, User user) {
        return evaluationRepository.findAllByQuestionAndUser(question, user);
    }

    @Override
    public List<Evaluation> findAllEvaluationsForUser(User user) {
        return evaluationRepository.findAllByUser(user);
    }

    @Override
    public List<Evaluation> findAllEvaluationsForMembership(Membership membership) {
        return evaluationRepository.findAllByWorkspaceAndUser(membership.getWorkspace(), membership.getUser());
    }
}
