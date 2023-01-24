package com.example.managepplapi.dtos;

import com.example.managepplapi.entities.Pessoa;

import java.time.LocalDate;

public record ListagemDePessoasDTO(Long id, String nome, LocalDate dataDeNascimento) {


    public ListagemDePessoasDTO(Pessoa pessoa) {
        this(pessoa.getId(), pessoa.getNome(), pessoa.getDataDeNascimento());

    }
}
