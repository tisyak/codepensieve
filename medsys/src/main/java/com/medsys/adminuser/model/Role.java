package com.medsys.adminuser.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;

import com.google.common.base.Objects;

 
@Entity
@Table(name = "ROLES")
public class Role implements Serializable, GrantedAuthority  {
 
    private static final long serialVersionUID = 6874667425302308430L;
    static Logger logger = LoggerFactory.getLogger(Role.class);
 
    @Id
    @Column(name = "role_id", length = 30)
    private String id;
    
    @NotNull(message = "{error.roles.role.null}")
    @NotEmpty(message = "{error.roles.role.empty}")
    @Size(max = 50, message = "{error.roles.role.max}")
    @Column(name = "role", length = 30)
    private String rolename;    
       
    //@OneToMany(cascade = CascadeType.ALL)  
    @OneToMany(fetch = FetchType.EAGER)  
    @JoinColumn(name = "role_id")
    private Set<AdminRole> adminRoles;
     
    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "role_permissions",
        joinColumns        = { @JoinColumn(name = "role_id") },
        inverseJoinColumns = { @JoinColumn(name = "permission_id", referencedColumnName = "perm_id") }
    )    
    private Set<Permission> permissions;
 
    
    
    
    public Role() {
		super();
	}

	public Role(String id) {
		super();
		this.id = id;
	}

	public String getRolename() {
        return rolename;
    }
 
    public void setRolename(String rolename) {
        this.rolename = rolename;
    }
        
   
	public Set<AdminRole> getAdminRoles() {
        return adminRoles;
    }
 
    public void setAdminRoles(Set<AdminRole> adminRoles) {
        this.adminRoles = adminRoles;
    }
 
    public Set<Permission> getPermissions() { 
        return permissions; 
    }
 
    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }
     
    
    @Override
    public String toString() {
        return String.format("%s(id=%s, rolename='%s')", 
                this.getClass().getSimpleName(), 
                this.getId(), this.getRolename());
    } 
 
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
 
        if (o instanceof Role) {
            final Role other = (Role) o;
            return Objects.equal(getId(), other.getId());
		}
        

        if (o instanceof AdminRole) {
        	
            final AdminRole other = (AdminRole) o;
            return Objects.equal(getId(), other.getRole().getId());
		}
		return false;
	}

	/*@Override
	public String toString() {
		return "Role [id=" + id + ", rolename=" + rolename + ", userRoles="
				+ userRoles + ", permissions=" + permissions + "]";
	} */

	@Override
	public int hashCode() {
		int hash =Objects.hashCode(getId()); 
		
		return hash;
	}
 
    @Override
    public String getAuthority() {
        return getRolename();
    }
    
    public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
	
	public String getRoleId() {
		return id;
	}
		

}