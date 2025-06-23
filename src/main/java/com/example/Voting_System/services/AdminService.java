package com.example.Voting_System.services;

import org.springframework.stereotype.Service;

import com.example.Voting_System.entity.Admin;

@Service
public interface AdminService {

	void saveAdmin(Admin admin);

	Admin findByUsernameAndPassword(String username, String password);

	
}
