package com.triplehelix.entities;

import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Role entity represents a role for the users (ADMIN, USER)
 * This entity implements Spring Security's GrantedAuthority interface to integrate with the security framework
 */
@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {
	
	// Role id. Unique identifier for the role (AUTO_INCREMENT)
	@Id
	@Column(name = "role_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int roleId;
	
	// Role name (ADMIN, USER...)
	@Column(name="role_name", nullable = false)
	private String roleName;
	
    /**
     * Implements the getAuthority method from the GrantedAuthority interface
     * This method is used by Spring Security to retrieve the role as a granted authority
     * 
     * @return The role name prefixed with "ROLE_", as expected by Spring Security
     */
	@Override
	public String getAuthority() {
		return "ROLE_" + roleName;
	}

	// Getters and Setters
	
	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
