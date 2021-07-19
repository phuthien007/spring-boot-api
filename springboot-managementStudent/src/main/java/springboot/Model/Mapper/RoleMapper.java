package springboot.Model.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import springboot.Entity.RoleEntity;
import springboot.Model.DTO.RoleDTO;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    RoleDTO toDTO(RoleEntity e);
    RoleEntity toEntity(RoleDTO d);
}
