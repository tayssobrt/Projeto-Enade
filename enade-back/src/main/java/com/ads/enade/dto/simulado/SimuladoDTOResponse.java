package com.ads.enade.dto.simulado;

import com.ads.enade.dto.course.CourseDtoResponse;
import com.ads.enade.dto.questao.QuestaoDTOResponse;

import java.time.LocalDate;
import java.util.List;

public record SimuladoDTOResponse(
        Long id,
        String titulo,
        LocalDate dataCriacao,
        Integer quantidadeDeQuestoes,
        CourseDtoResponse curso,
        String tipoSimulado,
        List<QuestaoDTOResponse> questoes
) {
}
