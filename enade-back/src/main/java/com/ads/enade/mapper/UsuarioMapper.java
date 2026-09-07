package com.ads.enade.mapper;

import com.ads.enade.dto.user.UserProfileDTO;
import com.ads.enade.entity.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = CursoMapper.class)
public interface UsuarioMapper {

    UserProfileDTO toDTO(Usuario usuario);
}
