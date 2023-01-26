package com.example.managepplapi.controllers;
import com.example.managepplapi.dtos.AlterarEnderecoPrincipalDTO;
import com.example.managepplapi.dtos.CriarPessoasDTO;
import com.example.managepplapi.dtos.EnderecoDTO;
import com.example.managepplapi.dtos.EnderecoDTOWrapper;
import com.example.managepplapi.entities.Endereco;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class EnderecosControllerTest {
    private MockMvc mockMvc;
    @InjectMocks
    private EnderecosController controller;
    @Mock
    private PessoasService pessoasService;
    @Mock
    private PessoasRepository pessoasRepository;
    private EnderecoDTO endereco;
    private CriarPessoasDTO pessoaDTO;
    private Pessoa pessoa;
    private EnderecoDTO end3;
    private EnderecoDTO end2;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        List<EnderecoDTO> enderecos = new ArrayList<>();
        end3 = new EnderecoDTO("Rua dos testinhos", "13242-555", "120", "São José dos Testinhoss");
        end2 = new EnderecoDTO("Rua dos testezões", "25342-231", "410", "São José dos Testões");
        endereco = new EnderecoDTO("Rua dos testes", "0424-004", "1020", "São José dos Testes");
        enderecos.add(endereco);
        enderecos.add(end2);
        enderecos.add(end3);

        pessoaDTO = new CriarPessoasDTO("Testinho", LocalDate.now(), enderecos);
        pessoa = new Pessoa(pessoaDTO);
        pessoa.getEnderecos().get(0).setPrincipal(true);
        pessoa.getEnderecos().get(0).setId(1l);
        pessoa.getEnderecos().get(1).setId(2l);
        pessoa.getEnderecos().get(2).setId(3l);
    }


    @Test
    @DisplayName("Deveria adicionar um endereco a pessoa e retornar 2 enderecos no teste")
    void deveriaAdicionarUmEnderecoAPessoa() {
        Long id = 1l;
        pessoa.setId(id);
        EnderecoDTOWrapper dadosWrapper = new EnderecoDTOWrapper(endereco);
        when(pessoasRepository.findById(id)).thenReturn(Optional.of(pessoa));

        controller.adicionarEndereco(dadosWrapper, id);

        verify(pessoasRepository, times(1)).findById(id);
        assertEquals(4, pessoa.getEnderecos().size());
    }

    @Test
    void deveriaListarOsEnderecosDaPessoa() {
        Long id = 1l;

        when(pessoasService.findAllEnderecos(id)).thenReturn(pessoa.getEnderecos());
        ResponseEntity<List<Endereco>> response = controller.listarEnderecosPessoa(id);

        verify(pessoasService,times(1)).findAllEnderecos(id);
        assertEquals(200,response.getStatusCodeValue());
        assertEquals(3, response.getBody().size());
    }


    @Test
    @DisplayName("Deveria alterar o endereco principal atual e retornar o novo endereco principal")
    void deveriaAlterarOEnderecoPrincipal() {
        Long id = 1l;
        pessoa.setId(id);

        AlterarEnderecoPrincipalDTO novoPrincipal = new AlterarEnderecoPrincipalDTO(3l);

        when(pessoasService.findById(id)).thenReturn(pessoa);
        controller.alterarEnderecoPrincipal(id, novoPrincipal);

        assertEquals(pessoa.getEnderecos().get(0).getPrincipal().booleanValue(), false);
        assertEquals(pessoa.getEnderecos().get(2).getPrincipal().booleanValue(), true);
    }
}