package com.example.managepplapi.controllers;

import com.example.managepplapi.dtos.CriarPessoasDTO;
import com.example.managepplapi.dtos.EditarPessoaDTO;
import com.example.managepplapi.dtos.EnderecoDTO;
import com.example.managepplapi.dtos.ListagemDePessoasDTO;
import com.example.managepplapi.entities.Pessoa;
import com.example.managepplapi.exceptions.PessoaNaoEncontradaException;
import com.example.managepplapi.repositories.PessoasRepository;
import com.example.managepplapi.services.PessoasService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class PessoasControllerTest {


    @Autowired
    @Mock
    private PessoasRepository repository;

    @Autowired
    private PessoasService pessoasService;

    @InjectMocks
    private PessoasController controller;
    private EnderecoDTO endereco;


    private Pessoa pessoa;
    private CriarPessoasDTO pessoasDTO;

    @BeforeEach
    void setUp() {
        endereco = new EnderecoDTO("Rua dos testes", "0424-004", "1020", "São José dos Testes");
        pessoasDTO = new CriarPessoasDTO("Testinho", LocalDate.now(), Collections.singletonList(endereco));
        pessoa = new Pessoa(pessoasDTO);
    }


    @Test
    void deveriaCriarUmaPessoa() {

        //salvando pessoa
        pessoasService.save(new Pessoa(pessoasDTO));
        assertEquals("São José dos Testes", pessoasDTO.enderecos().get(0).cidade());

    }

    @Test
    void deveriaEditarOsDadosDeUmaPessoa() {

        pessoasService.save(pessoa);
        pessoa.setId(1l);
        //salvando pessoa como "Testinho"

        EditarPessoaDTO pessoaAtualziada = new EditarPessoaDTO(1l, "TestinhA", LocalDate.now(), endereco);
        repository.getReferenceById(pessoaAtualziada.id());
        pessoa.atualizarDadosDaPessoa(pessoaAtualziada);
        //editando pessoa para TestinhA

        assertEquals("TestinhA", pessoa.getNome());

    }

    @Test
    void deveriaConsultarUmaPessoaPeloId() {

        pessoasService.save(pessoa);
        pessoa.setId(1l);
        Pessoa pessoaAchada = pessoasService.findById(1l);

        assertEquals("Testinho", pessoaAchada.getNome());
    }

    @Test
    void deveriaLancarExceptionPessoaNaoEncontrada() {
        pessoa.setId(1l);
        // criando e setando Id da pessoa porém não salvando

        assertThrows(PessoaNaoEncontradaException.class, () -> pessoasService.findById(1l));
    }

    @Test
    void deveriaRetornarListaDePessoas() {
        pessoasService.save(pessoa);
        pessoa.setCadastrada(true);

        EnderecoDTO end2 = new EnderecoDTO("Rua dos testinhos", "1234-023", "421", "São José dos Testinhos");
        CriarPessoasDTO newP2 = new CriarPessoasDTO("Testinha", LocalDate.now(), Collections.singletonList(end2));
        Pessoa p2 = new Pessoa(newP2);
        pessoasService.save(p2);
        p2.setCadastrada(false);
        //criando segunda pessoa e deixando boolean `cadastrada` false.


        List<Pessoa> pessoas = Arrays.asList(pessoa, p2);
        Page<Pessoa> page = new PageImpl<>(pessoas);

        when(repository.findAllByCadastradaTrue(PageRequest.of(0, 10))).thenReturn(page);
        Page<ListagemDePessoasDTO> result = controller.listarPessoas((PageRequest.of(0, 10)));

        assertThat(result.getTotalElements()).isEqualTo(2);


    }




}