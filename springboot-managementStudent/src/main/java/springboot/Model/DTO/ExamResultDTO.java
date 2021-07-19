package springboot.Model.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Date;
//@JsonInclude(value = JsonInclude.Include.NON_NULL)

public class ExamResultDTO {
	private Long id;
	
	private Long studentId;
	
	private Long examId;
	
	private Long score;
	
	private Date resultDate;
	
	private Long classId;
	
	private String note;

	public ExamResultDTO() {
	}

	public ExamResultDTO(Long id, Long studentId, Long examId, Long score, Date resultDate, Long classId, String note) {
		this.id = id;
		this.studentId = studentId;
		this.examId = examId;
		this.score = score;
		this.resultDate = resultDate;
		this.classId = classId;
		this.note = note;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public Long getExamId() {
		return examId;
	}

	public void setExamId(Long examId) {
		this.examId = examId;
	}

	public Long getScore() {
		return score;
	}

	public void setScore(Long score) {
		this.score = score;
	}

	public Date getResultDate() {
		return resultDate;
	}

	public void setResultDate(Date resultDate) {
		this.resultDate = resultDate;
	}

	public Long getClassId() {
		return classId;
	}

	public void setClassId(Long classId) {
		this.classId = classId;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
}
