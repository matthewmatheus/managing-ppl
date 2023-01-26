package com.example.managepplapi.controllers;

import com.example.managepplapi.dtos.AlterarEnderecoPrincipalDTO;
import com.example.managepplapi.dtos.EnderecoDTO;
import com.example.managepplapi.dtos.EnderecoDTOWrapper;
import com.example.managepplapi.entities.Endereco;
import com.example.managepplapi.entities.Pessoa;
import com.example.managepplapi.repositories.PessoasRepository;
import com.example.managepplapi.services.PessoasService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("enderecos")
public class EnderecosController {
    private PessoasService pessoaService;
    @Autowired
    private PessoasRepository pessoasRepository;

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
            newAdress.setPrincipal(false);
            pessoaService.save(pessoa);
        }
    }

    @GetMapping("/lista/{id}")
    public ResponseEntity<List<Endereco>> listarEnderecosPessoa(@PathVariable Long id) {
        List<Endereco> enderecos = pessoaService.findAllEnderecos(id);
        return ResponseEntity.ok(enderecos);

    }

    @PutMapping("/principal/{idPessoa}")
    @Transactional
    public void alterarEnderecoPrincipal(@PathVariable Long idPessoa, @RequestBody AlterarEnderecoPrincipalDTO novoPrincipal) {
        Pessoa pessoa = pessoaService.findById(idPessoa);
        Endereco enderecoAtual = pessoa.getEnderecos().stream()
                .filter(e -> e.getPrincipal() == true)
                .findFirst()
                .orElse(null);
        if (enderecoAtual != null) {
            enderecoAtual.setPrincipal(false);
        }
        Endereco novoEnderecoPrincipal = pessoa.getEnderecos().stream()
                .filter(e -> e.getId().equals(novoPrincipal.idEndereco()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Endereco nao encontrado"));
        novoEnderecoPrincipal.setPrincipal(true);
        pessoaService.save(pessoa);
    }


}

