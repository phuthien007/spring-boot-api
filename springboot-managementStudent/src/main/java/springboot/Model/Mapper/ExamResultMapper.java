package springboot.Model.Mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import springboot.Entity.ExamResultEntity;
import springboot.Model.DTO.ExamResultDTO;

@Mapper(componentModel = "spring")
public interface ExamResultMapper {

    ExamResultMapper INSTANCE = Mappers.getMapper(ExamResultMapper.class);

    @Mapping(source = "c.id", target = "classId")
    @Mapping(source = "student.id", target = "studentId")
    @Mapping(source = "exam.id", target = "examId")
    ExamResultDTO toDTO(ExamResultEntity e);

    @InheritInverseConfiguration
    ExamResultEntity toEntity(ExamResultDTO d);
}
