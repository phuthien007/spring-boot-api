package springboot.Model.Mapper;


import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import springboot.Entity.TeacherEntity;
import springboot.Model.DTO.TeacherDTO;

@Mapper(componentModel = "spring")
public interface TeacherMapper {

    TeacherMapper INSTANCE = Mappers.getMapper(TeacherMapper.class);

    TeacherDTO toDTO(TeacherEntity e);
    TeacherEntity toEntity(TeacherDTO d);
}
