package io.chatmed.evaluation_platform.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.chatmed.evaluation_platform.domain.Answer;
import io.chatmed.evaluation_platform.domain.Question;
import io.chatmed.evaluation_platform.domain.Workspace;
import io.chatmed.evaluation_platform.exceptions.ResourceNotFoundException;
import io.chatmed.evaluation_platform.service.domain.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 DataInitializer should work with test folder for testing, data folder for production.
 */
@Component
public class DataInitializer {

    private static final String DATA_LOCATION = "classpath:data/*.json";
    private final long workspaceId;
    private final String workspaceName;

    private final ModelService modelService;
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final WorkspaceService workspaceService;
    private final UserService userService;
    private final ObjectMapper objectMapper;
    private final MembershipService membershipService;

    public DataInitializer(
            ModelService modelService,
            QuestionService questionService,
            AnswerService answerService,
            WorkspaceService workspaceService,
            UserService userService,
            ObjectMapper objectMapper,
            @Value("${data.init.workspace.id}") long workspaceId,
            @Value("${data.init.workspace.name}") String workspaceName,
            MembershipService membershipService
    ) {
        this.modelService = modelService;
        this.questionService = questionService;
        this.answerService = answerService;
        this.workspaceService = workspaceService;
        this.userService = userService;
        this.objectMapper = objectMapper;
        this.workspaceId = workspaceId;
        this.workspaceName = workspaceName;
        this.membershipService = membershipService;
    }

    private void initModels(Resource[] resources, Workspace workspace) {
        List<String> modelNames = new ArrayList<>();

        for (Resource resource : resources) {
            String fileName = Objects.requireNonNull(resource.getFilename());
            if (fileName.endsWith(".json")) {
                modelNames.add(fileName.replace(".json", ""));
            }
        }
        modelService.createNewModels(modelNames, workspace);
    }

    private void initWorkspaceMemberships(Workspace workspace, Question question) {
        userService.findAll().forEach(user -> {
            if (membershipService.findMembership(user, workspace).isEmpty())
                membershipService.save(user, workspace, question);
        });
    }

    @PostConstruct
    public void initData() throws IOException {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources(DATA_LOCATION);

        Workspace workspace = workspaceService.findById(workspaceId)
                                              .orElseGet(() -> workspaceService.save(Workspace.builder()
                                                                                              .name(workspaceName)
                                                                                              .createdAt(LocalDateTime.now())
                                                                                              .build()));
        initModels(resources, workspace);

        for (Resource resource : resources) {
            try (InputStream inputStream = resource.getInputStream()) {
                JsonNode rootNode = objectMapper.readTree(inputStream);
                String modelName = Objects.requireNonNull(resource.getFilename()).replace(".json", "");

                modelService.findByWorkspaceAndName(workspace, modelName).ifPresent(model -> {
                    rootNode.fields().forEachRemaining(entry -> {
                        Long key = Long.parseLong(entry.getKey());
                        JsonNode node = entry.getValue();

                        Question question = questionService.findByWorkspaceAndQuestionKey(workspace, key)
                                                           .orElseGet(Question::new);
                        if (question.getId() == null) {
                            question.setQuestionKey(key);
                            question.setText(node.get("question").asText());
                            question.setWorkspace(workspace);
                            questionService.save(question);
                        }

                        Answer answer = answerService.findByQuestionAndModel(question, model).orElseGet(Answer::new);
                        if (answer.getId() == null) {
                            answer.setAnswerKey(key);
                            answer.setText(node.get("answer").asText());
                            answer.setQuestion(question);
                            answer.setModel(model);
                            answer.setWorkspace(workspace);
                            answerService.save(answer);
                        }
                    });
                });
            } catch (Exception e) {
                throw new RuntimeException("Error reading resource: " + resource.getFilename(), e);
            }
        }

        Question firstQuestionInWorkspace = questionService.findFirstQuestion(workspaceId)
                                                           .orElseThrow(ResourceNotFoundException::new);
        initWorkspaceMemberships(workspace, firstQuestionInWorkspace);
    }
}
