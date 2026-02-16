package com.autodeal.concessionaria.estoque.adapter.in;

import com.autodeal.concessionaria.estoque.application.FornecedorService;
import com.autodeal.concessionaria.estoque.domain.Fornecedor;
import com.autodeal.concessionaria.estoque.dto.FornecedorDto;
import com.autodeal.concessionaria.shared.vo.Cnpj;
import com.autodeal.concessionaria.shared.vo.Email;
import com.autodeal.concessionaria.shared.vo.NomeCompleto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estoque/fornecedores")
public class FornecedorController {

    private final FornecedorService fornecedorService;

    public FornecedorController(FornecedorService fornecedorService) {
        this.fornecedorService = fornecedorService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('GERENTE', 'ADMIN')")
    public ResponseEntity<FornecedorDto> criar(@Valid @RequestBody FornecedorDto dto) {
        Fornecedor fornecedor = fornecedorService.criarFornecedor(
                NomeCompleto.of(dto.nome()),
                Cnpj.of(dto.cnpj()),
                Email.of(dto.email()),
                dto.telefone(),
                dto.contatoResponsavel()
        );

        FornecedorDto response = new FornecedorDto(
                fornecedor.getId(),
                fornecedor.getNome().getValue(),
                fornecedor.getCnpj().getValue(),
                fornecedor.getEmail().getValue(),
                fornecedor.getTelefone(),
                fornecedor.getContatoResponsavel()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<FornecedorDto> buscarPorId(@PathVariable Long id) {
        return fornecedorService.buscarPorId(id)
                .map(f -> new FornecedorDto(
                        f.getId(),
                        f.getNome().getValue(),
                        f.getCnpj().getValue(),
                        f.getEmail().getValue(),
                        f.getTelefone(),
                        f.getContatoResponsavel()
                ))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<FornecedorDto>> listarTodos() {
        List<FornecedorDto> dtos = fornecedorService.buscarTodos().stream()
                .map(f -> new FornecedorDto(
                        f.getId(),
                        f.getNome().getValue(),
                        f.getCnpj().getValue(),
                        f.getEmail().getValue(),
                        f.getTelefone(),
                        f.getContatoResponsavel()
                ))
                .toList();

        return ResponseEntity.ok(dtos);
    }
}