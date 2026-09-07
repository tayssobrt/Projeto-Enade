package com.ads.enade.entity;

import com.ads.enade.enums.TipoQuestao;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "questao")
public class Questao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_questao")
    private Long id;

    @NotBlank
    @Column(name = "titulo", length = 150)
    private String titulo;

    @NotBlank
    @Column(name = "enunciado", length = 1000)
    private String enunciado;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_questao")
    private TipoQuestao tipoQuestao;

    @NotBlank
    @Column(name = "area_questao", length = 100)
    private String areaQuestao;

    @NotNull
    @Column(name = "possui_imagem")
    private Boolean possuiImagem;

    @NotBlank
    @Column(name = "img_url")
    private String imgURL;

    @NotBlank
    @Column(name = "explicacao")
    private String explicacao;

    @NotEmpty
    @OneToMany(mappedBy = "questao", cascade = CascadeType.ALL)
    private List<Alternativa> alternativas;

}
