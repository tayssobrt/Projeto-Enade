package com.ads.enade.dto.user;

import com.ads.enade.dto.simulado.SimuladoDTOResponse;

import java.time.LocalDate;

public record UsuarioSimuladoDTOResponse(
        Long id,
        Double nota,
        Integer quantidadeAcertos,
        Integer quantidadeDeQuestoes,
        Integer quantidadeDeRespostas,
        LocalDate dataConclusao,
        SimuladoDTOResponse simulado
) {
}
