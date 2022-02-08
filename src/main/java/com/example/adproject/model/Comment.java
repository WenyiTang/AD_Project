package com.example.adproject.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@EqualsAndHashCode
public class Comment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String caption;
	// Added CascadeType.REMOVE to remove comment from associated MealEntry and author User
	// Without CascadeType.REMOVE, I couldn't delete comments
	
	@ManyToOne
	private User author;

	
	@ManyToOne
	private MealEntry mealEntry;
	
	public Comment(Integer id, String caption) {
		super();
		this.id=id;
		this.caption = caption;
	}

	public Comment(String caption, User author, MealEntry mealEntry) {
		super();
		this.caption = caption;
		this.author = author;
		this.mealEntry = mealEntry;

	}
}