package com.example.Voting_System.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.Voting_System.entity.Profile;

@Repository
public interface ProfileRepo extends JpaRepository<Profile, Long> {

}
