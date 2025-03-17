package io.chatmed.evaluation_platform.service.domain.impl;

import io.chatmed.evaluation_platform.domain.Question;
import io.chatmed.evaluation_platform.domain.User;
import io.chatmed.evaluation_platform.exceptions.ResourceNotFoundException;
import io.chatmed.evaluation_platform.repository.QuestionRepository;
import io.chatmed.evaluation_platform.service.domain.QuestionService;
import io.chatmed.evaluation_platform.service.domain.UserService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final UserService userService;

    public QuestionServiceImpl(QuestionRepository questionRepository, UserService userService) {
        this.questionRepository = questionRepository;
        this.userService = userService;
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
    public Optional<Question> findQuestionToEvaluate(User user) {
        if (user.getNextQuestion() == null) {
            Question firstAvailableQuestion = questionRepository.findFirstByOrderById()
                                                                .orElseThrow(ResourceNotFoundException::new);
            userService.updateNextQuestion(user, firstAvailableQuestion);
        }
        return findById(user.getNextQuestion().getId());
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
    public Long countEvaluatedQuestions(Long id) {
        return questionRepository.countQuestionsEvaluated(id);
    }

    @Override
    public Long countRemainingQuestions(Long id) {
        return questionRepository.countRemainingQuestions(id);
    }
}
