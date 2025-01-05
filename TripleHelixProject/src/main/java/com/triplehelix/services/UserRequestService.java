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
	
	public List<UserRequest> getAllRequests() {
		return userRequestDAO.findAll();
	}
	
	public Optional<UserRequest> getRequestById(int requestId) {
		return userRequestDAO.findById(requestId);
	}
	
    public UserRequest createRequest(UserRequest request) {
        request.setCreatedAt(LocalDateTime.now());
        request.setUpdatedAt(LocalDateTime.now());
        return userRequestDAO.save(request);
    }

    public void deleteRequest(int requestId) {
    	userRequestDAO.deleteById(requestId);
    }

}
