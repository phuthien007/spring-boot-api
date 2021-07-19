package springboot.Model.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import springboot.Entity.CourseEntity;
import springboot.Model.DTO.CourseDTO;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    CourseMapper INSTANCE = Mappers.getMapper(CourseMapper.class);

    CourseDTO toDTO(CourseEntity e);
    CourseEntity toEntity(CourseDTO d);
}
