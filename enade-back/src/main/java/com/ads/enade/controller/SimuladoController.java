package com.ads.enade.controller;

import com.ads.enade.dto.user.UsuarioSimuladoDTOResponse;
import com.ads.enade.exception.handler.ErrorResponse;
import com.ads.enade.service.SimuladoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Simulado", description = "Endpoints para geração de simulados personalizados para o usuário autenticado")
@SecurityRequirement(name = "Bearer Authentication")
@RestController
@RequestMapping("/api/simulado")
@RequiredArgsConstructor
public class SimuladoController {

    private final SimuladoService simuladoService;

    @Operation(
            summary = "Gerar um novo simulado",
            description = "Gera um novo simulado para o usuário autenticado, com base no curso associado a ele "
                    + "e na quantidade de questões informada. As questões são selecionadas automaticamente. "
                    + "O simulado é criado com o status inicial de 'não respondido' — os campos de nota e "
                    + "quantidade de acertos são preenchidos posteriormente, quando o usuário submeter as respostas. "
                    + "A quantidade de questões deve estar entre **1 e 30**."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Simulado gerado com sucesso.",
                    content = @Content(schema = @Schema(implementation = UsuarioSimuladoDTOResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Quantidade de questões inválida (menor ou igual a 0, ou maior que 30). "
                            + "Se o parâmetro `quantidadeDeQuestoes` não for enviado, o erro retornado hoje "
                            + "segue o formato padrão do Spring, não o `ErrorResponse` customizado desta API.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Token JWT ausente, inválido ou expirado.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PostMapping
    public ResponseEntity<UsuarioSimuladoDTOResponse> buscarSimulado(
            @Parameter(
                    description = "Quantidade de questões desejadas no simulado. Deve ser um valor entre 1 e 30.",
                    required = true,
                    example = "10"
            )
            @RequestParam int quantidadeDeQuestoes){

        UsuarioSimuladoDTOResponse response = simuladoService.gerarSimulado(quantidadeDeQuestoes);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}