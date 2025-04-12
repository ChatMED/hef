package io.chatmed.evaluation_platform.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.chatmed.evaluation_platform.domain.Answer;
import io.chatmed.evaluation_platform.domain.Question;
import io.chatmed.evaluation_platform.service.domain.AnswerService;
import io.chatmed.evaluation_platform.service.domain.ModelService;
import io.chatmed.evaluation_platform.service.domain.QuestionService;
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class DataInitializer {

    /*
            Work with test folder for testing, data for production.
     */

    private static final String DATA_LOCATION = "classpath:data/*.json";
//    private static final String DATA_LOCATION = "classpath:test/*.json";

    private final ModelService modelService;
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final ObjectMapper objectMapper;

    public DataInitializer(
            ModelService modelService,
            QuestionService questionService,
            AnswerService answerService,
            ObjectMapper objectMapper
    ) {
        this.modelService = modelService;
        this.questionService = questionService;
        this.answerService = answerService;
        this.objectMapper = objectMapper;
    }

    private void initModels() throws IOException {
        List<String> modelNames = new ArrayList<>();

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources(DATA_LOCATION);

        for (Resource resource : resources) {
            String fileName = Objects.requireNonNull(resource.getFilename());
            if (fileName.endsWith(".json")) {
                modelNames.add(fileName.replace(".json", ""));
            }
        }
        modelService.createNewModels(modelNames);
    }

    @PostConstruct
    public void initData() throws IOException {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources(DATA_LOCATION);

        for (Resource resource : resources) {
            try (InputStream inputStream = resource.getInputStream()) {
                JsonNode rootNode = objectMapper.readTree(inputStream);
                String modelName = Objects.requireNonNull(resource.getFilename()).replace(".json", "");

                modelService.findByName(modelName).ifPresent(model -> {
                    rootNode.fields().forEachRemaining(entry -> {
                        Long key = Long.parseLong(entry.getKey());
                        JsonNode node = entry.getValue();

                        Question question = questionService.findByQuestionKey(key).orElseGet(Question::new);
                        if (question.getId() == null) {
                            question.setQuestionKey(key);
                            question.setText(node.get("question").asText());
                            questionService.save(question);
                        }

                        Answer answer = answerService.findByQuestionAndModel(question, model).orElseGet(Answer::new);
                        if (answer.getId() == null) {
                            answer.setAnswerKey(key);
                            answer.setText(node.get("answer").asText());
                            answer.setQuestion(question);
                            answer.setModel(model);
                            answerService.save(answer);
                        }
                    });
                });

            } catch (Exception e) {
                throw new RuntimeException("Error reading resource: " + resource.getFilename(), e);
            }
        }
    }
}
