package com.example.adproject.helper;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EntriesDataTable {

	private List<Integer> id;
	private List<String> title;
	private List<String> description;
	private List<String> feeling;
	private List<Integer> track_score;
	private String input;
	private String feel;
	private String track;
}
