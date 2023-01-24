package com.example.managepplapi.repositories;
import com.example.managepplapi.entities.Pessoa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface PessoasRepository extends JpaRepository<Pessoa, Long>{


    Page<Pessoa> findAllByCadastradaTrue(Pageable paginacao);
}
