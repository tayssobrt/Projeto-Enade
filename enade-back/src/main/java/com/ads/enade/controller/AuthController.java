package com.ads.enade.controller;

import com.ads.enade.dto.auth.LoginDTO;
import com.ads.enade.dto.auth.RegisterDTO;
import com.ads.enade.exception.handler.ErrorResponse;
import com.ads.enade.service.AuthService;
import com.ads.enade.utils.JwtResponse;
import com.ads.enade.utils.MessageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Autenticação", description = "Endpoints públicos para login e registro de usuários")
@SecurityRequirement(name = "") // Rotas públicas: sobrescreve a exigência global de autenticação
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Operation(
            summary = "Autenticar usuário",
            description = "Realiza login com username e senha, retornando um token JWT válido junto com "
                    + "os dados básicos do usuário (id, username, e-mail, curso e roles). "
                    + "Este token deve ser enviado no header `Authorization: Bearer {token}` "
                    + "em todas as requisições subsequentes que exigirem autenticação."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Autenticação realizada com sucesso.",
                    content = @Content(schema = @Schema(implementation = JwtResponse.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Username ou senha inválidos.", // ⚠️ ver Alerta 2 acima — status real pode ser 500 hoje
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginDTO loginRequest) {
        JwtResponse response = authService.authenticateUser(loginRequest);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Registrar novo usuário",
            description = "Cria uma nova conta de usuário vinculada a um curso existente. "
                    + "O usuário criado recebe automaticamente a role padrão USER. "
                    + "Após o registro, é necessário chamar o endpoint de login para obter o token JWT."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuário registrado com sucesso.",
                    content = @Content(schema = @Schema(implementation = MessageResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Username já está em uso, e-mail já está em uso, ou curso informado (`courseId`) não existe.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PostMapping("/register")
    public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody RegisterDTO signUpRequest) {
        MessageResponse response = authService.registerUser(signUpRequest);
        if (response.getMessage().startsWith("Error")) {
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }
}