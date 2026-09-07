package com.ads.enade.mapper;

import com.ads.enade.dto.simulado.SimuladoDTOResponse;
import com.ads.enade.entity.Simulado;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CursoMapper.class, QuestaoMapper.class})
public interface SimuladoMapper {

    SimuladoDTOResponse toDTO(Simulado simulado);
}
