package com.ads.enade.mapper;

import com.ads.enade.dto.alternativa.AlternativaDTOResponse;
import com.ads.enade.entity.Alternativa;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AlternativaMapper {

    AlternativaDTOResponse toDTO(Alternativa alternativa);
}
