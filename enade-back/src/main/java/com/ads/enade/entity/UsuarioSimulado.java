package com.ads.enade.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "usuario_simulado")
public class UsuarioSimulado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_simulado")
    private Simulado simulado;

    @Column(name = "nota")
    private Double nota;

    @Builder.Default
    private Boolean finalizado = false;

    @Builder.Default
    @Column(name = "quantidade_acertos")
    private Integer quantidadeAcertos = 0;

    @Column(name = "quantidade_de_questoes")
    private Integer quantidadeDeQuestoes;

    @Builder.Default
    @Column(name = "quantidade_de_respostas")
    private Integer quantidadeDeRespostas = 0;

    @Column(name = "data_conclusao")
    private LocalDate dataConclusao;

    public void adicionarQuantidadeDeAcertos(){
        this.quantidadeAcertos++;
    }

    public void adicionarQuantidadeDeRespostas(){
        this.quantidadeDeRespostas++;
    }

    public void adicionarNota(Double nota){
        this.nota = nota;
    }
}
