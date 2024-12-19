package com.triplehelix.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.triplehelix.entities.Role;


@Repository
public interface RoleDAO extends JpaRepository<Role, Integer> {

}
