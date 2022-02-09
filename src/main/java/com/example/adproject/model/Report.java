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
	
	@ManyToOne
	private MealEntry mealEntry;
	
	@ManyToOne
	private User reporter;
}
