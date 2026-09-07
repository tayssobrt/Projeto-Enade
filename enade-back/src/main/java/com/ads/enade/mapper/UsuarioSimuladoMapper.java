package com.ads.enade.mapper;

import com.ads.enade.dto.user.UsuarioSimuladoDTOResponse;
import com.ads.enade.entity.UsuarioSimulado;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {SimuladoMapper.class})
public interface UsuarioSimuladoMapper {

    UsuarioSimuladoDTOResponse toDTO(UsuarioSimulado usuarioSimulado);
}
