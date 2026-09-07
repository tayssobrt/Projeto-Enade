package com.ads.enade.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "alternativa")
public class Alternativa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_alternativa")
    private Long id;

    @NotBlank
    @Column(name = "texto", length = 500)
    private String texto;

    @NotNull
    @Column(name = "is_correta")
    private Boolean isCorreta;

    @NotBlank
    @Column(name = "opcao_alternativa")
    private String opcaoAlternativa;

    @ManyToOne
    @JoinColumn(name = "id_questao")
    @JsonIgnore
    private Questao questao;

}
