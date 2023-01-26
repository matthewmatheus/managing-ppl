package com.example.managepplapi.dtos;
import com.example.managepplapi.entities.Endereco;
import com.example.managepplapi.entities.Pessoa;

public record ListagemEnderecosDTO(Long id, String logradouro, String cep, String numero, String cidade) {


    public ListagemEnderecosDTO(Endereco enderecos, Pessoa pessoa) {
        this(pessoa.getId(), enderecos.getLogradouro(), enderecos.getCep(), enderecos.getNumero(),enderecos.getCidade());
    }

}
