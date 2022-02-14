package com.example.adproject.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.example.adproject.helper.ReportEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Report {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String reason;
	private LocalDate dateReported;
	private LocalDate dateResolved;
	@Column(columnDefinition = "ENUM('IN_PROGRESS', 'PENDING', 'RESOLVED')")
	@Enumerated(EnumType.STRING)
	private ReportEnum status;
	private String comments;
	
	@OneToOne
	private User resolvedBy;

	// Added @JsonIgnore annotation to @ManyToOne mapped attributes
	// This is to prevent java.lang.IllegalStateException Cannot call sendError()
	//   from occuring when submitting report to ReportAPI
	@JsonIgnore
	@ManyToOne
	private MealEntry mealEntry;
	@JsonIgnore
	@ManyToOne
	private User reporter;

	// Constructor used for user submitted reports (i.e. flagging)
	public Report(String reason, MealEntry mealEntry, User reporter) {
		this.reason = reason;
		this.dateReported = LocalDate.now();
		this.status = ReportEnum.PENDING;
		this.mealEntry = mealEntry;
		this.reporter = reporter;
	}
}
