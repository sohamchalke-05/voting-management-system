package com.example.Voting_System.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.Voting_System.entity.Voter;
import com.example.Voting_System.repo.VoterRepo;

@Service
public class VoterServiceImpl implements VoterService {
	
   @Autowired
   private VoterRepo voterrepo;

   public VoterServiceImpl() {
   }

   public List<Voter> voterlist() {
      return this.voterrepo.findAll();
   }

   public Voter findById(Long id) {
      return (Voter)this.voterrepo.findById(id).orElseThrow();
   }

   public Voter saveVoter(Voter voter) {
      return this.voterrepo.save(voter);
   }

   public Voter updateVoter(Voter voter) {
      return this.voterrepo.save(voter);
   }

   public void deleteById(Long id) {
      this.voterrepo.deleteById(id);
   }


   public Voter findByVerificationToken(String token) {
      return this.voterrepo.findByVerificationToken(token);
   }


}
