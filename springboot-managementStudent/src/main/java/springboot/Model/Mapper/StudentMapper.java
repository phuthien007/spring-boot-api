package springboot.Model.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import springboot.Entity.StudentEntity;
import springboot.Model.DTO.StudentDTO;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);

    StudentDTO toDTO(StudentEntity e);
    StudentEntity toEntity(StudentDTO d);
}
