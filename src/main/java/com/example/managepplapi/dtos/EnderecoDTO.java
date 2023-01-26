package com.example.managepplapi.dtos;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record EnderecoDTO(
        @JsonProperty("logradouro")
        @NotBlank
        String logradouro,
        @Pattern(regexp = "\\d{8}")
        @NotBlank
        @JsonProperty("cep")
        String cep,
        @NotNull
        @JsonProperty("numero")
        String numero,
        @NotBlank
        @JsonProperty("cidade")
        String cidade
) {
}
