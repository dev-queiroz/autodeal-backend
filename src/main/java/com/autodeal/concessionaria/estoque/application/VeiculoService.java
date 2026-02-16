package com.autodeal.concessionaria.estoque.application;

import com.autodeal.concessionaria.estoque.adapter.out.VeiculoRepository;
import com.autodeal.concessionaria.estoque.domain.Fornecedor;
import com.autodeal.concessionaria.estoque.domain.Veiculo;
import com.autodeal.concessionaria.estoque.domain.enums.EstadoVeiculo;
import com.autodeal.concessionaria.shared.vo.Dinheiro;
import com.autodeal.concessionaria.shared.vo.PlacaVeicular;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class VeiculoService {

    private final VeiculoRepository veiculoRepository;

    public VeiculoService(VeiculoRepository veiculoRepository) {
        this.veiculoRepository = veiculoRepository;
    }

    @Transactional
    public Veiculo cadastrarVeiculo(
            String chassi,
            PlacaVeicular placa,
            String marca,
            String modelo,
            Integer anoFabricacao,
            Integer anoModelo,
            String cor,
            Integer quilometragem,
            EstadoVeiculo estadoInicial,
            Dinheiro precoCompra,
            Dinheiro precoVendaSugerido,
            Fornecedor fornecedor
    ) {
        if (veiculoRepository.existsByPlaca(placa)) {
            throw new IllegalArgumentException("Placa já cadastrada no sistema: " + placa.getValue());
        }

        if (veiculoRepository.existsByChassi(chassi)) {
            throw new IllegalArgumentException("Chassi já cadastrado: " + chassi);
        }

        if (precoVendaSugerido.getValue().compareTo(precoCompra.getValue()) < 0) {
            throw new IllegalArgumentException("Preço de venda sugerido não pode ser menor que o preço de compra");
        }

        if (!estadoInicial.isDisponivelParaVenda()) {
            throw new IllegalArgumentException("Estado inicial deve ser um estado disponível para venda");
        }

        Veiculo veiculo = new Veiculo(
                chassi,
                placa,
                marca,
                modelo,
                anoFabricacao,
                anoModelo,
                cor,
                quilometragem,
                estadoInicial,
                precoCompra,
                precoVendaSugerido,
                fornecedor
        );

        return veiculoRepository.save(veiculo);
    }

    @Transactional(readOnly = true)
    public Optional<Veiculo> buscarPorId(Long id) {
        return veiculoRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Veiculo> buscarPorPlaca(PlacaVeicular placa) {
        return veiculoRepository.findByPlaca(placa);
    }

    @Transactional
    public Veiculo alterarEstado(Long veiculoId, EstadoVeiculo novoEstado) {
        Veiculo veiculo = veiculoRepository.findById(veiculoId)
                .orElseThrow(() -> new IllegalArgumentException("Veículo não encontrado: " + veiculoId));

        veiculo.atualizarEstado(novoEstado);
        return veiculoRepository.save(veiculo);
    }

    @Transactional(readOnly = true)
    public List<Veiculo> listarDisponiveisParaVenda() {
        return veiculoRepository.findDisponiveisParaVenda();
    }

    @Transactional(readOnly = true)
    public List<Veiculo> listarPorEstado(EstadoVeiculo estado) {
        return veiculoRepository.findByEstado(estado);
    }
}