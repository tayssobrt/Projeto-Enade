package com.ads.enade.entity;

import com.ads.enade.enums.TipoSimulado;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "simulado")
public class Simulado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "titulo")
    private String titulo;

    @Column(name = "data_criacao")
    private LocalDate dataCriacao;

    @Column(name = "quantidade_de_questoes")
    private Integer quantidadeDeQuestoes;

    @ManyToOne
    @JoinColumn(name = "id_curso")
    private Curso curso;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_simulado")
    private TipoSimulado tipoSimulado;

    @ManyToMany
    @JoinTable(name = "simulado_questao",
    joinColumns = @JoinColumn(name = "id_simulado"),
    inverseJoinColumns = @JoinColumn(name = "id_questao"))
    private List<Questao> questoes = new ArrayList<>();
}
