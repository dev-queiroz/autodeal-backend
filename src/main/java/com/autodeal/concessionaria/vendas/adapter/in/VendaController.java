package com.autodeal.concessionaria.vendas.adapter.in;

import com.autodeal.concessionaria.vendas.application.VendaService;
import com.autodeal.concessionaria.vendas.domain.Venda;
import com.autodeal.concessionaria.vendas.dto.VendaRequest;
import com.autodeal.concessionaria.vendas.dto.VendaResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vendas")
public class VendaController {

    private final VendaService vendaService;

    public VendaController(VendaService vendaService) {
        this.vendaService = vendaService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('VENDEDOR', 'GERENTE', 'ADMIN')")
    public ResponseEntity<VendaResponse> registrar(@Valid @RequestBody VendaRequest request) {
        Venda venda = vendaService.registrarVenda(
                request.veiculoId(),
                request.clienteId(),
                request.vendedorId(),
                request.descontoPercentual()
        );

        VendaResponse response = new VendaResponse(
                venda.getId(),
                venda.getVeiculo().getId(),
                venda.getVeiculo().getPlaca().getValue(),
                venda.getCliente().getId(),
                venda.getCliente().getNomeCompleto().getValue(),
                venda.getVendedor().getId(),
                venda.getVendedor().getNomeCompleto().getValue(),
                venda.getValorVenda().getValue(),
                venda.getComissaoVendedor().getValue(),
                venda.getStatus().name(),
                venda.getDataVenda()
        );

        return ResponseEntity.ok(response);
    }

    // GET por ID, listagem, cancelamento etc. podem ser adicionados depois
}