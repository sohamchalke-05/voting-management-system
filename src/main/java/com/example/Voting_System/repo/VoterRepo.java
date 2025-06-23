package com.example.Voting_System.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Voting_System.entity.Voter;


public interface VoterRepo extends JpaRepository<Voter, Long> {

    Voter findByVerificationToken(String token);
}
