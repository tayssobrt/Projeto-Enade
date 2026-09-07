package com.ads.enade.dto.alternativa;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AlternativaDTORequest (
        @NotBlank(message = "Texto é obrigatório para a alternativa")
        String texto,
        @NotNull(message = "É necessário marcar se é a opção correta ou não")
        Boolean isCorreta,
        @NotBlank(message = "A letra da alternativa é obrigatório")
        String opcaoAlternativa
){
}
