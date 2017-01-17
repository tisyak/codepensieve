package com.medsys.adminuser.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;

import com.google.common.base.Objects;

@Entity
@Table(name = "admin_roles")
public class AdminRole implements Serializable, GrantedAuthority {

	private static final long serialVersionUID = 1L;
	static Logger logger = LoggerFactory.getLogger(AdminRole.class);

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "admin_role_id", columnDefinition = "serial")
	private Integer adminRoleId;

	@NotNull(message = "{error.role.id.null}")
	@Column(name = "admin_id")
	private int adminId;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "role_id")
	private Role role;

	@Column(name = "is_active")
	private Boolean isActive;

	@Column(name = "update_by")
	private String updateBy;

	@Column(name = "update_timestamp")
	private Timestamp updateTimestamp;

	public AdminRole() {
		super();
		// default constructor
	}

	public AdminRole(Role role) {
		this.role = role;
	}

	public Integer getAdminRoleId() {
		return adminRoleId;
	}

	public void setAdminRoleId(Integer adminRoleId) {
		this.adminRoleId = adminRoleId;
	}

	public int getAdminId() {
		return adminId;
	}

	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	public String getRoleId() {
		return role.getId();
	}

	public void setRoleId(String roleId) {
		this.role.setId(roleId);
	}
	
	public String getRolename() {
		return role.getRolename();
	}

	public void setRolename(String rolename) {
		this.role.setRolename(rolename);
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Timestamp getUpdateTimestamp() {
		return updateTimestamp;
	}

	public void setUpdateTimestamp(Timestamp updateTimestamp) {
		this.updateTimestamp = updateTimestamp;
	}

	@Override
	public boolean equals(Object o) {
		//logger.debug("searching for :"+this);
		if (this == o)
			return true;
		if (o == null)
			return false;

		if (o instanceof AdminRole) {
			final AdminRole other = (AdminRole) o;
			//logger.debug("comparing with :"+other);
			return Objects.equal(getRole().getId(), other.getRole().getId())
					&& Objects.equal(getAdminId(), other.getAdminId())
					&& Objects.equal(getIsActive(), other.getIsActive());
		}

		if (o instanceof Role) {
			final Role other = (Role) o;
			return Objects.equal(getRole().getId(), other.getId());
		}
		return false;
	}

	@Override
	public int hashCode() {
			/*Since, AdminRole is constantly compared with Role and essentially Admin Role represents the Role itself
			 * returning hascode of only Role Id*/
		if(Boolean.TRUE.equals(this.isActive)){
			int hash = Objects.hashCode(getRole().getId());
			//logger.debug("Calculating hashcode based on Role id. Effective hash: " + hash);
			return hash;
		}
		else{
			int hash = Objects.hashCode(getRole().getId(),this.isActive);
			//logger.debug("Calculating hashcode based on Role id and isActive. Effective hash: " + hash);
			return hash;
		}
	}

	@Override
	public String toString() {
		return "AdminRoles [adminRoleId=" + adminRoleId + ", adminId="
				+ adminId + ", role=" + role + ", isActive=" + isActive
				+ ", updateBy=" + updateBy + ", updateTimestamp="
				+ updateTimestamp + "]";
	}

	@Override
	public String getAuthority() {
		return getRole().getRolename();
	}

}
