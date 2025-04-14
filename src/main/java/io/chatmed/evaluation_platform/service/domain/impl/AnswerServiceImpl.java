package io.chatmed.evaluation_platform.service.domain.impl;

import io.chatmed.evaluation_platform.domain.*;
import io.chatmed.evaluation_platform.repository.AnswerRepository;
import io.chatmed.evaluation_platform.service.domain.AnswerService;
import io.chatmed.evaluation_platform.service.domain.EvaluationService;
import io.chatmed.evaluation_platform.service.domain.ModelService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository answerRepository;
    private final EvaluationService evaluationService;
    private final ModelService modelService;

    public AnswerServiceImpl(
            AnswerRepository answerRepository,
            EvaluationService evaluationService,
            ModelService modelService
    ) {
        this.answerRepository = answerRepository;
        this.evaluationService = evaluationService;
        this.modelService = modelService;
    }

    @Override
    public Optional<Answer> findById(Long id) {
        return answerRepository.findById(id);
    }

    @Override
    public Optional<Answer> findByQuestionAndModel(Question question, Model model) {
        return answerRepository.findByQuestionAndModel(question, model);
    }

    @Override
    public Optional<Answer> findNextAnswerToEvaluate(Question question, User user) {
        List<Evaluation> evaluations = evaluationService.findAllEvaluationsForQuestionAndUser(question, user);
        List<Model> models = modelService.findAllByWorkspace(question.getWorkspace().getId());

        Model nextModel = models.stream()
                                .filter(model -> evaluations.stream()
                                                            .map(evaluation -> evaluation.getAnswer().getModel())
                                                            .noneMatch(model::equals))
                                .findFirst()
                                .orElseGet(() -> models.get(0));

        return answerRepository.findByQuestionAndModel(question, nextModel);
    }


    @Transactional
    @Override
    public Answer save(Answer answer) {
        return answerRepository.save(answer);
    }
}
