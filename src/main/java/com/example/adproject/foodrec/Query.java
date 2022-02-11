package com.example.adproject.foodrec;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Query {
	private String input;
	private String feeling;
	private String track;
}
