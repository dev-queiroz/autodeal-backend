package com.autodeal.concessionaria.estoque.adapter.out;

import com.autodeal.concessionaria.estoque.domain.Fornecedor;
import com.autodeal.concessionaria.shared.vo.Cnpj;
import com.autodeal.concessionaria.shared.vo.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {

    Optional<Fornecedor> findByCnpj(Cnpj cnpj);

    Optional<Fornecedor> findByEmail(Email email);

    boolean existsByCnpj(Cnpj cnpj);

    boolean existsByEmail(Email email);
}