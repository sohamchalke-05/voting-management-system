package com.example.Voting_System.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Voting_System.entity.Election;
import com.example.Voting_System.repo.ElectionRepo;

@Service
public class ElectionServiceImpl implements ElectionService {
	@Autowired
	private ElectionRepo electionrepo;

	public ElectionServiceImpl() {
	}

	public List<Election> findAllElections() {
		return this.electionrepo.findAll();
	}

	public Election findById(Long id) {
		return (Election) this.electionrepo.findById(id).orElseThrow();
	}

	public void saveElection(Election election) {
		this.electionrepo.save(election);
	}

	public void deleteById(Long id) {
		this.electionrepo.deleteById(id);
	}
}
