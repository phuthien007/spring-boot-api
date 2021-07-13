package springboot.Model.Converter;

import springboot.Entity.ExamEntity;
import springboot.Model.DTO.ExamDTO;

public class ExamConverter {

	public static ExamDTO toDTO(ExamEntity e) {
		ExamDTO d = new ExamDTO();
		d.setId(e.getId());
		d.setName(e.getName());
		if(e.getCourse() != null)
			d.setCourse(CourseConverter.toDTO(e.getCourse()));
		
		return d;
	}
	
	public static ExamEntity toEntity(ExamDTO d) {
		ExamEntity e = new ExamEntity();
		e.setId(d.getId());;
		e.setName(d.getName());
		if(d.getCourse() != null)
			e.setCourse(CourseConverter.toEntity(d.getCourse()));
		
		return e;
	}

}
