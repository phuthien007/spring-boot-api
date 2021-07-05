package springboot.Model.Converter;

import springboot.Entity.RoleEntity;
import springboot.Model.DTO.RoleDTO;

public class RoleConverter {

	public static RoleDTO toDTO(RoleEntity e) {
		// TODO Auto-generated method stub
		RoleDTO d = new RoleDTO();
		d.setId(e.getId());
		d.setRoleName(e.getRoleName());
		d.setDescription(e.getDescription());
		return d;
	}

	public static RoleEntity toEntity(RoleDTO d) {
		// TODO Auto-generated method stub
		RoleEntity e = new RoleEntity();
		e.setId(d.getId());
		e.setRoleName(d.getRoleName());
		e.setDescription(d.getDescription());
		return e;
	}

}
