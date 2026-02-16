package com.autodeal.concessionaria.iam.adapter.out;

import com.autodeal.concessionaria.iam.domain.Funcionario;
import com.autodeal.concessionaria.shared.vo.Cpf;
import com.autodeal.concessionaria.shared.vo.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    Optional<Funcionario> findByEmail(Email email);

    Optional<Funcionario> findByCpf(Cpf cpf);

    boolean existsByEmail(Email email);

    boolean existsByCpf(Cpf cpf);

    // Pode adicionar mais queries conforme necessidade (ex: por perfil, ativos, etc.)
    // Optional<List<Funcionario>> findByPerfilAcessoAndAtivoTrue(PerfilAcesso perfil);
}