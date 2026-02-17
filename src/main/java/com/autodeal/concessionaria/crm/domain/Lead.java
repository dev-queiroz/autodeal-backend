package com.autodeal.concessionaria.crm.domain;

import com.autodeal.concessionaria.shared.vo.Email;
import com.autodeal.concessionaria.shared.vo.NomeCompleto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "leads")
@Getter
@NoArgsConstructor
public class Lead {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private NomeCompleto nome;

    @Embedded
    private Email email;

    private String telefone;
    private String origem; // site, indicação, evento, etc
    private String status = "NOVO"; // NOVO, CONTACTADO, QUALIFICADO, CONVERTIDO, PERDIDO

    private LocalDateTime dataCriacao = LocalDateTime.now();

    public Lead(NomeCompleto nome, Email email, String telefone, String origem) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.origem = origem;
    }

    public void atualizarStatus(String novoStatus) {
        this.status = novoStatus;
    }
}