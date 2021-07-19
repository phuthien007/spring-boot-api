package springboot.Model.Mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import springboot.Entity.RegistrationEntity;
import springboot.Model.DTO.RegistrationDTO;

@Mapper(componentModel = "spring")
public interface RegistrationMapper {

    RegistrationMapper INSTANCE = Mappers.getMapper(RegistrationMapper.class);

    @Mapping(source = "c.id", target = "classId")
    @Mapping(source = "s.id", target = "studentId")
    RegistrationDTO toDTO(RegistrationEntity e);

    @InheritInverseConfiguration
    RegistrationEntity toEntity(RegistrationDTO d);
}
