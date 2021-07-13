package springboot.Model.Converter;

import springboot.Entity.ClassEntity;
import springboot.Model.DTO.ClassDTO;

public class ClassConverter {

	public static ClassDTO toDTO( ClassEntity e ) {
		ClassDTO d = new ClassDTO();
		if(e.getCourse() != null)
			d.setCourse(CourseConverter.toDTO(e.getCourse()));
		d.setEndDate(e.getEndDate());
		d.setId(e.getId());
		d.setName(e.getName());
		d.setStatus(e.getStatus());
		d.setStartDate(e.getStartDate());
		if(e.getTeacher() != null)
			d.setTeacher(TeacherConverter.toDTO(e.getTeacher()));
		return d;
	}
	
	public static ClassEntity toEntity( ClassDTO d ) {
		ClassEntity e = new ClassEntity();
		if(d.getCourse() != null)
			e.setCourse(CourseConverter.toEntity(d.getCourse()));
		e.setEndDate(d.getEndDate());
		e.setId(d.getId());
		e.setName(d.getName());
		e.setStatus(d.getStatus());
		e.setStartDate(d.getStartDate());
		if(d.getTeacher() != null)
			e.setTeacher(TeacherConverter.toEntity(d.getTeacher()));
		return e;
	}
	
}
