package com.example.managepplapi.controllers;

import com.example.managepplapi.dtos.EnderecoDTO;
import com.example.managepplapi.entities.Endereco;
import com.example.managepplapi.entities.Pessoa;
import com.example.managepplapi.services.EnderecoService;
import com.example.managepplapi.services.PessoasService;
import jakarta.validation.Valid;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("enderecos")
public class EnderecosController {


    private PessoasService pessoaService;
    private EnderecoService enderecoService;


    public EnderecosController(PessoasService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @PostMapping("/{id}")
    @Transactional
    public void adicionarEndereco(@RequestBody @Valid EnderecoDTO dados, @PathVariable Long id) {

        Pessoa pessoa = pessoaService.findById(id);
        Endereco newAdress = new Endereco();
        newAdress.setLogradouro(dados.logradouro());
        newAdress.setCep(dados.cep());
        newAdress.setNumero(dados.numero());
        newAdress.setCidade(dados.cidade());
        pessoa.getEnderecos().add(newAdress);
        pessoa.setEnderecos(pessoa.getEnderecos());

        pessoaService.save(pessoa);


    }



}
