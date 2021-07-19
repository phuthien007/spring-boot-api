package springboot.Entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import springboot.Entity.CompositeKey.PermistionUserIdKey;

//
@Entity
@Table(name = "authorization_each_author")
//@IdClass(PermistionUserIdKey.class)
public class AuthorizationEachAuthorEntity {

//	
//	
	@EmbeddedId
	private PermistionUserIdKey id;

	@ManyToOne(cascade = CascadeType.ALL)
	@MapsId("permistionId")
	@JoinColumn(name = "per_id", referencedColumnName = "id")
	private PermistionEntity permistion;

	@ManyToOne( cascade = CascadeType.ALL)
	@MapsId("userId")
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private UserEntity user;

	@Column(name = "description")
	private String description;

	/**
	 * @return the id
	 */
	public PermistionUserIdKey getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(PermistionUserIdKey id) {
		this.id = id;
	}

	/**
	 * @return the permistion
	 */
	public PermistionEntity getPermistion() {
		return permistion;
	}

	/**
	 * @param permistion the permistion to set
	 */
	public void setPermistion(PermistionEntity permistion) {
		this.permistion = permistion;
	}

	/**
	 * @return the user
	 */
	public UserEntity getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(UserEntity user) {
		this.user = user;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @param id
	 * @param permistion
	 * @param user
	 * @param description
	 */
	public AuthorizationEachAuthorEntity(PermistionUserIdKey id, PermistionEntity permistion, UserEntity user,
			String description) {
		super();
		this.id = id;
		this.permistion = permistion;
		this.user = user;
		this.description = description;
	}

	/**
	 * 
	 */
	public AuthorizationEachAuthorEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

}
