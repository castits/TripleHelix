package com.triplehelix.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.triplehelix.entities.UserRequest;
import com.triplehelix.repos.UserRequestDAO;

@Service
public class UserRequestService {
	
	@Autowired
	private UserRequestDAO userRequestDAO;
	
	public List<UserRequest> getAllUserRequests() {
		return userRequestDAO.findAll();
	}
	
	public Optional<UserRequest> getUserRequestById(int userRequestId) {
		return userRequestDAO.findById(userRequestId);
	}
	
    public UserRequest createUserRequest(UserRequest userRequest) {
    	userRequest.setCreatedAt(LocalDateTime.now());
    	userRequest.setUpdatedAt(LocalDateTime.now());
        return userRequestDAO.save(userRequest);
    }

    public void deleteUserRequest(int userRequestId) {
    	userRequestDAO.deleteById(userRequestId);
    }

}
