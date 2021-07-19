package springboot.Entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "events")
public class EventEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@ManyToOne
	@OnDelete(action = OnDeleteAction.NO_ACTION)
	@JoinColumn(name = "class_id", referencedColumnName = "id")
	private ClassEntity c;

	@Column(name = "create_date")
	@Temporal(TemporalType.DATE)
	private Date createDate;

	@Column(name = "status", nullable = false)
	private String status;

	@Column(name = "happen_date")
	@Temporal(TemporalType.DATE)
	private Date happenDate;

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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the c
	 */
	public ClassEntity getC() {
		return c;
	}

	/**
	 * @param c the c to set
	 */
	public void setC(ClassEntity c1) {
		c = c1;
	}

	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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
	 * @return the happenDate
	 */
	public Date getHappenDate() {
		return happenDate;
	}

	/**
	 * @param happenDate the happenDate to set
	 */
	public void setHappenDate(Date happenDate) {
		this.happenDate = happenDate;
	}

	/**
	 * @param id
	 * @param name
	 * @param c
	 * @param createDate
	 * @param status
	 * @param happenDate
	 */
	public EventEntity(Long id, String name, ClassEntity c1, Date createDate, String status, Date happenDate) {
		super();
		this.id = id;
		this.name = name;
		c = c1;
		this.createDate = createDate;
		this.status = status;
		this.happenDate = happenDate;
	}

	/**
	 * 
	 */
	public EventEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

}
