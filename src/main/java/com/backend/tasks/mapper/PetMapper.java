package com.backend.tasks.mapper;

import com.backend.tasks.dto.PetCreateOrUpdateDto;
import com.backend.tasks.dto.PetReadDto;
import com.backend.tasks.model.Pet;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.WARN)
public interface PetMapper {
    PetMapper INSTANCE = Mappers.getMapper(PetMapper.class);

    PetReadDto petToPetReadDto(Pet pet);


    Pet petCreateOrUpdateDtoToPet(PetCreateOrUpdateDto petDto);
}
