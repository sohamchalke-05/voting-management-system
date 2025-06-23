package com.example.Voting_System.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.Voting_System.entity.Profile;
import com.example.Voting_System.services.ProfileService;

@Controller
@RequestMapping
public class ProfileController {

	@Autowired
	private ProfileService profileService;

	@GetMapping("/profileEdit")
	public String showUploadForm(Model model) {
		model.addAttribute("profile", new Profile());
		return "Profile";
	}

	@GetMapping("/uploadProfile")
	public String uploadProfile(Model model) {
		model.addAttribute("profile", new Profile());
		return "uploadProfile";
	}

	@PostMapping("/upload")
	public String uploadProfile(@RequestParam("pid") Long pid, @RequestParam("file") MultipartFile file, Model model) {
		try {
			Profile profile = new Profile();
			profile.setPid(pid);
			profile.setImage(file.getBytes());
			profileService.setProfile(profile);
			model.addAttribute("message", "Profile image uploaded successfully!");
		} catch (IOException e) {
			model.addAttribute("message", "Failed to upload profile image!");
			e.printStackTrace();
		}
		return "uploadProfile";
	}

	@GetMapping("/view")
	public ResponseEntity<byte[]> viewProfileImage(@RequestParam("profileId") Long profileId) {
		Profile profile = profileService.getById(profileId);
		if (profile != null && profile.getImage() != null) {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.IMAGE_JPEG); // Assuming the image type is JPEG
			return new ResponseEntity<>(profile.getImage(), headers, HttpStatus.OK);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
}
