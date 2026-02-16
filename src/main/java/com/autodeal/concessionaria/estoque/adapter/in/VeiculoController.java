package com.autodeal.concessionaria.estoque.adapter.in;

import com.autodeal.concessionaria.estoque.application.VeiculoService;
import com.autodeal.concessionaria.estoque.domain.Fornecedor;
import com.autodeal.concessionaria.estoque.domain.Veiculo;
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
                new Fornecedor() // Placeholder — em produção viria de um FornecedorService.buscarPorId(request.fornecedorId())
        );

        // Converter para Response (pode usar mapper depois)
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
                1L, // fornecedorId placeholder
                "Fornecedor Exemplo", // placeholder
                veiculo.getDataEntradaEstoque(),
                veiculo.isAtivo()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<VeiculoResponse> buscarPorId(@PathVariable Long id) {
        return veiculoService.buscarPorId(id)
                .map(v -> /* converter para VeiculoResponse */ ResponseEntity.ok(new VeiculoResponse(/* ... */)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<VeiculoResumoResponse>> listarDisponiveis() {
        return ResponseEntity.ok(
                veiculoService.listarDisponiveisParaVenda().stream()
                        .map(v -> new VeiculoResumoResponse(
                                v.getId(),
                                v.getPlaca().getValue(),
                                v.getMarca(),
                                v.getModelo(),
                                v.getAnoModelo(),
                                v.getEstado(),
                                v.getPrecoVendaSugerido().getValue(),
                                v.isAtivo()
                        ))
                        .toList()
        );
    }

    // Outros endpoints (PUT para alterar estado, DELETE soft, etc.) podem vir depois
}