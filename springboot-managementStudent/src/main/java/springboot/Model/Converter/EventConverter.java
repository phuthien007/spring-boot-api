package springboot.Model.Converter;

import springboot.Entity.EventEntity;
import springboot.Model.DTO.EventDTO;

public class EventConverter {

	public static EventDTO toDTO(EventEntity e) {
		EventDTO d= new EventDTO();
		if(e.getC() != null)
			d.setClasses(ClassConverter.toDTO(e.getC()));
		d.setHappenDate(e.getHappenDate());
		d.setId(e.getId());
		d.setName(e.getName());
		d.setStatus(e.getStatus());
		return d;
	}
	
	public static EventEntity toEntity(EventDTO d) {
		EventEntity e= new EventEntity();
		if(d.getClasses() != null)
			e.setC(ClassConverter.toEntity(d.getClasses()));
		e.setHappenDate(d.getHappenDate());
		e.setId(d.getId());
		e.setName(d.getName());
		e.setStatus(d.getStatus());
		return e;
	}
}
