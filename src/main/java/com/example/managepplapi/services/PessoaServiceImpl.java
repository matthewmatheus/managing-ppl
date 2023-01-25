package com.example.managepplapi.services;

import com.example.managepplapi.dtos.ListagemEnderecosDTO;
import com.example.managepplapi.entities.Endereco;
import com.example.managepplapi.entities.Pessoa;
import com.example.managepplapi.exceptions.PessoaNaoEncontradaException;
import com.example.managepplapi.repositories.PessoasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PessoaServiceImpl implements PessoasService {

    @Autowired
    private PessoasRepository repository;

    @Override
    public Pessoa save(Pessoa pessoa) {
        repository.save(pessoa);
        return pessoa;
    }

    @Override
    public Pessoa findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new PessoaNaoEncontradaException(id));
    }


    @Override
    public List<Endereco> findAllEnderecos(Long id) {
        List<Endereco> enderecos = repository.findAllEnderecosByPessoaId(id);
        return enderecos;
    }


}
