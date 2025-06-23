package com.example.Voting_System.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.Voting_System.entity.Admin;

@Repository
public interface AdminRepo extends JpaRepository<Admin, Long> {
	
	Admin findByUsernameAndPassword(String username, String password);

}
