package com.triplehelix.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.triplehelix.entities.User;

/**
 * This interface provides users access in the database
 * It extends JpaRepository, which contains default queries
 * for CRUD operations and allows defining custom query methods
 */
@Repository
public interface UserDAO extends JpaRepository<User, Integer> {
	
	/**
	 * Returns a specific user searched by his email
	 * @param email - the user's email
	 * @return an Optional that could contain a user (found by email)
	 */
	Optional<User> findUserByUserEmail(String email);
	
	/**
	 * Returns a user found by his reset token
	 * @param resetToken - the user's reset token
	 * @return an Optional that could contain a user (found by reset token)
	 */
	Optional<User> findByResetToken(String resetToken);
	
	/**
	 * Returns the role id associated with a specific user (user id)
	 * This method uses a custom query with @Query annotation
	 * @param userId - the user's id
	 * @return the role id of a user (found by user id)
	 */
	@Query("SELECT u.role.roleId FROM User u WHERE u.userId = :userId")
    Integer findRoleIdByUserId(@Param("userId") int userId);

}
