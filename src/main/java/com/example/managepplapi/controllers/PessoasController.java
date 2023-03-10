package com.example.managepplapi.controllers;
import com.example.managepplapi.dtos.*;
import com.example.managepplapi.entities.Pessoa;
import com.example.managepplapi.repositories.PessoasRepository;
import com.example.managepplapi.services.PessoasService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("pessoas")
public class PessoasController {
    @Autowired
    private PessoasRepository repository;
    private PessoasService pessoaService;

    public PessoasController(PessoasService pessoaService, PessoasRepository repository) {
        this.pessoaService = pessoaService;
        this.repository = repository;
    }

    @PostMapping
    @Transactional
    public void criarPessoa(@RequestBody @Valid CriarPessoasDTO dados) {
       pessoaService.criaPessoa(dados);
    }


    @PutMapping
    @Transactional
    public void editarDadosPessoa(@RequestBody @Valid EditarPessoaDTO dados) {
        var pessoa = repository.getReferenceById(dados.id());
        pessoa.atualizarDadosDaPessoa(dados);
    }

    @GetMapping("/{id}")
    @Transactional
    public Pessoa consultarPessoaPorId(@PathVariable Long id) {
        return pessoaService.findById(id);

    }

    //Lista todas as pessoas que estão cadastradas
    @GetMapping()
    public Page<ListagemDePessoasDTO> listarPessoas(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        return repository.findAllByCadastradaTrue(paginacao).map(ListagemDePessoasDTO::new);

    }


}

