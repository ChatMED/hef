package io.chatmed.evaluation_platform.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "models")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Model(String modelName) {
        this.name = modelName;
    }
}
