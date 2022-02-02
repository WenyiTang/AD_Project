package com.example.adproject.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Role {

	@Id
	@Column(name = "role_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id; 
	
	private String type; 
	
}
