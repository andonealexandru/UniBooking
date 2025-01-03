package com.unibooking.service.mapper;

import com.unibooking.domain.Person;
import com.unibooking.service.dto.PersonDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonMapper {
    Person toEntity(PersonDTO dto);
}
