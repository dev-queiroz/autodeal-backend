package com.autodeal.concessionaria.iam.domain;

import com.autodeal.concessionaria.shared.vo.Cpf;
import com.autodeal.concessionaria.shared.vo.Email;
import com.autodeal.concessionaria.shared.vo.NomeCompleto;
import com.autodeal.concessionaria.iam.domain.enums.PerfilAcesso;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "funcionarios")
@Getter
@NoArgsConstructor
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "nome_completo"))
    })
    private NomeCompleto nomeCompleto;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "cpf"))
    })
    private Cpf cpf;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "email"))
    })
    private Email email;

    @Column(nullable = false)
    private String senhaHash;  // Armazenar apenas hash (BCrypt ou Argon2)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PerfilAcesso perfilAcesso;

    @Column(nullable = false)
    private boolean ativo = true;

    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro = LocalDateTime.now();

    public Funcionario(
            NomeCompleto nomeCompleto,
            Cpf cpf,
            Email email,
            String senhaHash,
            PerfilAcesso perfilAcesso
    ) {
        this.nomeCompleto = nomeCompleto;
        this.cpf = cpf;
        this.email = email;
        this.senhaHash = senhaHash;
        this.perfilAcesso = perfilAcesso;
    }

    // Método para desativar funcionário (soft delete)
    public void desativar() {
        this.ativo = false;
    }

    // Método para atualizar senha (após validação)
    public void atualizarSenha(String novoSenhaHash) {
        this.senhaHash = novoSenhaHash;
    }

    // Outros métodos de negócio podem vir aqui depois (ex: alterarPerfil)
}