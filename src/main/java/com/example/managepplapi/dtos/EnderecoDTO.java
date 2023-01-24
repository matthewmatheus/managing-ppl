package com.example.managepplapi.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EnderecoDTO(

        @NotBlank
        String logradouro,
        @NotBlank
//        @Pattern(regexp = "\\d{8}")
        String cep,
        @NotNull
        String numero,
        @NotBlank
        String cidade
) {
}
