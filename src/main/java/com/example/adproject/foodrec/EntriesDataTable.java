package com.example.adproject.foodrec;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EntriesDataTable {

	private Integer[] id;
	private String[] title;
	private String[] description;
	private String[] feeling;
	private Integer[] track_score;
}
