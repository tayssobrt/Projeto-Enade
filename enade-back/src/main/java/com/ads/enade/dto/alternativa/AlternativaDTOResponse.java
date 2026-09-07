package com.ads.enade.dto.alternativa;

public record AlternativaDTOResponse(
        Long id,
        String texto,
        Boolean isCorreta,
        String opcaoAlternativa
) {
}