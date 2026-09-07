package com.ads.enade.mapper;

import com.ads.enade.dto.questao.QuestaoDTOResponse;
import com.ads.enade.entity.Questao;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = AlternativaMapper.class)
public interface QuestaoMapper {

    QuestaoDTOResponse toDTO(Questao questao);
}
