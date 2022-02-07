package com.example.adproject.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.validation.constraints.Past;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name="user_type", discriminatorType = DiscriminatorType.STRING)
@Entity
public class User {

	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String username;
	private String email;
	private String password;
	private String name;
	private String gender;

	@Past
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private LocalDate dateOfBirth;
	private double height;
	private double weight;
	private String profilePic;
	private boolean enabled; 
	@Column(name = "reset_password_token")
	private String resetPasswordToken; 

	@OneToMany(mappedBy = "author", cascade = { CascadeType.ALL })
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Comment> comments;

	@OneToMany(mappedBy = "author", cascade = { CascadeType.ALL })
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<MealEntry> entries;

	@ManyToMany(mappedBy = "likers")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<MealEntry> likedEntries;

	@OneToMany(targetEntity = Goal.class, cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	@OrderColumn
	private List<Goal> goals;

	@OneToMany(mappedBy = "recipient")
	private List<FriendRequest> receivedRequests;

	@OneToMany(mappedBy = "sender")
	private List<FriendRequest> sentRequests;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(
			name = "users_roles",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id")
			)
	private Set<Role> roles = new HashSet<>();
	
	/* Test of using enum for role differentiation for Spring Security */
//	@Column(name="role", columnDefinition = "ENUM('USER', 'ADMIN')")
//	@Enumerated(EnumType.STRING)
//	private RoleEnum role; 
	
//	@OneToOne
//	private Session session;

	public User(Integer id, String username, String password, String name, String gender, LocalDate dateOfBirth,
			double height, double weight, String profilePic) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.name = name;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.height = height;
		this.weight = weight;
		this.profilePic = profilePic;
	}

	// Constructor used in FriendRequestTest unit tests
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	// Constructor used in LoginUseCase unit tests
	public User(String username, String password, String email, boolean enabled) {
		this.username = username;
		this.password = password;
		this.email = email; 
		this.enabled = enabled; 
	}

	public User(String name, @Past LocalDate dateOfBirth, double height, double weight) {
		super();
		this.name = name;
		this.dateOfBirth = dateOfBirth;
		this.height = height;
		this.weight = weight;
	}

	
}