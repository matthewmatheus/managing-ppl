package com.example.managepplapi.dtos;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record EditarPessoaDTO(

        @NotNull
        Long id,
        String nome,
        LocalDate dataDeNascimento,
        EnderecoDTO endereco)


{


}
