package com.autodeal.concessionaria.estoque.adapter.in;

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

    public VeiculoController(VeiculoService veiculoService) {
        this.veiculoService = veiculoService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('GERENTE', 'ADMIN')")
    public ResponseEntity<VeiculoResponse> cadastrar(@Valid @RequestBody VeiculoRequest request) {
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
                new Fornecedor() // Placeholder — substitua por FornecedorService.buscarPorId(request.fornecedorId())
        );

        return ResponseEntity.ok(toResponse(veiculo));
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
                1L, // Placeholder: fornecedorId
                "Fornecedor Exemplo", // Placeholder: fornecedorNome
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