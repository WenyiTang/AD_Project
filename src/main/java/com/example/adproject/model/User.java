package com.example.adproject.model;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import java.util.Set;

import java.util.Objects;

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
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.format.annotation.DateTimeFormat;

import com.example.adproject.validator.LogicalDimension;
import com.example.adproject.validator.UniqueEmail;
import com.example.adproject.validator.UniqueUsername;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class User {

	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@UniqueUsername(message = "Username is already taken")
	private String username;
	@UniqueEmail(message = "Email address is already registered")
	@Email(message = "Invalid email address")
	private String email;
	private String password;
	private String name;
	private String gender;

	@Past(message = "Must be a past date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dateOfBirth;
	@LogicalDimension(message = "Invalid height")
	private Double height;
	@LogicalDimension(message = "Invalid weight")
	private Double weight;
	@Column(nullable = true, length = 64)
	private String profilePic;

	private boolean enabled;
	@Column(name = "reset_password_token")
	private String resetPasswordToken;

	@OneToMany(mappedBy = "author", cascade = { CascadeType.ALL }, orphanRemoval = true)
	@LazyCollection(LazyCollectionOption.TRUE)
	private List<Comment> comments;

	@JsonIgnore
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
	@JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
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

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof User)) {
			return false;
		}
		User user = (User) o;
		return Objects.equals(id, user.id) && Objects.equals(username, user.username);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, username);
	}

	public User(String name, @Past LocalDate dateOfBirth, double height, double weight) {
		super();
		this.name = name;
		this.dateOfBirth = dateOfBirth;
		this.height = height;
		this.weight = weight;
	}

	// For displaying profile pic
	@Transient
	public String getImagePath() {
		if (profilePic == null || id == null) {
			return null;
		}
		return "/user-profilePic." + id + "/" + profilePic;
	}
}