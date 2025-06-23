package com.example.Voting_System.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.Voting_System.entity.Admin;
import com.example.Voting_System.repo.AdminRepo;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminRepo adminrepo;

	public AdminServiceImpl() {

	}

	public void saveAdmin(Admin admin) {
		this.adminrepo.save(admin);

	}

	public Admin findByUsernameAndPassword(String username, String password) {
		return  this.adminrepo.findByUsernameAndPassword(username, password);

	}

}
