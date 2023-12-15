package br.edu.infnet.ServWebJava.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Digimon {

    @JsonProperty("id")
    private Long ID;

    @JsonProperty("name")
    private String nome;

    @JsonProperty("levels")
    private List<Map<String, Object>> levels;

    public Digimon() {
    }

    public Digimon(Long ID, String nome, List<Map<String, Object>> levels) {
        this.ID = ID;
        this.nome = nome;
        this.levels = levels;
    }
}