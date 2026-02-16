package com.autodeal.concessionaria.iam.adapter.in;

import com.autodeal.concessionaria.iam.application.FuncionarioService;
import com.autodeal.concessionaria.iam.domain.Funcionario;
import com.autodeal.concessionaria.iam.dto.FuncionarioRequest;
import com.autodeal.concessionaria.iam.dto.FuncionarioResponse;
import com.autodeal.concessionaria.iam.dto.FuncionarioResumoResponse;
import com.autodeal.concessionaria.iam.dto.TrocaSenhaRequest;
import com.autodeal.concessionaria.shared.vo.Cpf;
import com.autodeal.concessionaria.shared.vo.Email;
import com.autodeal.concessionaria.shared.vo.NomeCompleto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/funcionarios")
public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FuncionarioResponse> criar(@Valid @RequestBody FuncionarioRequest request) {
        Funcionario criado = funcionarioService.criarFuncionario(
                NomeCompleto.of(request.nomeCompleto()),
                Cpf.of(request.cpf()),
                Email.of(request.email()),
                request.senha(),
                request.perfilAcesso()
        );

        FuncionarioResponse response = new FuncionarioResponse(
                criado.getId(),
                criado.getNomeCompleto().getValue(),
                criado.getCpf().getValue(),
                criado.getEmail().getValue(),
                criado.getPerfilAcesso(),
                criado.isAtivo(),
                criado.getDataCadastro()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public ResponseEntity<FuncionarioResponse> buscarPorId(@PathVariable Long id) {
        return funcionarioService.buscarPorId(id)
                .map(f -> new FuncionarioResponse(
                        f.getId(),
                        f.getNomeCompleto().getValue(),
                        f.getCpf().getValue(),
                        f.getEmail().getValue(),
                        f.getPerfilAcesso(),
                        f.isAtivo(),
                        f.getDataCadastro()
                ))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public ResponseEntity<List<FuncionarioResumoResponse>> listarTodos() {
        List<FuncionarioResumoResponse> resumos = funcionarioService.listarTodos().stream()
                .map(f -> new FuncionarioResumoResponse(
                        f.getId(),
                        f.getNomeCompleto().getValue(),
                        f.getEmail().getValue(),
                        f.getPerfilAcesso(),
                        f.isAtivo()
                ))
                .toList();

        return ResponseEntity.ok(resumos);
    }

    @PutMapping("/{id}/senha")
    @PreAuthorize("#id == authentication.principal.id or hasRole('ADMIN')")
    public ResponseEntity<Void> trocarSenha(
            @PathVariable Long id,
            @Valid @RequestBody TrocaSenhaRequest request) {

        funcionarioService.trocarSenha(id, request.senhaAtual(), request.novaSenha());
        return ResponseEntity.noContent().build();
    }

    // DELETE soft (desativar) pode ser adicionado depois
}