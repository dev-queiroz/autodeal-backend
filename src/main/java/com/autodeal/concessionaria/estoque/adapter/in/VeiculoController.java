package com.autodeal.concessionaria.estoque.adapter.in;

import com.autodeal.concessionaria.estoque.application.FornecedorService;
import com.autodeal.concessionaria.estoque.application.VeiculoService;
import com.autodeal.concessionaria.estoque.domain.Fornecedor;
import com.autodeal.concessionaria.estoque.domain.Veiculo;
import com.autodeal.concessionaria.estoque.domain.enums.EstadoVeiculo;
import com.autodeal.concessionaria.estoque.dto.VeiculoRequest;
import com.autodeal.concessionaria.estoque.dto.VeiculoResponse;
import com.autodeal.concessionaria.estoque.dto.VeiculoResumoResponse;
import com.autodeal.concessionaria.shared.vo.Dinheiro;
import com.autodeal.concessionaria.shared.vo.PlacaVeicular;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estoque/veiculos")
public class VeiculoController {

    private final VeiculoService veiculoService;
    private final FornecedorService fornecedorService;

    public VeiculoController(VeiculoService veiculoService, FornecedorService fornecedorService) {
        this.veiculoService = veiculoService;
        this.fornecedorService = fornecedorService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('GERENTE', 'ADMIN')")
    public ResponseEntity<VeiculoResponse> cadastrar(@Valid @RequestBody VeiculoRequest request) {
        Fornecedor fornecedor = fornecedorService.buscarPorId(request.fornecedorId())
                .orElseThrow(() -> new IllegalArgumentException("Fornecedor não encontrado: " + request.fornecedorId()));

        Veiculo veiculo = veiculoService.cadastrarVeiculo(
                request.chassi(),
                PlacaVeicular.of(request.placa()),
                request.marca(),
                request.modelo(),
                request.anoFabricacao(),
                request.anoModelo(),
                request.cor(),
                request.quilometragem(),
                request.estadoInicial(),
                Dinheiro.of(request.precoCompra()),
                Dinheiro.of(request.precoVendaSugerido()),
                fornecedor
        );

        VeiculoResponse response = new VeiculoResponse(
                veiculo.getId(),
                veiculo.getChassi(),
                veiculo.getPlaca().getValue(),
                veiculo.getMarca(),
                veiculo.getModelo(),
                veiculo.getAnoFabricacao(),
                veiculo.getAnoModelo(),
                veiculo.getCor(),
                veiculo.getQuilometragem(),
                veiculo.getEstado(),
                veiculo.getPrecoCompra().getValue(),
                veiculo.getPrecoVendaSugerido().getValue(),
                fornecedor.getId(),
                fornecedor.getNome().getValue(),
                veiculo.getDataEntradaEstoque(),
                veiculo.isAtivo()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<VeiculoResponse> buscarPorId(@PathVariable Long id) {
        return veiculoService.buscarPorId(id)
                .map(this::toResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<VeiculoResumoResponse>> listarDisponiveis() {
        List<VeiculoResumoResponse> resumos = veiculoService.listarDisponiveisParaVenda().stream()
                .map(this::toResumoResponse)
                .toList();

        return ResponseEntity.ok(resumos);
    }

    @PutMapping("/{id}/estado")
    @PreAuthorize("hasAnyRole('GERENTE', 'ADMIN')")
    public ResponseEntity<VeiculoResponse> alterarEstado(
            @PathVariable Long id,
            @RequestBody EstadoVeiculo novoEstado) {

        Veiculo atualizado = veiculoService.alterarEstado(id, novoEstado);
        return ResponseEntity.ok(toResponse(atualizado));
    }

    // Métodos auxiliares de mapeamento (evita duplicação)
    private VeiculoResponse toResponse(Veiculo v) {
        return new VeiculoResponse(
                v.getId(),
                v.getChassi(),
                v.getPlaca().getValue(),
                v.getMarca(),
                v.getModelo(),
                v.getAnoFabricacao(),
                v.getAnoModelo(),
                v.getCor(),
                v.getQuilometragem(),
                v.getEstado(),
                v.getPrecoCompra().getValue(),
                v.getPrecoVendaSugerido().getValue(),
                v.getFornecedor().getId(),
                v.getFornecedor().getNome().getValue(),
                v.getDataEntradaEstoque(),
                v.isAtivo()
        );
    }

    private VeiculoResumoResponse toResumoResponse(Veiculo v) {
        return new VeiculoResumoResponse(
                v.getId(),
                v.getPlaca().getValue(),
                v.getMarca(),
                v.getModelo(),
                v.getAnoModelo(),
                v.getEstado(),
                v.getPrecoVendaSugerido().getValue(),
                v.isAtivo()
        );
    }
}