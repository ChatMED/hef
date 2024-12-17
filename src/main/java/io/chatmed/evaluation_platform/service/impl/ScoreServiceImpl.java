package io.chatmed.evaluation_platform.service.impl;

import io.chatmed.evaluation_platform.exceptions.ResourceNotFoundException;
import io.chatmed.evaluation_platform.model.Answer;
import io.chatmed.evaluation_platform.model.Score;
import io.chatmed.evaluation_platform.model.dto.AnswerDto;
import io.chatmed.evaluation_platform.repository.AnswerRepository;
import io.chatmed.evaluation_platform.repository.ScoreRepository;
import io.chatmed.evaluation_platform.service.ScoreService;
import org.springframework.stereotype.Service;

@Service
public class ScoreServiceImpl implements ScoreService {
    private final AnswerRepository answerRepository;
    private final ScoreRepository scoreRepository;

    public ScoreServiceImpl(AnswerRepository answerRepository, ScoreRepository scoreRepository) {
        this.answerRepository = answerRepository;
        this.scoreRepository = scoreRepository;
    }

    @Override
    public Score evaluate(AnswerDto answerDto) {
        Answer answer = answerRepository.findById(answerDto.getId()).orElseThrow(ResourceNotFoundException::new);
        Score score = new Score();
        score.setAnswer(answer);
        score.setAccuracy(answerDto.getAccuracy());
        score.setBias(answerDto.getAccuracy());
        score.setFeedback(answerDto.getComment());
        score.setCompleteness(answerDto.getAccuracy());
        score.setRelevance(answerDto.getAccuracy());
        score.setSafety(answerDto.getAccuracy());
        score.setUser(answerDto.getUser());

        return scoreRepository.save(score);
    }
}
