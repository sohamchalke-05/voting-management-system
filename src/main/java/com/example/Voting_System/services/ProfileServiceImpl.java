package com.example.Voting_System.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.Voting_System.entity.Profile;
import com.example.Voting_System.repo.ProfileRepo;

@Service
public class ProfileServiceImpl implements ProfileService {

	@Autowired
	private ProfileRepo profilerepo;

	public ProfileServiceImpl() {
	}

	public Profile setProfile(Profile profile) {
		return (Profile) this.profilerepo.save(profile);
	}

	public Profile getById(Long id) {
		return (Profile) this.profilerepo.findById(id).orElseThrow();
	}

	public Profile existById(Long id) {
		return  profilerepo.findById(id).orElse(null);
	}

}
