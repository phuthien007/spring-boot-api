package springboot.Model.DTO;

import java.util.Date;

public class ExamResultDTO {
	private Long id;
	
	private StudentDTO student;
	
	private ExamDTO exam;
	
	private Long score;
	
	private Date resultDate;
	
	private ClassDTO classes;
	
	private String note;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the student
	 */
	public StudentDTO getStudent() {
		return student;
	}

	/**
	 * @param student the student to set
	 */
	public void setStudent(StudentDTO student) {
		this.student = student;
	}

	/**
	 * @return the exam
	 */
	public ExamDTO getExam() {
		return exam;
	}

	/**
	 * @param exam the exam to set
	 */
	public void setExam(ExamDTO exam) {
		this.exam = exam;
	}

	/**
	 * @return the score
	 */
	public Long getScore() {
		return score;
	}

	/**
	 * @param score the score to set
	 */
	public void setScore(Long score) {
		this.score = score;
	}

	/**
	 * @return the resultDate
	 */
	public Date getResultDate() {
		return resultDate;
	}

	/**
	 * @param resultDate the resultDate to set
	 */
	public void setResultDate(Date resultDate) {
		this.resultDate = resultDate;
	}

	/**
	 * @return the classes
	 */
	public ClassDTO getClasses() {
		return classes;
	}

	/**
	 * @param classes the classes to set
	 */
	public void setClasses(ClassDTO classes) {
		this.classes = classes;
	}

	/**
	 * @return the note
	 */
	public String getNote() {
		return note;
	}

	/**
	 * @param note the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * @param id
	 * @param student
	 * @param exam
	 * @param score
	 * @param resultDate
	 * @param classes
	 * @param note
	 */
	public ExamResultDTO(Long id, StudentDTO student, ExamDTO exam, Long score, Date resultDate, ClassDTO classes,
			String note) {
		super();
		this.id = id;
		this.student = student;
		this.exam = exam;
		this.score = score;
		this.resultDate = resultDate;
		this.classes = classes;
		this.note = note;
	}

	/**
	 * 
	 */
	public ExamResultDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "ExamResultDTO [id=" + id + ", student=" + student.getId() + ", exam=" + exam.getId() + ", score=" + score
				+ ", resultDate=" + resultDate + ", classes=" + classes.getId() + ", note=" + note + "]";
	}

	

	
}
