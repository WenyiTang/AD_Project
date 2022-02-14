package com.example.adproject.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.adproject.helper.EntriesDataTable;
import com.example.adproject.helper.Query;
import com.example.adproject.helper.RecResultJson;
import com.example.adproject.helper.SearchResult;
import com.example.adproject.model.MealEntry;
import com.example.adproject.model.User;
import com.example.adproject.repo.MealEntryRepo;
import com.example.adproject.service.MealEntryService;
import com.example.adproject.service.UserService;

@RestController
@RequestMapping("/api/recommend")
public class RecommenderAPI {
	private String[] titles = new String[5];
	
	@Autowired
	UserService uService;

	@Autowired
	MealEntryRepo mrepo;

	@Autowired
	MealEntryService mService;

	SearchResult results = new SearchResult();

	Query query = new Query();

//	@GetMapping("/getStringData")
//	public String getString() {
//		return "get text";
//	}


	@RequestMapping(value = "/postStringData", method = RequestMethod.POST)
	public Query postString(@RequestParam String input, String feeling, String track) {
		System.out.println(input);
		System.out.println(feeling);
		System.out.println(track);
		String q = input + " " + feeling + " " + track;
		System.out.println(q);
		query.setInput(q);
		query.setFeeling(feeling);
		query.setTrack(track);
		return query;
	}

//	@RequestMapping(value = "/postpostStringData", method = RequestMethod.POST)
//	public String postpostString(@RequestBody String input) {
//		System.out.println(input);
//		return input;
//	}

	@GetMapping("/enterquery1")
	public Query queryString() {
		return query;
	}

	@RequestMapping(value = "/runSearch", method = RequestMethod.POST)
	public SearchResult runSearch() {
		System.out.println("runSearch1");
		String url = "http://localhost:5000/enterquery";
		RestTemplate restTemplate = new RestTemplate();
		results = restTemplate.getForObject(url, SearchResult.class);
		System.out.println("got results from flask");
		System.out.println(results.getRes0());
		System.out.println(results.getRes1());
		System.out.println(results.getRes2());
		System.out.println(results.getRes3());
		System.out.println(results.getRes4());
		results.setResults(new Integer[] {
				results.getRes0(), results.getRes1(), results.getRes2(), results.getRes3(), results.getRes4()
				});
		System.out.println(results.getGoodResult());
		return results;
	}

	@GetMapping("/getResultJson")
	public RecResultJson resultJson() {
		results = runSearch();
		RecResultJson r = new RecResultJson();
		System.out.println("getResultJson");
		System.out.println(results.getRes0());
		System.out.println(results.getRes1());
		System.out.println(results.getRes2());
		System.out.println(results.getRes3());
		System.out.println(results.getRes4());
		Integer[] resultArray = results.getResults();
		for (int x=0; x<resultArray.length; x++) {
			titles[x] = mrepo.findById(resultArray[x]).get().getTitle();
		}
		System.out.println("In getResultJson");
		for (String s : titles) {
			System.out.println(s);
		}
		r.setTitles(titles);
		r.setGoodResult(results.getGoodResult()); 
		return r;
	}

	//pass mealEntryTable to Flask
	@RequestMapping(value = "/passData", method = {RequestMethod.POST, RequestMethod.GET})
	public EntriesDataTable passDataToFlask() {
		User user = uService.findUser(1);
		List<MealEntry> entries = mService.findMealEntryByUser(user);
		//to implement include friend's posts
		//...
		
		Integer size = entries.size();
		System.out.println(size);
		EntriesDataTable data = new EntriesDataTable();
		List<Integer> ids = new ArrayList<Integer>();
		List<String> titles = new ArrayList<String>();
		List<String> descriptions = new ArrayList<String>();
		List<String> feelings = new ArrayList<String>();
		List<Integer> track_scores = new ArrayList<Integer>();
		for (MealEntry m : entries) {
			ids.add(m.getId());
			titles.add(m.getTitle());
			descriptions.add(m.getDescription());
			feelings.add(m.getFeeling().toString());
			track_scores.add(m.getTrackScore());
		}
		data.setId(ids.toArray(new Integer[size]));
		data.setTitle(titles.toArray(new String[size]));
		data.setDescription(descriptions.toArray(new String[size]));
		data.setFeeling(feelings.toArray(new String[size]));
		data.setTrack_score(track_scores.toArray(new Integer[size]));
		return data;
	}
}
