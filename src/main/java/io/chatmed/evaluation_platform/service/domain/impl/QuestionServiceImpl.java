package io.chatmed.evaluation_platform.service.domain.impl;

import io.chatmed.evaluation_platform.domain.Evaluation;
import io.chatmed.evaluation_platform.domain.Question;
import io.chatmed.evaluation_platform.domain.User;
import io.chatmed.evaluation_platform.exceptions.ResourceNotFoundException;
import io.chatmed.evaluation_platform.repository.QuestionRepository;
import io.chatmed.evaluation_platform.service.domain.EvaluationService;
import io.chatmed.evaluation_platform.service.domain.ModelService;
import io.chatmed.evaluation_platform.service.domain.QuestionService;
import io.chatmed.evaluation_platform.service.domain.UserService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final UserService userService;
    private final ModelService modelService;
    private final EvaluationService evaluationService;

    public QuestionServiceImpl(
            QuestionRepository questionRepository,
            UserService userService,
            ModelService modelService, EvaluationService evaluationService
    ) {
        this.questionRepository = questionRepository;
        this.userService = userService;
        this.modelService = modelService;
        this.evaluationService = evaluationService;
    }

    @Override
    public Optional<Question> findById(Long id) {
        return questionRepository.findById(id);
    }

    @Override
    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    @Override
    public Optional<Question> findFirstQuestion() {
        return questionRepository.findFirstByOrderById();
    }

    @Override
    public Optional<Question> findQuestionToEvaluate(User user) {
        if (user.getCurrentQuestion() == null || user.getNextQuestion() == null) {
            Question firstAvailableQuestion = findFirstQuestion().orElseThrow(ResourceNotFoundException::new);
            userService.updateCurrentAndNextQuestion(user, firstAvailableQuestion);
        }
        return findById(user.getCurrentQuestion().getId());
    }

    @Override
    public Optional<Question> findPreviousQuestion(Long id) {
        return questionRepository.findFirstByIdLessThanOrderByIdDesc(id);
    }

    @Override
    public Optional<Question> findNextQuestion(Long id) {
        return questionRepository.findFirstByIdGreaterThan(id);
    }

    @Override
    public Optional<Question> findByQuestionKey(Long questionKey) {
        return questionRepository.findByQuestionKey(questionKey);
    }

    @Override
    public Optional<Question> findByText(String text) {
        return questionRepository.findByText(text);
    }

    @Transactional
    @Override
    public Optional<Question> save(Question question) {
        return Optional.of(questionRepository.save(question));
    }

    @Override
    public Long countEvaluatedQuestions(User user) {
        List<Evaluation> evaluations = evaluationService.findAllEvaluationsForUser(user);
        Map<Question, List<Evaluation>> evaluatedQuestions = evaluations.stream()
                                                                        .collect(Collectors.groupingBy(
                                                                                Evaluation::getQuestion,
                                                                                Collectors.toList()
                                                                        ));

        return evaluatedQuestions.entrySet()
                                 .stream()
                                 .filter(entry -> entry.getValue().size() == modelService.countAllModels())
                                 .count();
    }

    @Override
    public Long countRemainingQuestions(User user) {
        return questionRepository.countAllBy() - countEvaluatedQuestions(user);
    }
}
