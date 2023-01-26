package com.example.managepplapi.exceptions;

public class PessoaNaoEncontradaException extends RuntimeException {
    public PessoaNaoEncontradaException(Long id) {

        super("Não foi possível encontrar a pessoa de id " + id + " !");

    }
}
