package com.medsys.adminuser.model;

import javax.management.relation.Role;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;

import com.google.common.base.Objects;

 
@Entity
@Table(name = "M_PERMISSIONS")
public class Permission implements GrantedAuthority {
 
    private static final long serialVersionUID = -5404269148967698143L;
    static Logger logger = LoggerFactory.getLogger(Permission.class);
    
    @Id
    @Column(name = "perm_id")
    private String id;
    
    @NotNull(message = "{error.permission.permissionname.null}")
    @NotEmpty(message = "{error.permission.permissionname.empty}")
    @Size(max = 50, message = "{permission.permissionname.role.max}")
    @Column(name = "permission", length = 50)
    private String permissionname;
     
   /* @OneToMany(fetch = FetchType.EAGER)  
    @JoinTable(name = "role_permissions",   
        joinColumns        = {@JoinColumn(name = "perm_id", referencedColumnName = "permission_id")},  
        inverseJoinColumns = {@JoinColumn(name = "role_id")}  
    )  
    private Set<Role> permRoles;*/
 
    
    public String getPermissionname() {
        return permissionname;
    }
 
    public void setPermissionname(String permissionname) {
        this.permissionname = permissionname;
    }
 
    @Override
    public String getAuthority() {
        return permissionname;
    }
 
   /* public Set<Role> getPermRoles() {
        return permRoles;
    }
 
    public void setPermRoles(Set<Role> permRoles) {
        this.permRoles = permRoles;
    }
 */
   
    @Override
    public String toString() {
        return String.format("%s(id=%s, permissionname='%s')", 
                this.getClass().getSimpleName(), 
                this.getId(), this.getPermissionname());
    }
 
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
 
        if (o instanceof Role) {
            final Permission other = (Permission) o;
            return Objects.equal(getId(), other.getId())
					&& Objects.equal(getPermissionname(), other.getPermissionname());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId(), getPermissionname());
	}

    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

    
}