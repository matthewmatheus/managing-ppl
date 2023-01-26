package com.example.managepplapi.entities;
import com.example.managepplapi.dtos.EnderecoDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String logradouro;
    private String cep;
    private String numero;
    private String cidade;

    @ManyToOne
    @JoinColumn(name = "pessoa_id")
    @JsonIgnore
    private Pessoa pessoa;
    private Boolean principal;

    public Endereco(EnderecoDTO dados) {
        this.logradouro = dados.logradouro();
        this.cep = dados.cep();
        this.numero = dados.numero();
        this.cidade = dados.cidade();
        this.principal = false;
    }

    public void atualizarDadosEndereco(EnderecoDTO dados) {
        if (dados.logradouro() != null) {
            this.logradouro = dados.logradouro();
        }
        if (dados.cep() != null) {
            this.cep = dados.cep();
        }
        if (dados.numero() != null) {
            this.numero = dados.numero();
        }
        if (dados.cidade() != null) {
            this.cidade = dados.cidade();
        }
    }
}
