package springboot.Model.Converter;

import springboot.Entity.UserEntity;
import springboot.Model.DTO.UserDTO;

public class UserConverter {

	public static UserDTO toDTO(UserEntity e) {
		UserDTO d  = new UserDTO();
		d.setBirthday(e.getBirthday());
		d.setEmail(e.getEmail());
		d.setFullname(e.getFullname());
		d.setId(e.getId());
		d.setLastLoginDate(e.getLastLoginDate());
		d.setLockoutDate(e.getLockoutDate());
		d.setLoginFailedCount(e.getLoginFailedCount());
		d.setUsername(e.getUsername());
		d.setPassword(e.getPassword());
		d.setRole(RoleConverter.toDTO(e.getRole()) );
		return d;
	}
	
	public static UserEntity toEntity(UserDTO d) {
		UserEntity e  = new UserEntity();
		e.setBirthday(d.getBirthday());
		e.setEmail(d.getEmail());
		e.setFullname(d.getFullname());
		e.setId(d.getId());
		e.setLastLoginDate(d.getLastLoginDate());
		e.setLockoutDate(d.getLockoutDate());
		e.setLoginFailedCount(d.getLoginFailedCount());
		e.setUsername(d.getUsername());
		e.setPassword(d.getPassword());
		e.setRole(RoleConverter.toEntity(d.getRole()) );
		return e;
	}
	
}
