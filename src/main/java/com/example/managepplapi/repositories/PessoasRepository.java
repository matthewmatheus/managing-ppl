package com.example.managepplapi.repositories;
import com.example.managepplapi.dtos.ListagemEnderecosDTO;
import com.example.managepplapi.entities.Endereco;
import com.example.managepplapi.entities.Pessoa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PessoasRepository extends JpaRepository<Pessoa, Long>{


    Page<Pessoa> findAllByCadastradaTrue(Pageable paginacao);

    List<Endereco> findAllById(Long id);


    @Query("SELECT p FROM Pessoa p JOIN FETCH p.enderecos WHERE p.id = :id")
    List<Endereco> findAllEnderecosByPessoaId(@Param("id") Long id);
}
