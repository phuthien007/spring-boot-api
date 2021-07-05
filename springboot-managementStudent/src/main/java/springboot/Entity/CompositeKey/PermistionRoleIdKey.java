package springboot.Entity.CompositeKey;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class PermistionRoleIdKey implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long role_id;
	private Long per_id;
	/**
	 * @return the role_id
	 */
	public Long getRole_id() {
		return role_id;
	}
	/**
	 * @param role_id the role_id to set
	 */
	public void setRole_id(Long role_id) {
		this.role_id = role_id;
	}
	/**
	 * @return the per_id
	 */
	public Long getPer_id() {
		return per_id;
	}
	/**
	 * @param per_id the per_id to set
	 */
	public void setPer_id(Long per_id) {
		this.per_id = per_id;
	}
	/**
	 * @param role_id
	 * @param per_id
	 */
	public PermistionRoleIdKey(Long role_id, Long per_id) {
		super();
		this.role_id = role_id;
		this.per_id = per_id;
	}
	/**
	 * 
	 */
	public PermistionRoleIdKey() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
