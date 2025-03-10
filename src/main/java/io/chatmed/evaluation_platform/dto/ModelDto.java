package io.chatmed.evaluation_platform.dto;

import io.chatmed.evaluation_platform.domain.Model;

import java.util.List;
import java.util.stream.Collectors;

public record ModelDto(Long id, String name) {

    public static ModelDto from(Model model) {
        return new ModelDto(model.getId(), model.getName());
    }

    public static List<ModelDto> from(List<Model> models) {
        return models.stream().map(ModelDto::from).collect(Collectors.toList());
    }
}
