package com.ads.enade.dto.simulado;

import jakarta.validation.constraints.NotNull;

public record RespostaUsuarioSimulado(
        @NotNull
        Long idUsuarioSimulado,
        @NotNull
        Long idQuestao,
        @NotNull
        Long idAlternativa
) {
}
