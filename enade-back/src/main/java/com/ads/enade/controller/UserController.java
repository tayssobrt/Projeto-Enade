package com.ads.enade.controller;

import com.ads.enade.dto.user.UserProfileDTO;
import com.ads.enade.exception.handler.ErrorResponse;
import com.ads.enade.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Usuários", description = "Endpoints relacionados ao perfil do usuário autenticado ou de terceiros")
@SecurityRequirement(name = "Bearer Authentication")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/users")
@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(
            summary = "Buscar perfil de um usuário pelo ID",
            description = "Retorna os dados de perfil (nome de usuário, e-mail e curso) de um usuário específico, "
                    + "identificado pelo parâmetro `userid`. Use este endpoint quando precisar consultar o perfil "
                    + "de qualquer usuário do sistema (não necessariamente o usuário logado)."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Perfil do usuário encontrado com sucesso.",
                    content = @Content(schema = @Schema(implementation = UserProfileDTO.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Token JWT ausente, inválido ou expirado.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Usuário autenticado não possui nenhuma das roles exigidas (USER, MODERATOR ou ADMIN).",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Nenhum usuário encontrado com o ID informado.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @GetMapping("/profile")
    public ResponseEntity<UserProfileDTO> getUserProfile(
            @Parameter(
                    description = "ID do usuário cujo perfil será consultado.",
                    required = true,
                    example = "1"
            )
            @RequestParam(name = "userid") Long userId) {
        UserProfileDTO user = userService.getUserProfile(userId);
        return ResponseEntity.ok(user);
    }

    @Operation(
            summary = "Buscar perfil do usuário autenticado",
            description = "Retorna os dados de perfil (nome de usuário, e-mail e curso) do usuário atualmente "
                    + "autenticado, identificado automaticamente a partir do token JWT enviado. "
                    + "Use este endpoint para exibir os dados do usuário logado (ex: tela de perfil próprio), "
                    + "sem precisar informar nenhum ID manualmente."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Perfil do usuário autenticado retornado com sucesso.",
                    content = @Content(schema = @Schema(implementation = UserProfileDTO.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Token JWT ausente, inválido ou expirado.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Usuário autenticado não possui nenhuma das roles exigidas (USER, MODERATOR ou ADMIN).",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @GetMapping("/me")
    public ResponseEntity<UserProfileDTO> me() {
        UserProfileDTO user = userService.me();
        return ResponseEntity.ok(user);
    }
}