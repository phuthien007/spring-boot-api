package springboot.Model.Converter;

import springboot.Entity.ExamResultEntity;
import springboot.Model.DTO.ExamResultDTO;

public class ExamResultConverter {

	public static ExamResultDTO toDTO(ExamResultEntity e) {
		ExamResultDTO d= new ExamResultDTO();
		d.setId(e.getId());
		d.setScore(e.getScore());
		d.setClasses(ClassConverter.toDTO(e.getC()));
		d.setExam(ExamConverter.toDTO(e.getExam()));
		d.setNote(e.getNote());
		d.setStudent(StudentConverter.toDTO(e.getStudent()));
		d.setResultDate(e.getResultDate());
		
		return d;
	};	
	
	public static ExamResultEntity toEntity(ExamResultDTO d) {
		ExamResultEntity e= new ExamResultEntity();
		e.setId(d.getId());
		e.setScore(d.getScore());
		e.setExam(ExamConverter.toEntity(d.getExam()));
		e.setNote(d.getNote());
		e.setStudent(StudentConverter.toEntity(d.getStudent()));
		e.setC(ClassConverter.toEntity(d.getClasses()));
		e.setResultDate(d.getResultDate());
		return e;
	};	
	
}
