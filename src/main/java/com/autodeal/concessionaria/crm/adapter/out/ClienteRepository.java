package com.autodeal.concessionaria.crm.adapter.out;

import com.autodeal.concessionaria.crm.domain.Cliente;
import com.autodeal.concessionaria.shared.vo.Cpf;
import com.autodeal.concessionaria.shared.vo.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByCpf(Cpf cpf);

    Optional<Cliente> findByEmail(Email email);

    boolean existsByCpf(Cpf cpf);

    boolean existsByEmail(Email email);
}