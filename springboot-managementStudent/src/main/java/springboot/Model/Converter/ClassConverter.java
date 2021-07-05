package springboot.Model.Converter;

import springboot.Entity.ClassEntity;
import springboot.Model.DTO.ClassDTO;

public class ClassConverter {

	public static ClassDTO toDTO( ClassEntity e ) {
		ClassDTO d = new ClassDTO();
		d.setCourse(CourseConverter.toDTO(e.getCourse()));
		d.setEndDate(e.getEndDate());
		d.setId(e.getId());
		d.setName(e.getName());
		d.setStatus(e.getStatus());
		d.setStartDate(e.getStartDate());
		d.setTeacher(TeacherConverter.toDTO(e.getTeacher()));
		return d;
	}
	
	public static ClassEntity toEntity( ClassDTO d ) {
		ClassEntity e = new ClassEntity();
		e.setCourse(CourseConverter.toEntity(d.getCourse()));
		e.setEndDate(d.getEndDate());
		e.setId(d.getId());
		e.setName(d.getName());
		e.setStatus(d.getStatus());
		e.setStartDate(d.getStartDate());
		e.setTeacher(TeacherConverter.toEntity(d.getTeacher()));		
		return e;
	}
	
}
