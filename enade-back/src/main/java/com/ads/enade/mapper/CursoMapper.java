package com.ads.enade.mapper;

import com.ads.enade.dto.course.CourseDtoResponse;
import com.ads.enade.entity.Curso;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CursoMapper {

    CourseDtoResponse toDTO(Curso curso);
}
