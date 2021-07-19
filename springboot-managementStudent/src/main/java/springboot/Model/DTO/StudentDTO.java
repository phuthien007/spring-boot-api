package springboot.Model.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;
//@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class StudentDTO {
	private Long id;
	private String fullname;
	private String address;
	private String email;
	private String phone;
	private Date birthday;
	private String note;
	private String facebook;
		
	
	
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
	 * @return the fullname
	 */
	public String getFullname() {
		return fullname;
	}
	/**
	 * @param fullname the fullname to set
	 */
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * @return the birthday
	 */
	public Date getBirthday() {
		return birthday;
	}
	/**
	 * @param birthday the birthday to set
	 */
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
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
	 * @return the facebook
	 */
	public String getFacebook() {
		return facebook;
	}
	/**
	 * @param facebook the facebook to set
	 */
	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}
	/**
	 * @param id
	 * @param fullname
	 * @param address
	 * @param email
	 * @param phone
	 * @param birthday
	 * @param note
	 * @param facebook
	 */
	public StudentDTO(Long id, String fullname, String address, String email, String phone, Date birthday, String note,
			String facebook) {
		super();
		this.id = id;
		this.fullname = fullname;
		this.address = address;
		this.email = email;
		this.phone = phone;
		this.birthday = birthday;
		this.note = note;
		this.facebook = facebook;
	}
	public StudentDTO() {
		// TODO Auto-generated constructor stub
	}	
	
	
}
