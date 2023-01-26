package com.example.managepplapi.controllers;
import com.example.managepplapi.dtos.AlterarEnderecoPrincipalDTO;
import com.example.managepplapi.dtos.EnderecoDTOWrapper;
import com.example.managepplapi.entities.Endereco;
import com.example.managepplapi.services.PessoasService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("enderecos")
public class EnderecosController {
    private PessoasService pessoaService;

    public EnderecosController(PessoasService pessoaService) {
        this.pessoaService = pessoaService;
    }


    @PostMapping("/{id}")
    @Transactional
    public void adicionarEndereco(@RequestBody @Valid EnderecoDTOWrapper dadosWrapper, @PathVariable Long id) {
        pessoaService.addAdress(dadosWrapper, id);
    }

    @GetMapping("/lista/{id}")
    public ResponseEntity<List<Endereco>> listarEnderecosPessoa(@PathVariable Long id) {
        List<Endereco> enderecos = pessoaService.findAllEnderecos(id);
        return ResponseEntity.ok(enderecos);

    }

    @PutMapping("/principal/{idPessoa}")
    @Transactional
    public void alterarEnderecoPrincipal(@PathVariable Long idPessoa, @RequestBody AlterarEnderecoPrincipalDTO novoPrincipal) {
        pessoaService.alterarPrincipal(idPessoa, novoPrincipal);
    }


}

