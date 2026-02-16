package com.autodeal.concessionaria.vendas.adapter.out;

import com.autodeal.concessionaria.vendas.domain.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Long> {
}