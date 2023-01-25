package com.example.managepplapi.services;
import com.example.managepplapi.dtos.ListagemEnderecosDTO;
import com.example.managepplapi.entities.Endereco;
import com.example.managepplapi.entities.Pessoa;

import java.util.List;

public interface PessoasService {
    Pessoa save(Pessoa pessoa);


    Pessoa findById(Long id);


    List<Endereco> findAllEnderecos(Long id);
}
