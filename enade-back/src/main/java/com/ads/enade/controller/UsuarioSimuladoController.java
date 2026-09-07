package com.ads.enade.controller;

import com.ads.enade.dto.questao.UsuarioQuestaoDTOResponse;
import com.ads.enade.dto.simulado.RespostaUsuarioSimulado;
import com.ads.enade.dto.simulado.UsuarioSimuladoOverview;
import com.ads.enade.dto.user.UsuarioSimuladoDTOResponse;
import com.ads.enade.exception.handler.ErrorResponse;
import com.ads.enade.service.UsuarioSimuladoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Simulados do Usuário", description = "Endpoints para consulta, resposta e finalização dos simulados do usuário autenticado")
@SecurityRequirement(name = "Bearer Authentication")
@RestController
@RequestMapping("/api/usuario/simulados")
@RequiredArgsConstructor
public class UsuarioSimuladoController {

    private final UsuarioSimuladoService usuarioSimuladoService;

    @Operation(
            summary = "Listar simulados do usuário autenticado",
            description = "Retorna todos os simulados (finalizados ou em andamento) já gerados para o usuário autenticado. "
                    + "Se o usuário não possuir nenhum simulado, retorna uma lista vazia."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de simulados retornada com sucesso (pode ser vazia).",
                    content = @Content(schema = @Schema(implementation = UsuarioSimuladoDTOResponse.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Token JWT ausente, inválido ou expirado.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @GetMapping
    public ResponseEntity<List<UsuarioSimuladoDTOResponse>> buscarSimuladosDoUsuario(){
        List<UsuarioSimuladoDTOResponse> responses = usuarioSimuladoService.buscarSimuladosUsuario();
        return ResponseEntity.ok(responses);
    }

    @Operation(
            summary = "Registrar resposta de uma questão do simulado",
            description = "Registra a resposta do usuário autenticado para uma questão específica dentro de um simulado. "
                    + "Use este endpoint para todas as questões, **exceto a última** do simulado — para a última questão, "
                    + "utilize o endpoint `/resposta/finalizar`. "
                    + "Não é permitido responder a mesma questão mais de uma vez no mesmo simulado, "
                    + "nem responder por um simulado que não pertença ao usuário autenticado."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Resposta registrada com sucesso.",
                    content = @Content(schema = @Schema(implementation = UsuarioQuestaoDTOResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Questão já respondida anteriormente neste simulado, ou esta é a última questão "
                            + "do simulado (nesse caso, utilize o endpoint `/resposta/finalizar`).",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Token JWT ausente, inválido ou expirado.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Simulado do usuário, questão ou alternativa informados não foram encontrados.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PostMapping("/resposta")
    public ResponseEntity<UsuarioQuestaoDTOResponse> registrarRespostaDoUsuario(@RequestBody @Valid RespostaUsuarioSimulado request){
        UsuarioQuestaoDTOResponse response = usuarioSimuladoService.registrarRespostaDoUsuarioNoSimulado(request);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Responder a última questão e finalizar o simulado",
            description = "Registra a resposta da **última questão pendente** do simulado e o finaliza, calculando "
                    + "a nota final do usuário. Utilize este endpoint somente quando todas as demais questões já "
                    + "tiverem sido respondidas via `/resposta` — caso ainda existam questões pendentes, a finalização "
                    + "é recusada."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Simulado finalizado com sucesso. Retorna o resumo com nota e desempenho.",
                    content = @Content(schema = @Schema(implementation = UsuarioSimuladoOverview.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Questão já respondida anteriormente, ou ainda existem questões do simulado "
                            + "não respondidas (finalização não permitida antes de responder tudo).",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Token JWT ausente, inválido ou expirado.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Simulado do usuário, questão ou alternativa informados não foram encontrados.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PostMapping("/resposta/finalizar")
    public ResponseEntity<UsuarioSimuladoOverview> finalizarSimuladoUsuario(@RequestBody @Valid RespostaUsuarioSimulado request){
        UsuarioSimuladoOverview response = usuarioSimuladoService.finalizarSimuladoUsuario(request);
        return ResponseEntity.ok(response);
    }
}