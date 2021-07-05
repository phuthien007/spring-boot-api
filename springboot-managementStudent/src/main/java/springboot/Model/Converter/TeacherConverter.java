package springboot.Model.Converter;

import springboot.Entity.TeacherEntity;
import springboot.Model.DTO.TeacherDTO;

public class TeacherConverter {

	public static TeacherDTO toDTO(TeacherEntity e) {
		// TODO Auto-generated method stub
		TeacherDTO d = new TeacherDTO();
		d.setAddress(e.getAddress());
		d.setEmail(e.getEmail());
		d.setFullname(e.getFullname());
		d.setGrade(e.getGrade());
		d.setId(e.getId());
		d.setPhone(e.getPhone());
		return d;
	}

	public static TeacherEntity toEntity(TeacherDTO e) {
		// TODO Auto-generated method stub
		TeacherEntity d = new TeacherEntity();
		d.setAddress(e.getAddress());
		d.setEmail(e.getEmail());
		d.setFullname(e.getFullname());
		d.setGrade(e.getGrade());
		d.setId(e.getId());
		d.setPhone(e.getPhone());
		return d;
	}	
}
