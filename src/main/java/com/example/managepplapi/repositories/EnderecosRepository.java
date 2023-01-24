package com.example.managepplapi.repositories;

import com.example.managepplapi.entities.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecosRepository extends JpaRepository<Endereco, Long> {



}
