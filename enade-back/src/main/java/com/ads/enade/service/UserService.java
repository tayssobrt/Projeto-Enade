package com.ads.enade.service;

import com.ads.enade.dto.user.UserProfileDTO;
import com.ads.enade.entity.Usuario;
import com.ads.enade.exception.UserNotFoundException;
import com.ads.enade.mapper.UsuarioMapper;
import com.ads.enade.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AuthService authService;
    private final UsuarioMapper usuarioMapper;


    // Método para buscar perfil do usuário pelo ID
    @Transactional
    public UserProfileDTO getUserProfile(Long userId) {
        Usuario usuario = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        return usuarioMapper.toDTO(usuario);
    }

    public UserProfileDTO me(){

        Usuario usuario = authService.me();

        return usuarioMapper.toDTO(usuario);
    }
}
