package com.example.managepplapi.controllers;
import com.example.managepplapi.dtos.CriarPessoasDTO;
import com.example.managepplapi.dtos.EditarPessoaDTO;
import com.example.managepplapi.dtos.EnderecoDTO;
import com.example.managepplapi.dtos.ListagemDePessoasDTO;
import com.example.managepplapi.entities.Pessoa;
import com.example.managepplapi.repositories.PessoasRepository;
import com.example.managepplapi.services.PessoasService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
        pessoasService.save(pessoa);
        pessoa.setId(1l);

    }


    @Test
    @DisplayName("Deveria persistir com sucesso uma pessoa e seu nome de cidade bater com o assert")
    void deveriaCriarUmaPessoa() {

        assertEquals("São José dos Testes", pessoa.getEnderecos().get(0).getCidade());

    }

    @Test
    @DisplayName("Deveria editar os dados de uma pessoa e trocar o nome de Testinho para TestinhA")
    void deveriaEditarOsDadosDeUmaPessoa() {

        EditarPessoaDTO pessoaAtualizada = new EditarPessoaDTO(1l, "TestinhA", LocalDate.now(), endereco);
        repository.getReferenceById(pessoaAtualizada.id());
        pessoa.atualizarDadosDaPessoa(pessoaAtualizada);
        //editando pessoa para TestinhA

        assertEquals("TestinhA", pessoa.getNome());
    }

    @Test
    @DisplayName("Deveria consultar e encontrar uma pessoa pelo id")
    void deveriaConsultarUmaPessoaPeloId() {

        Pessoa pessoaAchada = pessoasService.findById(1l);
        assertEquals("Testinho", pessoaAchada.getNome());
    }



//    @Test
//    void deveriaLancarExceptionPessoaNaoEncontrada() throws Exception  {
//        Long id = 2l;
//
//        assertThrows(PessoaNaoEncontradaException.class, () -> pessoasService.findById(id));
//
//    }



    @Test
    @DisplayName("Deveria retornar uma lista de 2 pessoas")
    void deveriaRetornarListaDePessoas() {

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