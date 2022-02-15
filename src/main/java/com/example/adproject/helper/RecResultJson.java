package com.example.adproject.helper;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.adproject.model.MealEntry;
import com.example.adproject.service.MealEntryService;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RecResultJson {
	
	private String[] titles;
	private String[] authors;
	private String[] feelings;
	private String[] trackScores;
	private String[] imageUrls;
	private String goodResult;
	
	public RecResultJson(MealEntry[] results, String goodResult) {
		
		int count = results.length;
		
		this.titles = new String[count];
		this.authors = new String[count];
		this.feelings = new String[count];
		this.trackScores = new String[count];
		this.imageUrls = new String[count];
		this.goodResult = goodResult;
		
		for (int x=0; x<count; x++) {
			MealEntry m = results[x];
			titles[x] = m.getTitle();
			authors[x] = m.getAuthor().getUsername();
			feelings[x] = m.getFeeling().toString();
			if (m.getTrackScore() == 1) {
				trackScores[x] = "on-track";
			}
			else {
				trackScores[x] = "off-track";
			}
			imageUrls[x] = m.getImageURL();
		}
	}
}
