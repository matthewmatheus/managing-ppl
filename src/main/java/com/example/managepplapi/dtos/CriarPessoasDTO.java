package com.example.managepplapi.dtos;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record CriarPessoasDTO(

        @NotBlank
        String nome,
        @NotNull
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate dataDeNascimento,
        @NotNull @Valid
        EnderecoDTO endereco


) {


}
