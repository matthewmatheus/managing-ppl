package com.example.managepplapi.services;
import com.example.managepplapi.dtos.EnderecoDTO;
import com.example.managepplapi.entities.Endereco;
import com.example.managepplapi.entities.Pessoa;

import java.util.List;
import java.util.Set;

public interface PessoasService {
    Pessoa save(Pessoa pessoa);


    Pessoa findById(Long id);


    Pessoa findAllEnderecos(Long id);
}
