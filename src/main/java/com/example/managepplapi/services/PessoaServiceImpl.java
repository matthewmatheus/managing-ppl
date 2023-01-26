package com.example.managepplapi.services;
import com.example.managepplapi.dtos.AlterarEnderecoPrincipalDTO;
import com.example.managepplapi.dtos.CriarPessoasDTO;
import com.example.managepplapi.dtos.EnderecoDTO;
import com.example.managepplapi.dtos.EnderecoDTOWrapper;
import com.example.managepplapi.entities.Endereco;
import com.example.managepplapi.entities.Pessoa;
import com.example.managepplapi.exceptions.PessoaNaoEncontradaException;
import com.example.managepplapi.repositories.PessoasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Override
    public void addAdress(EnderecoDTOWrapper dadosWrapper, Long id) {
        EnderecoDTO dados = dadosWrapper.endereco();
        Optional<Pessoa> pessoaOptional = repository.findById(id);
        if (pessoaOptional.isPresent()) {
            Pessoa pessoa = pessoaOptional.get();
            Endereco newAdress = new Endereco();
            newAdress.setLogradouro(dados.logradouro());
            newAdress.setCep(dados.cep());
            newAdress.setNumero(dados.numero());
            newAdress.setCidade(dados.cidade());
            newAdress.setPessoa(pessoa);
            pessoa.getEnderecos().add(newAdress);
            newAdress.setPrincipal(false);
            this.save(pessoa);
        }
    }

    @Override
    public void alterarPrincipal(Long idPessoa, AlterarEnderecoPrincipalDTO novoPrincipal) {

        //logica para retirar o TRUE do antigo endereco principal e passar para o end de id passado no BODY.

        Pessoa pessoa = this.findById(idPessoa);
        Endereco enderecoAtual = pessoa.getEnderecos().stream()
                .filter(e -> e.getPrincipal() == true)
                .findFirst()
                .orElse(null);
        if (enderecoAtual != null) {
            enderecoAtual.setPrincipal(false);
        }
        Endereco novoEnderecoPrincipal = pessoa.getEnderecos().stream()
                .filter(e -> e.getId().equals(novoPrincipal.idEndereco()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Endereco nao encontrado"));
        novoEnderecoPrincipal.setPrincipal(true);
        this.save(pessoa);
    }

    @Override
    public void criaPessoa(CriarPessoasDTO dados) {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(dados.nome());
        pessoa.setDataDeNascimento(dados.dataDeNascimento());
        pessoa.setCadastrada(true);
        List<Endereco> enderecos = new ArrayList<>();

        // logica para deixar o primeiro endereço sempre como TRUE por padrão.

        int i = 0;
        boolean principalJaAdicionado = false;
        for (EnderecoDTO enderecoDTO : dados.enderecos()) {
            Endereco enderecoNovo = new Endereco(enderecoDTO);
            enderecoNovo.setPessoa(pessoa);
            if(dados.enderecos().size() > 1) {
                if(i == 0) {
                    enderecoNovo.setPrincipal(true);
                    i++;
                    principalJaAdicionado = true;
                } else{
                    if(!principalJaAdicionado) {
                        enderecoNovo.setPrincipal(true);
                        principalJaAdicionado = true;
                    }
                }
            }
            enderecos.add(enderecoNovo);
        }
        pessoa.setEnderecos(enderecos);
        this.save(pessoa);
    }
}
