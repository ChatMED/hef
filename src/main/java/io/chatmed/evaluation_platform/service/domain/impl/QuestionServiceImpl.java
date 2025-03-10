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

//    @Override
//    public List<QuestionResultsDto> getResults() {
//        List<Question> questions = questionRepository.findAll();
//        List<QuestionResultsDto> questionDtos = new ArrayList<>();
//
//        for (Question question : questions) {
//            List<Answer> answers = answerRepository.findByQuestion(question);
//            List<AnswerResultsDto> answerResultsDtos = new ArrayList<>();
//
//            for (Answer answer : answers) {
//                List<Evaluation> evaluations = scoreRepository.findAllByAnswer(answer);
//
//                // Calculate averages
//                double avgAccuracy = evaluations.stream()
//                                                .filter(score -> score.getAccuracy() != null)
//                                                .mapToDouble(Evaluation::getAccuracy)
//                                                .average()
//                                                .orElse(0.0);
//                double avgComprehensiveness = evaluations.stream()
//                                                         .filter(score -> score.getComprehensiveness() !=
//                                                         null)
//                                                         .mapToDouble(Evaluation::getComprehensiveness)
//                                                         .average()
//                                                         .orElse(0.0);
//                double avgClarity = evaluations.stream()
//                                               .filter(score -> score.getClarity() != null)
//                                               .mapToDouble(Evaluation::getClarity)
//                                               .average()
//                                               .orElse(0.0);
//                double avgEmpathy = evaluations.stream()
//                                               .filter(score -> score.getEmpathy() != null)
//                                               .mapToDouble(Evaluation::getEmpathy)
//                                               .average()
//                                               .orElse(0.0);
//                double avgBias = evaluations.stream()
//                                            .filter(score -> score.getBias() != null)
//                                            .mapToDouble(Evaluation::getBias)
//                                            .average()
//                                            .orElse(0.0);
//                double avgHarm = evaluations.stream()
//                                            .filter(score -> score.getHarm() != null)
//                                            .mapToDouble(Evaluation::getHarm)
//                                            .average()
//                                            .orElse(0.0);
//                double avgTrust = evaluations.stream()
//                                             .filter(score -> score.getTrust() != null)
//                                             .mapToDouble(Evaluation::getTrust)
//                                             .average()
//                                             .orElse(0.0);
//
//                // Convert Scores to AnswerDto
//                List<AnswerDto> answerDtos = evaluations.stream().map(score -> {
//                    AnswerDto answerDto = AnswerDto.from(answer);
//                    answerDto.setAccuracy(score.getEvaluationMetrics().accuracy());
//                    answerDto.setComprehensiveness(score.getEvaluationMetrics().comprehensiveness());
//                    answerDto.setClarity(score.getEvaluationMetrics().clarity());
//                    answerDto.setEmpathy(score.getEvaluationMetrics().empathy());
//                    answerDto.setBias(score.getEvaluationMetrics().bias());
//                    answerDto.setHarm(score.getEvaluationMetrics().harm());
//                    answerDto.setTrust(score.getEvaluationMetrics().trust());
//
//                    answerDto.setComment(score.getComment());
//                    answerDto.setUser(score.getUser());
//                    return answerDto;
//                }).toList();
//
//                // Create AnswerResultsDto with averages
//                AnswerResultsDto answerResultsDto = AnswerResultsDto.from(answer);
//                answerResultsDto.setResults(answerDtos);
//                answerResultsDto.setAccuracy(avgAccuracy);
//                answerResultsDto.setComprehensiveness(avgComprehensiveness);
//                answerResultsDto.setClarity(avgClarity);
//                answerResultsDto.setEmpathy(avgEmpathy);
//                answerResultsDto.setBias(avgBias);
//                answerResultsDto.setHarm(avgHarm);
//                answerResultsDto.setTrust(avgTrust);
//
//                answerResultsDto.setComment(null); // Comment might not be averaged, set to null or handle
//                // as needed
//                answerResultsDto.setUser(null);    // User may not be averaged, set to null or handle as
//                // needed
//
//                answerResultsDtos.add(answerResultsDto);
//            }
//
//            QuestionResultsDto questionDto = QuestionResultsDto.from(question, answerResultsDtos);
//            questionDtos.add(questionDto);
//        }
//        return questionDtos;
//        return null;
//    }
}
