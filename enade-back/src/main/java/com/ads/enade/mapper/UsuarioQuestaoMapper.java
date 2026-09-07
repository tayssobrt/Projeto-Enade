package com.ads.enade.mapper;

import com.ads.enade.dto.questao.UsuarioQuestaoDTOResponse;
import com.ads.enade.entity.UsuarioQuestao;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {QuestaoMapper.class, AlternativaMapper.class})
public interface UsuarioQuestaoMapper {

    UsuarioQuestaoDTOResponse toDTO(UsuarioQuestao usuarioQuestao);
}
