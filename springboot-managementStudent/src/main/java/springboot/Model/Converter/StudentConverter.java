package springboot.Model.Converter;

import springboot.Entity.StudentEntity;
import springboot.Model.DTO.StudentDTO;

public class StudentConverter {

	public static StudentDTO toDTO(StudentEntity e) {
		StudentDTO d = new StudentDTO();
		d.setAddress(e.getAddress());
		d.setBirthday(e.getBirthday());
		d.setEmail(e.getEmail());
		d.setFacebook(e.getFacebook());
		d.setPhone(e.getPhone());
		d.setNote(e.getNote());
		d.setFullname(e.getFullname());
		d.setId(e.getId());
		return d;
	}
	
	public static StudentEntity toEntity(StudentDTO d) {
		StudentEntity e = new StudentEntity();
		e.setAddress(d.getAddress());
		e.setBirthday(d.getBirthday());
		e.setEmail(d.getEmail());
		e.setFacebook(d.getFacebook());
		e.setPhone(d.getPhone());
		e.setNote(d.getNote());
		e.setId(d.getId());
		e.setFullname(d.getFullname());
		return e;
	}
	
}
