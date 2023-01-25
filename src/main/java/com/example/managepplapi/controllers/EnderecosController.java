package com.example.managepplapi.controllers;

import com.example.managepplapi.dtos.EnderecoDTO;
import com.example.managepplapi.dtos.EnderecoDTOWrapper;
import com.example.managepplapi.dtos.ListagemEnderecosDTO;
import com.example.managepplapi.entities.Endereco;
import com.example.managepplapi.entities.Pessoa;
import com.example.managepplapi.repositories.PessoasRepository;
import com.example.managepplapi.services.EnderecoService;
import com.example.managepplapi.services.PessoasService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("enderecos")
public class EnderecosController {


    private PessoasService pessoaService;
    private EnderecoService enderecoService;

    @Autowired
    private PessoasRepository pessoasRepository;
    private EnderecoDTOWrapper dadosWrapper;

    @Autowired


    public EnderecosController(PessoasService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @PostMapping("/{id}")
    @Transactional
    public void adicionarEndereco(@RequestBody @Valid EnderecoDTOWrapper dadosWrapper, @PathVariable Long id) {

        EnderecoDTO dados = dadosWrapper.endereco();
        Optional<Pessoa> pessoaOptional = pessoasRepository.findById(id);
        if (pessoaOptional.isPresent()) {
            Pessoa pessoa = pessoaOptional.get();
            Endereco newAdress = new Endereco();
            newAdress.setLogradouro(dados.logradouro());
            newAdress.setCep(dados.cep());
            newAdress.setNumero(dados.numero());
            newAdress.setCidade(dados.cidade());
            newAdress.setPessoa(pessoa);
            pessoa.getEnderecos().add(newAdress);
            pessoaService.save(pessoa);
        }
    }

    @GetMapping("/lista/{id}")
    public ResponseEntity<List<Endereco>> listarEnderecosPessoa(@PathVariable Long id) {
        List<Endereco> enderecos = pessoaService.findAllEnderecos(id);
        return ResponseEntity.ok(enderecos);

    }


}

