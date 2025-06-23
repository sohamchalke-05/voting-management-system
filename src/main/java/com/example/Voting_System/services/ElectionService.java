package com.example.Voting_System.services;

import java.util.List;

import com.example.Voting_System.entity.Election;

public interface ElectionService {

	List<Election> findAllElections();

	Election findById(Long id);

	void saveElection(Election election);

	void deleteById(Long id);
}
