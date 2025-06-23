package com.example.Voting_System.entity;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Setter
@Getter
@Entity
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class Admin {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long aid;
	private @NotBlank(message = "Name cannot be empty") @Size(min = 4, max = 12, message = "Name should have at least 4 to 12 characters") @Pattern(regexp = "^[a-zA-Z]+$", message = "Only characters are allowed") String aname;
	private String address;
	private String gender;
	private Date dob;
	private String username;
	private String password;
}
