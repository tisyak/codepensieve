package com.medsys.adminuser.model;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.google.common.base.Objects;
import com.medsys.master.model.AdminAccountStatus;

@Entity
@Table(name = "ADMIN_USERS")
public class AdminUser implements UserDetails {

	private static final long serialVersionUID = 6311364761937265306L;
	static Logger logger = LoggerFactory.getLogger(AdminUser.class);

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "admin_id", columnDefinition = "serial")
	private Integer id;

	@NotNull(message = "{error.field.required}")
	@NotEmpty(message = "{error.field.required}")
	@Size(max = 50, message = "{error.user.username.max}")
	@Column(name = "user_name", length = 100)
	private String username;

	@NotNull(message = "{error.field.required}")
	@NotEmpty(message = "{error.field.required}")
	@Column(name = "name")
	private String name;

	@Column(name = "email")
	private String email;

	@Size(max = 10)
	@Column(name = "mobile_number")
	private String mobileNumber;

	@Size(max = 90, message = "{error.user.password.max}")
	@Column(name = "passwd", length = 90)
	private String password;

	@Transient
	private String confirmPassword;
	
	@Transient
	private String oldPassword;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "account_status_code", referencedColumnName = "status_code")
	private AdminAccountStatus adminAccountStatus;

	/** The update timestamp. */
	@Column(name = "reg_timestamp")
	private Timestamp registrationTimestamp;

	/** The update by. */
	@Column(name = "update_by")
	private String updateBy;

	/** The update timestamp. */
	@Column(name = "update_timestamp")
	private Timestamp updateTimestamp;

	@Transient
	private boolean enabled;
	
	@Transient
    private String captcha;

	@OneToMany(mappedBy = "adminId", fetch = FetchType.EAGER)
	@Cascade(value={CascadeType.ALL})
	private Set<AdminRole> adminRoles;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Timestamp getRegistrationTimestamp() {
		return registrationTimestamp;
	}

	public void setRegistrationTimestamp(Timestamp registrationTimestamp) {
		this.registrationTimestamp = registrationTimestamp;
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

	public boolean getEnabled() {
		if (!this.adminAccountStatus.getStatusCode().equals("D")) {
			this.enabled = true;
		} else {
			this.enabled = false;
		}

		return this.enabled;

	}

	public void setEnabled(boolean enabled) {
		if (!this.adminAccountStatus.getStatusCode().equals("D")) {
			this.enabled = true;
		} else {
			this.enabled = false;
		}
	}



	public Set<AdminRole> getAdminRoles() {
		return adminRoles;
	}
	
	public Set<AdminRole> getActiveAdminRoles() {
		 Set<AdminRole> activeAdminRoles = new HashSet<AdminRole>();
		for(AdminRole role: adminRoles){
			if(role.getIsActive()){
				activeAdminRoles.add(role);
			}
		};
		return activeAdminRoles;
		
	}

	public void setAdminRoles(Set<AdminRole> adminRoles) {
		this.adminRoles = adminRoles;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null)
			return false;

		if (o instanceof AdminUser) {
			final AdminUser other = (AdminUser) o;
			return Objects.equal(getUsername(), other.getUsername());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getUsername());
	}

	@Transient
	public Set<Permission> getPermissions() {
		Set<Permission> perms = new HashSet<Permission>();
		for (AdminRole adminRoles : adminRoles) {
			perms.addAll(adminRoles.getRole().getPermissions());
		}
		return perms;
	}

	@Override
	@Transient
	public Collection<GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		authorities.addAll(getActiveAdminRoles());
		authorities.addAll(getPermissions());
		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		// return true = account is valid / not expired
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// return true = account is not locked
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// return true = password is valid / not expired
		return true;
	}

	@Override
	public boolean isEnabled() {
		if (!this.adminAccountStatus.getStatusCode().equals("D")) {
			return true;
		} else {
			return false;
		}
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public AdminAccountStatus getAdminAccountStatus() {
		return adminAccountStatus;
	}

	public void setAdminAccountStatus(AdminAccountStatus adminAccountStatus) {
		this.adminAccountStatus = adminAccountStatus;
	}

	public Integer getId() {
		return id;
	}
	
	@Override
	public String toString() {
		return "AdminUser [id=" + id + ", username=" + username + ", name="
				+ name + ", email=" + email + ", mobileNumber=" + mobileNumber
				+ ", password=" + password + ", adminAccountStatus="
				+ adminAccountStatus 
				+ ", registrationTimestamp=" + registrationTimestamp
				+ ", updateBy=" + updateBy + ", updateTimestamp="
				+ updateTimestamp + ", enabled=" + enabled 
				+ ", adminRoles=" + adminRoles + "]";
	}
}