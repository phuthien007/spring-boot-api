package springboot.Model.Converter;

import springboot.Entity.CourseEntity;
import springboot.Model.DTO.CourseDTO;

public class CourseConverter {

	public static CourseDTO toDTO(CourseEntity e) {
		// TODO Auto-generated method stub
		CourseDTO d = new CourseDTO();
		d.setId(e.getId());
		d.setName(e.getName());
		d.setType(e.getType());
		return d;
	}

	public static CourseEntity toEntity(CourseDTO d) {
		// TODO Auto-generated method stub
		CourseEntity e = new CourseEntity();
		e.setId(d.getId());
		e.setName(d.getName());
		e.setType(d.getType());
		return e;
	}	
	
}
