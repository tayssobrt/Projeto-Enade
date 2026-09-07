package com.ads.enade.controller;

import com.ads.enade.dto.questao.QuestaoDTORequest;
import com.ads.enade.dto.questao.QuestaoDTOResponse;
import com.ads.enade.exception.handler.ErrorResponse;
import com.ads.enade.service.QuestaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Questões", description = "Endpoints para cadastro e consulta de questões (área administrativa)")
@SecurityRequirement(name = "Bearer Authentication")
@RestController
@RequestMapping("/api/questao")
@RequiredArgsConstructor
public class QuestaoController {

    private final QuestaoService questaoService;

    @Operation(
            summary = "Cadastrar uma nova questão",
            description = "Cria uma nova questão com suas alternativas. Endpoint restrito a administradores. "
                    + "A questão deve conter **exatamente 5 alternativas**, e as alternativas não podem se repetir "
                    + "entre si (mesma letra/opção não pode aparecer em posições consecutivas). "
                    + "Se `possuiImagem` for `true`, o campo `imgURL` torna-se obrigatório."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Questão cadastrada com sucesso.",
                    content = @Content(schema = @Schema(implementation = QuestaoDTOResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos: campo obrigatório ausente, quantidade de alternativas diferente de 5, "
                            + "`imgURL` ausente quando `possuiImagem = true`, ou alternativas repetidas.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Token JWT ausente, inválido ou expirado.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Usuário autenticado não possui a role ADMIN.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<QuestaoDTOResponse> registerQuestion(@Valid @RequestBody QuestaoDTORequest request){
        QuestaoDTOResponse response = questaoService.registerQuestion(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Buscar questão por ID",
            description = "Retorna os dados completos de uma questão específica, incluindo suas alternativas. "
                    + "Endpoint restrito a administradores."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Questão encontrada com sucesso.",
                    content = @Content(schema = @Schema(implementation = QuestaoDTOResponse.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Token JWT ausente, inválido ou expirado.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Usuário autenticado não possui a role ADMIN.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Nenhuma questão encontrada com o ID informado.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<QuestaoDTOResponse> findById(
            @Parameter(description = "ID da questão a ser buscada.", required = true, example = "1")
            @PathVariable Long id){
        QuestaoDTOResponse response = questaoService.findById(id);
        return ResponseEntity.ok(response);
    }
}