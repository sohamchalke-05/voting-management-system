package com.example.Voting_System.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@Entity
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class Voter {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotBlank(message = "Name cannot be Blank") @Pattern(regexp = "^[a-zA-Z]+$", message = "Only characters are allowed") @Size(min = 8, max = 16, message = "Name should have at least 8 to 16 characters")
	private String name;
	private LocalDate dob;
	private String gender;
	private String username;
	private String password;
	@Column(nullable = false)
	private boolean verified = false;
	@NotBlank(message = "Email cannot be blank")
	@Column(unique = true)
	private String email;
	private String verificationToken;

	@NotBlank(message = "Phone cannot be blank")
	@Pattern(regexp = "^[0-9]{10}$", message = "Phone must be 10 digits")
	private String phone;

	public boolean isEligible() {
		if (dob == null) return false;
		return java.time.Period.between(dob, java.time.LocalDate.now()).getYears() >= 18;
	}
}