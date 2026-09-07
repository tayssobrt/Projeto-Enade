package com.ads.enade.dto.questao;

import com.ads.enade.dto.alternativa.AlternativaDTORequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record QuestaoDTORequest (
        @NotBlank(message = "Titulo é obrigatório para a questão")
        String titulo,
        @NotBlank(message = "Enunciado é obrigatório para a questão")
        String enunciado,
        @NotBlank(message = "Tipo da questão é obrigatório")
        String tipoQuestao,
        @NotBlank(message = "Área da questão é obrigatório")
        String areaQuestao,
        @NotNull(message = "Indique se a questão possui imagem")
        Boolean possuiImagem,
        String imgURL,
        @NotBlank(message = "Explicação é obrigatório para a questão")
        String explicacao,
        List<AlternativaDTORequest> alternativas
){
}
