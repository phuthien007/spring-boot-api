package springboot.Model.Converter;

import springboot.Entity.ExamResultEntity;
import springboot.Model.DTO.ExamResultDTO;

public class ExamResultConverter {

	public static ExamResultDTO toDTO(ExamResultEntity e) {
		ExamResultDTO d= new ExamResultDTO();
		d.setId(e.getId());
		d.setScore(e.getScore());
		if(e.getC() != null)
			d.setClasses(ClassConverter.toDTO(e.getC()));
		if(e.getExam() != null)
			d.setExam(ExamConverter.toDTO(e.getExam()));
		d.setNote(e.getNote());
		if(e.getExam() != null)
			d.setStudent(StudentConverter.toDTO(e.getStudent()));
		d.setResultDate(e.getResultDate());
		
		return d;
	};	
	
	public static ExamResultEntity toEntity(ExamResultDTO d) {
		ExamResultEntity e= new ExamResultEntity();
		e.setId(d.getId());
		e.setScore(d.getScore());
		if(d.getExam() != null)
			e.setExam(ExamConverter.toEntity(d.getExam()));
		e.setNote(d.getNote());
		if(d.getStudent() != null)
			e.setStudent(StudentConverter.toEntity(d.getStudent()));
		if(d.getClasses() != null)
			e.setC(ClassConverter.toEntity(d.getClasses()));
		e.setResultDate(d.getResultDate());
		return e;
	};	
	
}
