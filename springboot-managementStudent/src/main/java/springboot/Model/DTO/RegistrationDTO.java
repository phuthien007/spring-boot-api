package springboot.Model.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class RegistrationDTO {


	private ClassDTO classes;
	
	private StudentDTO student;
	
	private Date registerDay;
	
	private String status;

	

	

	/**
	 * @return the registerDay
	 */
	public Date getRegisterDay() {
		return registerDay;
	}

	/**
	 * @param registerDay the registerDay to set
	 */
	public void setRegisterDay(Date registerDay) {
		this.registerDay = registerDay;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the c
	 */
	public ClassDTO getC() {
		return classes;
	}

	/**
	 * @param c the c to set
	 */
	public void setC(ClassDTO c) {
		this.classes = c;
	}

	/**
	 * @return the s
	 */
	public StudentDTO getS() {
		return student;
	}

	/**
	 * @param s the s to set
	 */
	public void setS(StudentDTO s) {
		this.student = s;
	}

	/**
	 * 
	 */
	public RegistrationDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
}
