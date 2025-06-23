package com.example.Voting_System.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Setter
@Getter
@Entity
@RequiredArgsConstructor
@ToString
@AllArgsConstructor
public class Election {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String description;
	private String date;
	private String state;
	private String city;

	private String startDate;
	private String endDate;
	private String status; // e.g., UPCOMING, ONGOING, COMPLETED
}