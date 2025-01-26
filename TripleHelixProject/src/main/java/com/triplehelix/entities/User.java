package com.triplehelix.entities;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

/**
 * User entity that represents a registered user
 * This class implements Spring Security's UserDetails interface to integrate with authentication and authorization mechanisms
 */
@Entity
@Table(name = "users")
public class User implements UserDetails {

	// User id. Unique identifier for the user (AUTO_INCREMENT)
	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userId;
	
	// User's name
	@Column(name = "user_name", nullable = false)
	private String userName;
	
	// User's surname
	@Column(name = "user_surname", nullable = false)
	private String userSurname;
	
	// User's email (unique in the database)
	@Column(name = "user_email", unique = true, nullable = false)
	private String userEmail;
	
	// Encrypted user's password
	@Column(name = "user_password", nullable = false)
	private String userPassword;
	
	// Token used for password reset
	@Column(name = "reset_token")
	private String resetToken;
	
	// Timestamp when the user was created; not updatable
	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;
	
	// Timestamp for the last update of the user
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;
	
	// Many to one relationship with Role; each user is assigned only one role
	@ManyToOne
	@JoinColumn(name = "role_id")
	private Role role;
	
	/**
	 * Lifecycle callback to set createdAt and updatedAt before persisting the entity
	 */
	@PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

	/**
     * Lifecycle callback to update updatedAt before updating the entity
     */
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
	
    // Default constructor
	public User() {}

	// Constructor
	public User(String userName, String userSurname, String userEmail, String userPassword, Role role) {
		this.userName = userName;
		this.userSurname = userSurname;
		this.userEmail = userEmail;
		this.userPassword = userPassword;
		this.role = role;
	}
	
    /**
     * Returns the roles granted to the user
     * In this case, it's a single role for the user
     */
	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(role);
    }

    /**
     * Returns the password of the user for authentication
     */
    @Override
    public String getPassword() {
        return userPassword;
    }

    /**
     * Returns the username (in this case, email) for authentication
     */
    @Override
    public String getUsername() {
        return userEmail;
    }
    
    /**
     * Indicates whether the user's account is expired. Always returns true (not expired)
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user's account is locked. Always returns true (not locked)
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indicates whether the user's credentials are expired. Always returns true (not expired)
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user's account is enabled. Always returns true
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
    
    // Getters and Setters

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserSurname() {
		return userSurname;
	}

	public void setUserSurname(String userSurname) {
		this.userSurname = userSurname;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getResetToken() {
		return resetToken;
	}

	public void setResetToken(String resetToken) {
		this.resetToken = resetToken;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
}
