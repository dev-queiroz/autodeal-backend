package com.autodeal.concessionaria.estoque.domain;

import com.autodeal.concessionaria.shared.vo.Cnpj;
import com.autodeal.concessionaria.shared.vo.Email;
import com.autodeal.concessionaria.shared.vo.NomeCompleto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "fornecedores")
@Getter
@NoArgsConstructor
public class Fornecedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "nome"))
    })
    private NomeCompleto nome;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "cnpj"))
    })
    private Cnpj cnpj;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "email"))
    })
    private Email email;

    @Column(length = 20)
    private String telefone;

    @Column(length = 100)
    private String contatoResponsavel;

    private boolean ativo = true;

    public Fornecedor(NomeCompleto nome, Cnpj cnpj, Email email, String telefone, String contatoResponsavel) {
        this.nome = nome;
        this.cnpj = cnpj;
        this.email = email;
        this.telefone = telefone;
        this.contatoResponsavel = contatoResponsavel;
    }

    public void desativar() {
        this.ativo = false;
    }
}