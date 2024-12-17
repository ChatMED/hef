package io.chatmed.evaluation_platform.service.impl;

import io.chatmed.evaluation_platform.model.Answer;
import io.chatmed.evaluation_platform.model.Question;
import io.chatmed.evaluation_platform.model.Score;
import io.chatmed.evaluation_platform.model.dto.AnswerDto;
import io.chatmed.evaluation_platform.model.dto.AnswerResultsDto;
import io.chatmed.evaluation_platform.model.dto.QuestionDto;
import io.chatmed.evaluation_platform.model.dto.QuestionResultsDto;
import io.chatmed.evaluation_platform.repository.AnswerRepository;
import io.chatmed.evaluation_platform.repository.QuestionRepository;
import io.chatmed.evaluation_platform.repository.ScoreRepository;
import io.chatmed.evaluation_platform.service.QuestionService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final ScoreRepository scoreRepository;

    public QuestionServiceImpl(QuestionRepository questionRepository, AnswerRepository answerRepository, ScoreRepository scoreRepository) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.scoreRepository = scoreRepository;
    }

    @Override
    public List<QuestionDto> getQuestions() {
        List<Question> questions = questionRepository.findAll();
        List<QuestionDto> questionDtos = new ArrayList<>();
        for (Question question : questions) {
            List<AnswerDto> answerDtos = answerRepository.findByQuestion(question).stream().map(AnswerDto::from).toList();
            QuestionDto questionDto = QuestionDto.from(question, answerDtos);
            questionDtos.add(questionDto);
        }
        return questionDtos.stream().sorted(Comparator.comparing(QuestionDto::getId)).collect(Collectors.toList());
    }
    @Override
    public List<QuestionResultsDto> getResults() {
        List<Question> questions = questionRepository.findAll();
        List<QuestionResultsDto> questionDtos = new ArrayList<>();

        for (Question question : questions) {
            List<Answer> answers = answerRepository.findByQuestion(question);
            List<AnswerResultsDto> answerResultsDtos = new ArrayList<>();

            for (Answer answer : answers) {
                List<Score> scores = scoreRepository.findAllByAnswer(answer);

                // Calculate averages
                double avgBias = scores.stream().mapToDouble(Score::getBias).average().orElse(0.0);
                double avgAccuracy = scores.stream().mapToDouble(Score::getAccuracy).average().orElse(0.0);
                double avgCompleteness = scores.stream().mapToDouble(Score::getCompleteness).average().orElse(0.0);
                double avgRelevance = scores.stream().mapToDouble(Score::getRelevance).average().orElse(0.0);
                double avgSafety = scores.stream().mapToDouble(Score::getSafety).average().orElse(0.0);

                // Convert Scores to AnswerDto
                List<AnswerDto> answerDtos = scores.stream().map(score -> {
                    AnswerDto answerDto = AnswerDto.from(answer);
                    answerDto.setBias(score.getBias());
                    answerDto.setAccuracy(score.getAccuracy());
                    answerDto.setCompleteness(score.getCompleteness());
                    answerDto.setRelevance(score.getRelevance());
                    answerDto.setSafety(score.getSafety());
                    answerDto.setComment(score.getFeedback());
                    answerDto.setUser(score.getUser());
                    return answerDto;
                }).toList();

                // Create AnswerResultsDto with averages
                AnswerResultsDto answerResultsDto = AnswerResultsDto.from(answer);
                answerResultsDto.setResults(answerDtos);
                answerResultsDto.setBias(avgBias);
                answerResultsDto.setAccuracy(avgAccuracy);
                answerResultsDto.setCompleteness(avgCompleteness);
                answerResultsDto.setRelevance(avgRelevance);
                answerResultsDto.setSafety(avgSafety);
                answerResultsDto.setComment(null); // Comment might not be averaged, set to null or handle as needed
                answerResultsDto.setUser(null);    // User may not be averaged, set to null or handle as needed

                answerResultsDtos.add(answerResultsDto);
            }

            QuestionResultsDto questionDto = QuestionResultsDto.from(question, answerResultsDtos);
            questionDtos.add(questionDto);
        }
        return questionDtos;
    }

}
