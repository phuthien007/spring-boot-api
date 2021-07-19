package springboot.Model.Mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import springboot.Entity.ExamEntity;
import springboot.Model.DTO.ExamDTO;

@Mapper(componentModel = "spring")
public interface ExamMapper {

    ExamMapper INSTANCE = Mappers.getMapper(ExamMapper.class);

    @Mapping(source = "course.id", target = "courseId")
    ExamDTO toDTO(ExamEntity e);

    @InheritInverseConfiguration
    ExamEntity toEntity(ExamDTO d);
}
