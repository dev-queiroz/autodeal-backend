package com.autodeal.concessionaria.vendas.application;

import com.autodeal.concessionaria.crm.domain.Cliente;
import com.autodeal.concessionaria.estoque.application.VeiculoService;
import com.autodeal.concessionaria.estoque.domain.Veiculo;
import com.autodeal.concessionaria.estoque.domain.enums.EstadoVeiculo;
import com.autodeal.concessionaria.iam.application.FuncionarioService;
import com.autodeal.concessionaria.iam.domain.Funcionario;
import com.autodeal.concessionaria.shared.vo.Dinheiro;
import com.autodeal.concessionaria.vendas.adapter.out.VendaRepository;
import com.autodeal.concessionaria.vendas.domain.Venda;
import com.autodeal.concessionaria.vendas.domain.vo.PorcentagemComissao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class VendaService {

    private final VendaRepository vendaRepository;
    private final VeiculoService veiculoService;
    private final FuncionarioService funcionarioService;
    private final CalculadoraPrecoVenda calculadora;

    public VendaService(VendaRepository vendaRepository,
                        VeiculoService veiculoService,
                        FuncionarioService funcionarioService,
                        CalculadoraPrecoVenda calculadora) {
        this.vendaRepository = vendaRepository;
        this.veiculoService = veiculoService;
        this.funcionarioService = funcionarioService;
        this.calculadora = calculadora;
    }

    @Transactional
    public Venda registrarVenda(Long veiculoId, Long clienteId, Long vendedorId, BigDecimal descontoPercentual) {
        Veiculo veiculo = veiculoService.buscarPorId(veiculoId)
                .orElseThrow(() -> new IllegalArgumentException("Veículo não encontrado"));

        if (!veiculo.getEstado().isDisponivelParaVenda()) {
            throw new IllegalStateException("Veículo não está disponível para venda");
        }

        Cliente cliente = new Cliente(); // Placeholder - em produção viria de ClienteService
        Funcionario vendedor = funcionarioService.buscarPorId(vendedorId)
                .orElseThrow(() -> new IllegalArgumentException("Vendedor não encontrado"));

        Dinheiro precoFinal = calculadora.calcularPrecoVendaFinal(veiculo, descontoPercentual);
        PorcentagemComissao comissao = PorcentagemComissao.of(vendedor.getPorcentagemComissao());

        if (!calculadora.validarMargemMinima(veiculo, precoFinal)) {
            throw new IllegalArgumentException("Margem abaixo do mínimo permitido");
        }

        Venda venda = new Venda(veiculo, cliente, vendedor, precoFinal, comissao);

        veiculoService.alterarEstado(veiculoId, EstadoVeiculo.VENDIDO);

        return vendaRepository.save(venda);
    }
}