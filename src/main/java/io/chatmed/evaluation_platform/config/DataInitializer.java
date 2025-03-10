package io.chatmed.evaluation_platform.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.chatmed.evaluation_platform.domain.Answer;
import io.chatmed.evaluation_platform.domain.Question;
import io.chatmed.evaluation_platform.domain.Version;
import io.chatmed.evaluation_platform.exceptions.VersionNotFoundException;
import io.chatmed.evaluation_platform.service.domain.AnswerService;
import io.chatmed.evaluation_platform.service.domain.ModelService;
import io.chatmed.evaluation_platform.service.domain.QuestionService;
import io.chatmed.evaluation_platform.service.domain.VersionService;
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
public class DataInitializer {

    private final ModelService modelService;
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final VersionService versionService;

    public DataInitializer(
            ModelService modelService,
            QuestionService questionService,
            AnswerService answerService,
            VersionService versionService
    ) {
        this.modelService = modelService;
        this.questionService = questionService;
        this.answerService = answerService;
        this.versionService = versionService;
    }

    private void initModels() throws IOException {
        ClassPathResource resource = new ClassPathResource("data");
        List<String> modelNames = Arrays.stream(Objects.requireNonNull(resource.getFile().listFiles()))
                                        .filter(file -> file.getName().endsWith(".json"))
                                        .map(file -> file.getName().replace(".json", ""))
                                        .toList();
        modelService.createNewModels(modelNames);
    }

    private Version initVersion() {
        return versionService.save(Version.builder().createdAt(LocalDateTime.now()).build())
                             .orElseThrow(VersionNotFoundException::new);
    }


    @PostConstruct
    public void initData() throws IOException {
        initModels();
        Version version = initVersion();
        ObjectMapper mapper = new ObjectMapper();

        modelService.findAll().forEach(model -> {
            try {
                String fileName = "/data/" + model.getName() + ".json";
                InputStream inputStream = getClass().getResourceAsStream(fileName);
                JsonNode rootNode = mapper.readTree(inputStream);

                rootNode.fields().forEachRemaining(entry -> {
                    Long key = Long.parseLong(entry.getKey());
                    JsonNode node = entry.getValue();

                    Question question = questionService.findByQuestionKey(key).orElseGet(Question::new);

                    if (question.getId() == null) {
                        question.setQuestionKey(key);
                        question.setText(node.get("question").asText());
                        question.setVersion(version);
                        questionService.save(question);
                    }

                    Answer answer = answerService.findByQuestionAndModel(question, model)
                                                 .orElseGet(Answer::new);
                    if (answer.getId() == null) {
                        answer.setAnswerKey(key);
                        answer.setText(node.get("answer").asText());
                        answer.setQuestion(question);
                        answer.setModel(model);
                        answerService.save(answer);
                    }
                });
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
