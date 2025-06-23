package com.example.Voting_System.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.Voting_System.entity.Admin;
import com.example.Voting_System.entity.Voter;
import com.example.Voting_System.services.AdminService;
import com.example.Voting_System.services.VoterService;
import com.example.Voting_System.services.ElectionService;
import com.example.Voting_System.entity.Election;

@Controller
public class AdminController {

	@Autowired
	private AdminService adminService;
	
	@Autowired
	private VoterService voterservice;

	@Autowired
	private ElectionService electionService;

	public AdminController() {
	}

	@GetMapping({ "/Main" })
	public String showMainDashboard() {
		return "MainDashboard";
	}

	@GetMapping({ "/Adashboard" })
	public String showDashboard() {
		return "Adashboard";
	}

	@GetMapping({ "/adminRegister" })
	public String showRegistrationForm(Model model) {
		model.addAttribute("admin", new Admin());
		return "adminRegister";
	}

	@PostMapping({ "/goAdminRegister" })
	public String registerAdmin(Admin admin) {
		this.adminService.saveAdmin(admin);
		return "redirect:/adminlogin";
	}

	@GetMapping({ "/adminlogin" })
	public String showLoginForm() {
		return "adminlogin";
	}

	@PostMapping({ "/goAdminLogin" })
	public String loginAdmin(@RequestParam("username") String username, @RequestParam("password") String password,
			Model model) {
		Admin admin = this.adminService.findByUsernameAndPassword(username, password);
		if (admin != null) {
			return "redirect:/Adashboard";
		} else {
			model.addAttribute("error", "Invalid username or password");
			return "error";
		}
	}

	@GetMapping({ "/addVoter" })
	public String addVoter(Model model) {
		model.addAttribute("voter", new Voter());
		return "addVoter";
	}

	@PostMapping({ "/goAddVoter" })
	public String VoterAdd(Voter voter) {
		this.voterservice.saveVoter(voter);
		return "redirect:/voterslist";
	}
	
	@GetMapping({ "/logout" })
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}

		return "redirect:/logoutSuccess";
	}

	@GetMapping({ "/logoutSuccess" })
	public String showLogoutSuccessPage() {
		return "logoutSuccess";
	}
	
	@GetMapping({ "/voterslist" })
	public String listVoters(Model model) {
		model.addAttribute("voters", voterservice.voterlist());
		return "voterslist";
	}

	@GetMapping({ "/electionslist" })
	public String listElections(Model model) {
		model.addAttribute("elections", electionService.findAllElections());
		return "electionslist";
	}

	@PostMapping("/verifyVoter")
	public String verifyVoter(@RequestParam("id") Long id) {
		Voter voter = voterservice.findById(id);
		if (voter != null) {
			voter.setVerified(true);
			voterservice.updateVoter(voter);
		}
		return "redirect:/voterslist";
	}

}