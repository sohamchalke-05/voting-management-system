package com.example.Voting_System.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.*;

import java.util.Arrays;

@Setter
@Getter
@Entity
@RequiredArgsConstructor
@ToString
@AllArgsConstructor
public class Profile {
	@Id
	@Column(name = "profile", length = 100)
	private Long pid;
	@Lob
	@Column(columnDefinition = "LONGBLOB")
	private byte[] image;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Profile profile = (Profile) o;
		return pid != null && pid.equals(profile.pid);
	}

	@Override
	public int hashCode() {
		return java.util.Objects.hash(pid);
	}
}
