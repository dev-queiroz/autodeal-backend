package com.autodeal.concessionaria.crm.application;

import com.autodeal.concessionaria.crm.adapter.out.ClienteRepository;
import com.autodeal.concessionaria.crm.domain.Cliente;
import com.autodeal.concessionaria.shared.vo.Cpf;
import com.autodeal.concessionaria.shared.vo.Email;
import com.autodeal.concessionaria.shared.vo.NomeCompleto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Cliente cadastrarCliente(NomeCompleto nome, Cpf cpf, Email email, String telefone) {
        if (repository.existsByCpf(cpf)) {
            throw new IllegalArgumentException("CPF já cadastrado");
        }
        if (repository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email já cadastrado");
        }

        Cliente cliente = new Cliente(nome, cpf, email, telefone);
        return repository.save(cliente);
    }

    @Transactional(readOnly = true)
    public Optional<Cliente> buscarPorId(Long id) {
        return repository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Cliente> listarTodos() {
        return repository.findAll();
    }
}