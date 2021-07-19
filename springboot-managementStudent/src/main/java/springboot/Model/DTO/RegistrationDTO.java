package springboot.Model.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Date;
//@JsonInclude(value = JsonInclude.Include.NON_NULL)


public class RegistrationDTO {


	private Long classId;
	
	private Long studentId;
	
	private Date registerDay;
	
	private String status;

	public RegistrationDTO() {
	}

	public RegistrationDTO(Long classId, Long studentId, Date registerDay, String status) {
		this.classId = classId;
		this.studentId = studentId;
		this.registerDay = registerDay;
		this.status = status;
	}

	public Long getClassId() {
		return classId;
	}

	public void setClassId(Long classId) {
		this.classId = classId;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public Date getRegisterDay() {
		return registerDay;
	}

	public void setRegisterDay(Date registerDay) {
		this.registerDay = registerDay;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
