package com.ads.enade.service;

import com.ads.enade.dto.auth.EmailDTO;
import com.ads.enade.dto.auth.LoginDTO;
import com.ads.enade.dto.auth.RegisterDTO;
import com.ads.enade.dto.auth.ResetPasswordDTO;
import com.ads.enade.entity.Curso;
import com.ads.enade.entity.PasswordResetToken;
import com.ads.enade.entity.Role;
import com.ads.enade.entity.Usuario;
import com.ads.enade.enums.ERole;
import com.ads.enade.exception.*;
import com.ads.enade.repository.CourseRepository;
import com.ads.enade.repository.PasswordResetTokenRepository;
import com.ads.enade.repository.RoleRepository;
import com.ads.enade.repository.UserRepository;
import com.ads.enade.security.UserDetailsImpl;
import com.ads.enade.security.jwt.JwtUtils;
import com.ads.enade.utils.JwtResponse;
import com.ads.enade.utils.MessageResponse;
import com.ads.enade.utils.impl.EmailServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final EmailServiceImpl emailService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final CourseRepository courseRepository;

    public JwtResponse authenticateUser(LoginDTO loginRequest) {

        log.info("Inicinado processo de autenticação do usuário [{}]", loginRequest.getUsername());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        Long courseId = userDetails.getCourseId();

        log.info("Usuário autenticado com sucesso...");

        return new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                courseId,
                userDetails.getEmail(),
                roles);
    }

    public MessageResponse registerUser(RegisterDTO signUpRequest) {

        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            log.error("Erro: username já existente");
            throw new UsernameAlreadyTakenException("Error: Username is already taken!");
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            log.error("Erro: email já existente");
            throw new EmailAlreadyInUseException("Error: Email is already in use!");
        }

        log.info("Iniciando processo de registro de um novo usuário...");

        Curso course = courseRepository.findById(signUpRequest.getCourseId())
                .orElseThrow(() -> new CourseNotFoundException("Error: Course is not found."));

        Usuario usuario = new Usuario(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                passwordEncoder.encode(signUpRequest.getPassword()));

        Role roleUser = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RoleNotFoundException("Error: Role is not found"));

        usuario.setRoles(Set.of(roleUser));
        usuario.setCourse(course);
        userRepository.save(usuario);

        log.info("Usuário registrado com sucesso | [{}]", usuario.getUsername());

        return new MessageResponse("User registered successfully!");
    }

    public void sendResetPasswordEmail(EmailDTO emailDTO) {

        //TODO: O EMAIL TEM QUE SER VERIFICADO NA AMAZON, SE FOR PARA PRODUÇÃO, NÃO PRECISA VERIFICAR.

        Usuario usuario = userRepository.findByEmail(emailDTO.getEmail())
                .orElseThrow(() -> new EmailNotFoundException("Email not found."));

        String token = UUID.randomUUID().toString();
        PasswordResetToken resetToken = new PasswordResetToken(token, usuario);
        passwordResetTokenRepository.save(resetToken);

        String resetLink = "98.85.62.40/src/pages/novaSenha.html?token=" + token; //TODO: LEMBRAR DE COLOCAR O SITE
        emailService.sendResetPasswordEmail(usuario.getEmail(), resetLink);
    }

    public void resetPassword(ResetPasswordDTO resetPasswordDTO) {
        PasswordResetToken tokenOptional = passwordResetTokenRepository.findByToken(resetPasswordDTO.getToken())
                .orElseThrow(() -> new InvalidTokenException("Invalid or expired token."));

        if (tokenOptional.getExpiryDate().before(new Date())) {
            throw new InvalidTokenException("Expired token.");
        }

        Usuario usuario = tokenOptional.getUsuario();
        usuario.setPassword(passwordEncoder.encode(resetPasswordDTO.getNewPassword()));
        userRepository.save(usuario);

        passwordResetTokenRepository.delete(tokenOptional);
    }

    @Transactional
    public Usuario me(){
        UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new EmailNotFoundException("Email invállido"));
    }
}
