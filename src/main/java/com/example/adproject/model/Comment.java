package com.example.adproject.model;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Entity
public class Comment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String caption;
	@ManyToOne
	private User author;
	@ManyToOne
	private MealEntry mealEntry;
	
	public Comment(Integer id, String caption) {
		super();
		this.id=id;
		this.caption = caption;
	}
}