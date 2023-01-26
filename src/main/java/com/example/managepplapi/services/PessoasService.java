package com.example.managepplapi.services;
import com.example.managepplapi.dtos.AlterarEnderecoPrincipalDTO;
import com.example.managepplapi.dtos.CriarPessoasDTO;
import com.example.managepplapi.dtos.EnderecoDTOWrapper;
import com.example.managepplapi.entities.Endereco;
import com.example.managepplapi.entities.Pessoa;
import java.util.List;
public interface PessoasService {
    Pessoa save(Pessoa pessoa);

    Pessoa findById(Long id);

    List<Endereco> findAllEnderecos(Long id);

    void addAdress(EnderecoDTOWrapper dadosWrapper, Long id);

    void alterarPrincipal(Long idPessoa, AlterarEnderecoPrincipalDTO novoPrincipal);

    void criaPessoa(CriarPessoasDTO dados);
}
