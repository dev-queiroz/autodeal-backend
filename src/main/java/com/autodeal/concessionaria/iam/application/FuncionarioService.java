package com.autodeal.concessionaria.iam.application;

import com.autodeal.concessionaria.iam.adapter.out.FuncionarioRepository;
import com.autodeal.concessionaria.iam.domain.Funcionario;
import com.autodeal.concessionaria.iam.domain.enums.PerfilAcesso;
import com.autodeal.concessionaria.shared.vo.Cpf;
import com.autodeal.concessionaria.shared.vo.Email;
import com.autodeal.concessionaria.shared.vo.NomeCompleto;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class FuncionarioService {

    private final FuncionarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    public FuncionarioService(FuncionarioRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Funcionario criarFuncionario(
            NomeCompleto nomeCompleto,
            Cpf cpf,
            Email email,
            String senhaPlana,
            PerfilAcesso perfil
    ) {
        if (repository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email já cadastrado");
        }
        if (repository.existsByCpf(cpf)) {
            throw new IllegalArgumentException("CPF já cadastrado");
        }

        String senhaHash = passwordEncoder.encode(senhaPlana);

        Funcionario funcionario = new Funcionario(nomeCompleto, cpf, email, senhaHash, perfil);
        return repository.save(funcionario);
    }

    public List<Funcionario> listarTodos() {
        return repository.findAll();
    }

    public Optional<Funcionario> buscarPorEmail(Email email) {
        return repository.findByEmail(email);
    }

    public Optional<Funcionario> buscarPorCpf(Cpf cpf) {
        return repository.findByCpf(cpf);
    }

    public Optional<Funcionario> buscarPorId(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public void trocarSenha(Long id, String senhaAtual, String novaSenha) {
        Funcionario f = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Funcionário não encontrado"));

        if (!passwordEncoder.matches(senhaAtual, f.getSenhaHash())) {
            throw new IllegalArgumentException("Senha atual incorreta");
        }

        f.atualizarSenha(passwordEncoder.encode(novaSenha));
        repository.save(f);
    }
}