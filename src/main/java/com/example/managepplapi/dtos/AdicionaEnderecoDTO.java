package com.example.managepplapi.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import org.springframework.lang.Nullable;

public record AdicionaEnderecoDTO(

        @Nullable
        @JsonIgnore
        Long id,
        EnderecoDTO novoEndereco
) {
}
