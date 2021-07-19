package springboot.Model.Mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import springboot.Entity.PlanEntity;
import springboot.Model.DTO.PlanDTO;

@Mapper(componentModel = "spring")
public interface PlanMapper {

    PlanMapper INSTANCE = Mappers.getMapper(PlanMapper.class);

    @Mapping(source = "course.id", target = "courseId")
    PlanDTO toDTO(PlanEntity e);

    @InheritInverseConfiguration
    PlanEntity toEntity(PlanDTO d);
}
