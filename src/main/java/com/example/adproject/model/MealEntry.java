package com.example.adproject.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.example.adproject.helper.FeelingEnum;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class MealEntry {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String imageURL;
	private String filename;
	private boolean visibility;
	private String title;
	private String description;
	private boolean flagged;
	@Column(columnDefinition = "ENUM('CRYING', 'PENSIVE', 'HAPPY', 'JOYFUL')")
	@Enumerated(EnumType.STRING)
	private FeelingEnum feeling;
	private int trackScore;
	private LocalDateTime timeStamp;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Goal goal;
	
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name = "meal_entry_likers", 
	  joinColumns = @JoinColumn(name = "meal_entry_id"), 
	  inverseJoinColumns = @JoinColumn(name = "user_id"))
	private List<User> likers;
	
	@ManyToOne
	private User author;
	
	@OneToMany(mappedBy = "mealEntry", cascade = { CascadeType.ALL })
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Comment> comments;
	
	
	public MealEntry(String imageURL, boolean visibility, String title, String description, 
					 boolean flagged, FeelingEnum feeling, int trackScore, LocalDateTime timeStamp, 
					 Goal goal, List<User> likers, User author, List<Comment> comments) {
		this.imageURL = imageURL;
		this.visibility = visibility;
		this.title = title;
		this.description = description;
		this.flagged = flagged;
		this.feeling = feeling;
		this.trackScore = trackScore;
		this.timeStamp = timeStamp;
		this.goal = goal;
		this.likers = likers;
		this.author = author;
		this.comments = comments;
	}
}
