package springboot.Model.Converter;

import springboot.Entity.RegistrationEntity;
import springboot.Model.DTO.RegistrationDTO;

public class RegistrationConverter {

	public static RegistrationDTO toDTO(RegistrationEntity e) {
		RegistrationDTO d= new RegistrationDTO();
		d.setC(ClassConverter.toDTO(e.getC()));
		
		d.setRegisterDay(e.getRegisterDay());
		d.setS(StudentConverter.toDTO(e.getS()));
		d.setStatus(e.getStatus());
		return d;
	}
	
	public static RegistrationEntity toEntity(RegistrationDTO d) {
		RegistrationEntity e= new RegistrationEntity();
		if(d.getC() != null)
			e.setC(ClassConverter.toEntity(d.getC()));
		
		e.setRegisterDay(d.getRegisterDay());
		if(d.getS() != null)
			e.setS(StudentConverter.toEntity(d.getS()));
		e.setStatus(d.getStatus());
		return e;
	}
}
