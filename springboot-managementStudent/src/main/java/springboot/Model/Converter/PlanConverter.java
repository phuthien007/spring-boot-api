package springboot.Model.Converter;

import springboot.Entity.PlanEntity;
import springboot.Model.DTO.PlanDTO;

public class PlanConverter {

	public static PlanDTO toDTO(PlanEntity e) {
		PlanDTO d = new PlanDTO();
		d.setId(e.getId());
		d.setName(e.getName());
		d.setCourse(CourseConverter.toDTO(e.getCourse()));
		return d;
	}
	
	public static PlanEntity toEntity(PlanDTO d) {
		PlanEntity e = new PlanEntity();
		e.setId(d.getId());
		e.setName(d.getName());
		e.setCourse(CourseConverter.toEntity(d.getCourse()));
		return e;
	}
	
}
