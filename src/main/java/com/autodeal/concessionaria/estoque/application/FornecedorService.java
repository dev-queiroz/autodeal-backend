package com.autodeal.concessionaria.estoque.application;

import com.autodeal.concessionaria.estoque.adapter.out.FornecedorRepository;
import com.autodeal.concessionaria.estoque.domain.Fornecedor;
import com.autodeal.concessionaria.shared.vo.Cnpj;
import com.autodeal.concessionaria.shared.vo.Email;
import com.autodeal.concessionaria.shared.vo.NomeCompleto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class FornecedorService {

    private final FornecedorRepository repository;

    public FornecedorService(FornecedorRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Fornecedor criarFornecedor(
            NomeCompleto nome,
            Cnpj cnpj,
            Email email,
            String telefone,
            String contatoResponsavel
    ) {
        if (repository.existsByCnpj(cnpj)) {
            throw new IllegalArgumentException("CNPJ já cadastrado: " + cnpj.getValue());
        }
        if (repository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email já cadastrado: " + email.getValue());
        }

        Fornecedor fornecedor = new Fornecedor(nome, cnpj, email, telefone, contatoResponsavel);
        return repository.save(fornecedor);
    }

    public List<Fornecedor> buscarTodos() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Fornecedor> buscarPorId(Long id) {
        return repository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Fornecedor> buscarPorCnpj(Cnpj cnpj) {
        return repository.findByCnpj(cnpj);
    }
}