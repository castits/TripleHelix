package com.triplehelix.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.triplehelix.entities.UserRequest;
import com.triplehelix.services.UserRequestService;

@RestController
@RequestMapping("/api/requests")
public class UserRequestController {
	
	@Autowired
	private UserRequestService userRequestService;
	
	@GetMapping
    public List<UserRequest> getAllRequests() {
        return userRequestService.getAllUserRequests();
    }

    @GetMapping("/{id}")
    public Optional<UserRequest> getRequestById(@PathVariable int id) {
        return userRequestService.getUserRequestById(id);
    }

    @PostMapping
    public UserRequest createRequest(@RequestBody UserRequest userRequest) {
        return userRequestService.createUserRequest(userRequest);
    }
	
}
