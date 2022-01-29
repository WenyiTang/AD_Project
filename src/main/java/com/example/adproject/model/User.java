package com.example.adproject.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String username;
	private String password;
	private String name;
	private String gender;
	@Past
	@DateTimeFormat(pattern="dd-MM-yyyy")
	private LocalDate dateOfBirth;
	private Double height;
	private Double weight;
	private String profilePic;
	
	@OneToMany(mappedBy = "author")
	private List<Comment> comments;
	
	@OneToOne
	private Session session;
}
