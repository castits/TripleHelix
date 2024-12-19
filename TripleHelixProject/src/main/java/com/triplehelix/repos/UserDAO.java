package com.triplehelix.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.triplehelix.entities.User;

@Repository
public interface UserDAO extends JpaRepository<User, Integer> {
	
	Optional<User> findUserByEmail(String email);

}
