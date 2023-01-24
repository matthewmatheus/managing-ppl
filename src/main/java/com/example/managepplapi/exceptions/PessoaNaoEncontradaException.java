package com.example.managepplapi.exceptions;

public class PessoaNaoEncontradaException extends RuntimeException {
    public PessoaNaoEncontradaException(Long id) {

        super("Não foi pessível encontrar a pessoa de id " + id + " !");

    }
}
