package com.example.managepplapi.entities;

import com.example.managepplapi.dtos.CriarPessoasDTO;
import com.example.managepplapi.dtos.EditarPessoaDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "pessoas")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataDeNascimento;


    @Embedded
    private List<Endereco> enderecos = new ArrayList<>();

    private Boolean cadastrada;

    public Pessoa(CriarPessoasDTO dados) {
        this.cadastrada = true;
        this.nome = dados.nome();
        this.dataDeNascimento = dados.dataDeNascimento();
        this.enderecos = dados.enderecos();
    }

    public void atualizarDadosDaPessoa(EditarPessoaDTO dados) {
        if (dados.nome() != null) {
            this.nome = dados.nome();
        }
        if (dados.dataDeNascimento() != null) {
            this.dataDeNascimento = dados.dataDeNascimento();
        }
            if (dados.endereco() != null && this.enderecos != null) {
                for (Endereco e : enderecos) {
                    e.atualizarDadosEndereco(dados.endereco());
                }
            }
        }


    }

