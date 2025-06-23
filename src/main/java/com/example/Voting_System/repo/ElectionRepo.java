package com.example.Voting_System.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Voting_System.entity.Election;

public interface ElectionRepo extends JpaRepository<Election, Long>{

}
