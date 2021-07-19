package springboot.Model.Mapper;

import javassist.ClassMap;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import springboot.Entity.ClassEntity;
import springboot.Entity.CourseEntity;
import springboot.Model.DTO.ClassDTO;

@Mapper(componentModel = "spring")
public interface ClassMapper {

    ClassMapper INSTANCE = Mappers.getMapper(ClassMapper.class);

    @Mapping(source = "course.id", target = "courseId")
    @Mapping(source = "teacher.id", target = "teacherId")
    ClassDTO toDTO(ClassEntity e);

    @InheritInverseConfiguration
    ClassEntity toEntity(ClassDTO d);

//    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    @InheritInverseConfiguration
//    void updateClassFromDto( ClassDTO d, @MappingTarget ClassEntity e);


}
