package com.ads.enade.dto.questao;

import com.ads.enade.dto.alternativa.AlternativaDTOResponse;

import java.util.List;

public record QuestaoDTOResponse(
        Long id,
        String titulo,
        String enunciado,
        String tipoQuestao,
        String areaQuestao,
        Boolean possuiImagem,
        String imgURL,
        String explicacao,
        List<AlternativaDTOResponse> alternativas
) {
}
