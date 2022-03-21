package ru.learnup.vtb.operasales.mappers;

import org.mapstruct.Mapper;
import ru.learnup.vtb.operasales.controllers.dto.PremiereDto;
import ru.learnup.vtb.operasales.model.Premiere;
import ru.learnup.vtb.operasales.repositories.entities.PremiereEntity;

@Mapper(componentModel = "spring")
public interface PremiereMapper {

    PremiereDto toDto(Premiere premiere);
    Premiere toDomain(PremiereDto premiereDto);

    PremiereEntity toEntity(Premiere premiere);
    Premiere toDomain(PremiereEntity premiereEntity);

}
