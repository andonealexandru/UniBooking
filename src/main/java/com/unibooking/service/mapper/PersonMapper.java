package com.unibooking.service.mapper;

import com.unibooking.domain.Person;
import com.unibooking.service.dto.PersonDTO;
import com.unibooking.service.dto.PersonResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PersonMapper {
    Person toEntity(PersonDTO dto);
    @Mapping(source = "code", target = "code")
    PersonResponseDTO toResponseDTO(Person entity);
}
