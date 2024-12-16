package io.chatmed.evaluation_platform.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.chatmed.evaluation_platform.model.Answer;
import io.chatmed.evaluation_platform.model.Model;
import io.chatmed.evaluation_platform.model.Question;
import io.chatmed.evaluation_platform.repository.AnswerRepository;
import io.chatmed.evaluation_platform.repository.ModelRepository;
import io.chatmed.evaluation_platform.repository.QuestionRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Optional;

@Component
public class DataInitializer {

    private final QuestionRepository questionRepository;

    private final AnswerRepository answerRepository;

    private final ModelRepository modelRepository;

    public DataInitializer(QuestionRepository questionRepository, AnswerRepository answerRepository, ModelRepository modelRepository) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.modelRepository = modelRepository;
    }

    @PostConstruct
    public void initData() {
        try {
            String name = "mistral-7B-Instruct-v0.3";
            ObjectMapper mapper = new ObjectMapper();
            InputStream inputStream = getClass().getResourceAsStream("/data.json");
            JsonNode rootNode = mapper.readTree(inputStream);

            rootNode.fields().forEachRemaining(entry -> {
                JsonNode node = entry.getValue();
                Model m = null;
                Optional<Model> model = modelRepository.findByName(name);
                if (model.isEmpty()) {
                    m = new Model();
                    m.setName(name);
                    modelRepository.save(m);
                } else {
                    m = model.get();
                }
                Question q = null;
                Optional<Question> question = questionRepository.findByText(node.get("question").asText());
                if (question.isEmpty()) {
                    q = new Question();
                    q.setText(node.get("question").asText());
                    questionRepository.save(q);
                } else {
                    q = question.get();
                }
                Answer a = null;
                Optional<Answer> answer = answerRepository.findByQuestionAndModel(q, m);
                if (answer.isEmpty()) {
                    a = new Answer();
                    a.setQuestion(q);
                    a.setModel(m);
                    a.setText(node.get("answer").asText());
                    answerRepository.save(a);
                }
            });

            System.out.println("Data loaded successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
