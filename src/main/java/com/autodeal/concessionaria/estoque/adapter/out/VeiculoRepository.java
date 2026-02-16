package com.autodeal.concessionaria.estoque.adapter.out;

import com.autodeal.concessionaria.estoque.domain.Veiculo;
import com.autodeal.concessionaria.estoque.domain.enums.EstadoVeiculo;
import com.autodeal.concessionaria.shared.vo.PlacaVeicular;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {

    Optional<Veiculo> findByPlaca(PlacaVeicular placa);

    boolean existsByPlaca(PlacaVeicular placa);

    boolean existsByChassi(String chassi);

    List<Veiculo> findByEstado(EstadoVeiculo estado);

    List<Veiculo> findByEstadoIn(List<EstadoVeiculo> estados);

    // Consulta útil para dashboard / estoque disponível
    List<Veiculo> findByEstadoInAndAtivoTrueOrderByDataEntradaEstoqueAsc(
            List<EstadoVeiculo> estados
    );

    // Busca por marca + modelo parcial (LIKE)
    List<Veiculo> findByMarcaContainingIgnoreCaseAndModeloContainingIgnoreCase(
            String marca,
            String modelo
    );

    // Exemplo: veículos disponíveis para venda
    default List<Veiculo> findDisponiveisParaVenda() {
        return findByEstadoInAndAtivoTrueOrderByDataEntradaEstoqueAsc(
                List.of(EstadoVeiculo.NOVO, EstadoVeiculo.SEMINOVO, EstadoVeiculo.USADO, EstadoVeiculo.DEMONSTRACAO, EstadoVeiculo.CONSIGNADO)
        );
    }
}