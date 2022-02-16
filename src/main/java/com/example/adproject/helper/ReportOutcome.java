package com.example.adproject.helper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReportOutcome {
	
	@NotNull
	private Boolean inappropriate;
	
	@NotBlank
	private String comments;
}
