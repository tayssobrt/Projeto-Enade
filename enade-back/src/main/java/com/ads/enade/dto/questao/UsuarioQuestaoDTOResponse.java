package com.ads.enade.dto.questao;

import com.ads.enade.dto.alternativa.AlternativaDTOResponse;

import java.time.LocalDate;

public record UsuarioQuestaoDTOResponse(
        Long id,
        Boolean isAcerto,
        LocalDate dataResposta,
        AlternativaDTOResponse alternativa,
        QuestaoDTOResponse questao
) {
}
