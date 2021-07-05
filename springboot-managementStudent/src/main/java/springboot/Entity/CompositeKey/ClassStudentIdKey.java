package springboot.Entity.CompositeKey;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class ClassStudentIdKey implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long classId;
	private Long studentId;
		
	
	/**
	 * @return the classId
	 */
	public Long getClassId() {
		return classId;
	}
	/**
	 * @param classId the classId to set
	 */
	public void setClassId(Long classId) {
		this.classId = classId;
	}
	/**
	 * @return the studentId
	 */
	public Long getStudentId() {
		return studentId;
	}
	/**
	 * @param studentId the studentId to set
	 */
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
	/**
	 * @param classId
	 * @param studentId
	 */
	public ClassStudentIdKey(Long classId, Long studentId) {
		super();
		this.classId = classId;
		this.studentId = studentId;
	}
	/**
	 * 
	 */
	public ClassStudentIdKey() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
