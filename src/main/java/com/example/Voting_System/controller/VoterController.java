package com.example.Voting_System.controller;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import com.example.Voting_System.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.Voting_System.entity.Election;
import com.example.Voting_System.entity.Voter;
import com.example.Voting_System.services.ElectionService;
import com.example.Voting_System.services.VoterService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class VoterController {

	@Autowired
	private VoterService voterService;
	@Autowired
	private ElectionService electionService;

	@Autowired
	private EmailService emailService; // Assuming you have an EmailService for sending emails

	public VoterController() {
	}

	@GetMapping({ "/Vdashboard" })
	public String showVoterDashboard() {
		return "Vdashboard";
	}

	@GetMapping({ "/voterRegister" })
	public String showRegistrationForm(Model model) {
		model.addAttribute("voter", new Voter());
		return "voterRegister";
	}

	@PostMapping("/goVoterRegister")
	public String registerVoter(@ModelAttribute("voter") Voter voter,
								BindingResult result,
								Model model) {
		if (result.hasErrors()) {
			return "voterRegister";
		}

		// Generate verification token
		String token = UUID.randomUUID().toString();
		voter.setVerificationToken(token);
		voter.setVerified(false);

		// Save voter with token
		voterService.saveVoter(voter);  // This should persist voter including token

		// Build verification link
		String verifyLink = "http://localhost:8080/verify?token=" + token;

		// Send email
		emailService.sendVerificationEmail(voter.getEmail(), verifyLink);

		// Show success message
		model.addAttribute("message", "Registration successful! Please check your email to verify your account.");
		return "voterlogin"; // Or redirect to a "registration-success" page
	}


	@GetMapping({ "/voterlogin" })
	public String showLoginForm(Model model) {
		return "voterlogin";
	}

	@PostMapping("/goVoterLogin")
	public String loginVoter(@RequestParam("username") String username,
							 @RequestParam("password") String password,
							 Model model,
							 HttpSession session) {

		List<Voter> voters = voterService.voterlist();

		for (Voter voter : voters) {
			if (voter.getUsername().equals(username) && voter.getPassword().equals(password)) {
				if (!voter.isVerified()) {
					model.addAttribute("error", "Your account is not verified. Please check your email.");
					return "voterlogin";
				}

				session.setAttribute("id", voter.getId());
				model.addAttribute("voter", voter);
				return "redirect:/Vdashboard";
			}
		}

		model.addAttribute("error", "Invalid username or password.");
		return "voterlogin";
	}


//	@GetMapping({ "/voterslist" })
//	public String viewVoters(Model model) {
//		List<Voter> voters = this.voterService.voterlist();
//		model.addAttribute("voters", voters);
//		return "voterslist";
//	}

//	@GetMapping({ "/deleteVoter/{id}" })
//	public String deleteVoter(@PathVariable("id") Long id) {
//		if (this.voterService.findById(id) != null) {
//			this.voterService.deleteById(id);
//		}
//
//		return "redirect:/voterslist";
//	}

//	@GetMapping({ "/editVoterDetails/{id}" })
//	public String showEditForm(@PathVariable("id") Long id, Model model) {
//		Voter voter = this.voterService.findById(id);
//		if (voter != null) {
//			model.addAttribute("voter", voter);
//			return "editVoterDetails";
//		} else {
//			return "redirect:/voterslist";
//		}
//	}

//	@PostMapping({ "/updateVoter" })
//	public String updateVoter(@ModelAttribute("voter") Voter voter, BindingResult result) {
//		if (result.hasErrors()) {
//			return "editVoterDetails";
//		} else {
//			this.voterService.updateVoter(voter);
//			return "redirect:/voterslist";
//		}
//	}

	@GetMapping({ "/viewElections" })
	public String viewElections(Model model) {
		List<Election> elections = this.electionService.findAllElections();
		model.addAttribute("elections", elections);
		return "viewElections";
	}

	@GetMapping({ "/viewElectionsToVoter" })
	public String viewElectionsToVoter(Model model) {
		List<Election> elections = this.electionService.findAllElections();
		model.addAttribute("elections", elections);
		return "viewElectionsToVoter";
	}

//	@GetMapping({ "/addElection" })
//	public String showAddElectionForm(Model model) {
//		model.addAttribute("election", new Election());
//		return "addElection";
//	}

//	@PostMapping({ "/goAddElection" })
//	public String goAddElection(@ModelAttribute("election") Election election, BindingResult result, Model model) {
//		if (result.hasErrors()) {
//			return "addElection";
//		} else {
//			this.electionService.saveElection(election);
//			return "redirect:/viewElections";
//		}
//	}

//	@GetMapping({ "/editElection/{id}" })
//	public String showEditElectionForm(@PathVariable("id") Long id, Model model) {
//		Election election = this.electionService.findById(id);
//		if (election != null) {
//			model.addAttribute("election", election);
//			return "editElection";
//		} else {
//			return "redirect:/viewElections";
//		}
//	}

//	@PostMapping({ "/updateElection" })
//	public String updateElection(@ModelAttribute("election") Election election, BindingResult result) {
//		if (result.hasErrors()) {
//			return "editElection";
//		} else {
//			this.electionService.saveElection(election);
//			return "redirect:/viewElections";
//		}
//	}

//	@GetMapping({ "/deleteElection/{id}" })
//	public String deleteElection(@PathVariable("id") Long id) {
//		if (this.electionService.findById(id) != null) {
//			this.electionService.deleteById(id);
//		}
//
//		return "redirect:/viewElections";
//	}

	@GetMapping({ "/logoutVoter" })
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}

		return "redirect:/logoutSuccessVoter";
	}

	@GetMapping({ "/logoutSuccessVoter" })
	public String showLogoutSuccessPageVoter() {
		return "logoutSuccessVoter";
	}

	@GetMapping({ "/inputIdVoter" })
	public String inputIdVoter(Model model) {
		return "inputIdVoter";
	}

	@PostMapping({ "/validateIdVoter" })
	public String validateIdVoter(@RequestParam("id") Long id, HttpSession session, Model model) {
		Long sessionId = (Long) session.getAttribute("id");

		if (sessionId == null) {
			model.addAttribute("error", "No voter logged in or session expired.");
			return "inputIdVoter"; // Redirect to the input page to enter ID again
		}

		if (!sessionId.equals(id)) {
			model.addAttribute("error",
					"The ID entered does not match the logged-in voter's ID. Please enter the correct ID.");
			return "inputIdVoter"; // Show the error message
		}

		Voter voter = voterService.findById(id);
		if (voter == null) {
			model.addAttribute("error", "Voter not found.");
			return "inputIdVoter";
		}

		model.addAttribute("voter", voter);
		return "viewDetailsToVoter";
	}

	@GetMapping({ "/editOwnDetails/{id}" })
	public String showEditOwnDetails(@PathVariable("id") Long id, Model model) {
		Voter voter = voterService.findById(id);
		if (voter != null) {
			model.addAttribute("voter", voter);
			return "editVoterDetails";
		} else {
			return "redirect:/viewDetailsToVoter";
		}
	}

	@PostMapping("/updateOwnDetails")
	public String updateOwnDetails(@ModelAttribute("voter") Voter voter, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("voter", voter);
			return "editVoterDetails";
		}
		voterService.saveVoter(voter);
		model.addAttribute("message", "Details updated successfully.");
		return "redirect:/viewDetailsToVoter";
	}

	@GetMapping({ "/deleteOwnDetails/{id}" })
	public String showDeleteOwnDetails(@PathVariable("id") Long id) {
		if (voterService.findById(id) != null) {
			voterService.deleteById(id);
		}

		return "redirect:/Main";
	}

	@GetMapping("/viewDetailsToVoter")
	public String viewDetailsToVoter(HttpSession session, Model model) {
		Long id = (Long) session.getAttribute("id");
		if (id == null) {
			return "redirect:/Vdashboard"; // Redirect to dashboard if no ID found
		}
		Voter voter = voterService.findById(id);
		model.addAttribute("voter", voter);
		return "viewDetailsToVoter"; // The Thymeleaf template name
	}

	@GetMapping("/verify")
	public String verifyAccount(@RequestParam("token") String token, Model model) {
		Voter voter = voterService.findByVerificationToken(token);
		if (voter == null) {
			model.addAttribute("message", "Invalid or expired verification link.");
			return "verification-failed"; // Create this view
		}

		voter.setVerified(true);
		voter.setVerificationToken(null); // Optional: clear the token
		voterService.saveVoter(voter);

		model.addAttribute("message", "Your account has been successfully verified!");
		return "verification-success"; // Create this view
	}



}
