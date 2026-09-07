package com.ads.enade.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "usuario_questao")
public class UsuarioQuestao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_usuario_simulado")
    private UsuarioSimulado usuarioSimulado;

    @ManyToOne
    @JoinColumn(name = "id_questao")
    private Questao questao;

    @Column(name = "is_acerto")
    private Boolean isAcerto;

    @Column(name = "data_resposta")
    private LocalDate dataResposta;

    @ManyToOne
    @JoinColumn(name = "id_alternativa")
    private Alternativa alternativa;
}
