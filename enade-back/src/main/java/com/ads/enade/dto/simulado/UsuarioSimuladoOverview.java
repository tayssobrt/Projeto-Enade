package com.ads.enade.dto.simulado;

import java.time.LocalDate;

public record UsuarioSimuladoOverview(
        Long id,
        Integer quantidadeDeAcertos,
        Integer quantidadeDeRespostas,
        Integer quantidadeDeQuestoes,
        Double nota,
        LocalDate dataConclusao
) {
}
