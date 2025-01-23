package com.triplehelix.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.triplehelix.entities.User;

@Repository
public interface UserDAO extends JpaRepository<User, Integer> {
	
	Optional<User> findUserByUserEmail(String email);
	Optional<User> findByResetToken(String resetToken);
	
	@Query("SELECT u.role.roleId FROM User u WHERE u.userId = :userId")
    Integer findRoleIdByUserId(@Param("userId") int userId);

}
