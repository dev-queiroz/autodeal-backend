package com.autodeal.concessionaria.crm.adapter.in;

import com.autodeal.concessionaria.crm.application.ClienteService;
import com.autodeal.concessionaria.crm.domain.Cliente;
import com.autodeal.concessionaria.crm.dto.ClienteRequest;
import com.autodeal.concessionaria.crm.dto.ClienteResponse;
import com.autodeal.concessionaria.crm.dto.ClienteResumoResponse;
import com.autodeal.concessionaria.shared.vo.Cpf;
import com.autodeal.concessionaria.shared.vo.Email;
import com.autodeal.concessionaria.shared.vo.NomeCompleto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/crm/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('VENDEDOR', 'GERENTE', 'ADMIN')")
    public ResponseEntity<ClienteResponse> cadastrar(@Valid @RequestBody ClienteRequest request) {
        Cliente cliente = clienteService.cadastrarCliente(
                NomeCompleto.of(request.nomeCompleto()),
                Cpf.of(request.cpf()),
                Email.of(request.email()),
                request.telefone()
        );

        ClienteResponse response = new ClienteResponse(
                cliente.getId(),
                cliente.getNomeCompleto().getValue(),
                cliente.getCpf().getValue(),
                cliente.getEmail().getValue(),
                cliente.getTelefone(),
                cliente.isAtivo(),
                cliente.getDataCadastro()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ClienteResponse> buscarPorId(@PathVariable Long id) {
        return clienteService.buscarPorId(id)
                .map(c -> new ClienteResponse(
                        c.getId(),
                        c.getNomeCompleto().getValue(),
                        c.getCpf().getValue(),
                        c.getEmail().getValue(),
                        c.getTelefone(),
                        c.isAtivo(),
                        c.getDataCadastro()
                ))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<ClienteResumoResponse>> listarTodos() {
        List<ClienteResumoResponse> resumos = clienteService.listarTodos().stream()
                .map(c -> new ClienteResumoResponse(
                        c.getId(),
                        c.getNomeCompleto().getValue(),
                        c.getCpf().getValue(),
                        c.getEmail().getValue()
                ))
                .toList();

        return ResponseEntity.ok(resumos);
    }
}