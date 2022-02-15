package com.example.adproject.api;

import java.util.ArrayList;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.adproject.helper.BlogEntry;
import com.example.adproject.helper.EntriesDataTable;
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
	//private String[] titles = new String[5];
	
	@Autowired
	UserService uService;

	@Autowired
	MealEntryRepo mrepo;

	@Autowired
	MealEntryService mService;

//	SearchResult results = new SearchResult();

//	@GetMapping("/getStringData")
//	public String getString() {
//		return "get text";
//	}


//	@RequestMapping(value = "/postStringData/{uid}", method = RequestMethod.POST)
//	public SearchQuery postString(@PathVariable("uid") Integer userId, @RequestParam String input, String feeling, String track) {
//		System.out.println("post string: " + userId);
//		System.out.println(input);
//		System.out.println(feeling);
//		System.out.println(track);
////		String q = input + " " + track + " " + feeling;
////		System.out.println(q);
//		User user = uService.findUser(userId);
//		SearchQuery query = sqrepo.findSearchQueryByUser(user);
//		if (query == null) {
//			query = new SearchQuery(user);
//		}
//		query.setQueryHelper(new QueryHelper(input, feeling, track)); 
//		sqrepo.save(query);
//		return query;
//	}
//
////	@RequestMapping(value = "/postpostStringData", method = RequestMethod.POST)
////	public String postpostString(@RequestBody String input) {
////		System.out.println(input);
////		return input;
////	}
//
//	@GetMapping("/enterquery1/{uid}")
//	public QueryHelper queryString(@PathVariable("uid") Integer userId) {
//		User user = uService.findUser(userId);
//		SearchQuery query = sqrepo.findSearchQueryByUser(user);
//		return query.getQueryHelper();
//	}
//
//	@RequestMapping(value = "/runSearch", method = RequestMethod.POST)
//	public SearchResult runSearch(Integer userId) {
//		System.out.print("post string: " + userId);
//		System.out.println("runSearch1");
//		String url = "http://localhost:5000/enterquery?uid=" + userId;
//		RestTemplate restTemplate = new RestTemplate();
//		results = restTemplate.getForObject(url, SearchResult.class);
//		System.out.println("got results from flask");
//		System.out.println(results.getRes0());
//		System.out.println(results.getRes1());
//		System.out.println(results.getRes2());
//		System.out.println(results.getRes3());
//		System.out.println(results.getRes4());
//		results.setResults(new Integer[] {
//				results.getRes0(), results.getRes1(), results.getRes2(), results.getRes3(), results.getRes4()
//				});
//		System.out.println(results.getGoodResult());
//		return results;
//	}
//
//	@GetMapping("/getResultJson/{uid}")
//	public RecResultJson resultJson(@PathVariable("uid") Integer userId) {
//		System.out.print("post string: " + userId);
//		results = runSearch(userId);
//		RecResultJson r = new RecResultJson();
//		System.out.println("getResultJson");
//		System.out.println(results.getRes0());
//		System.out.println(results.getRes1());
//		System.out.println(results.getRes2());
//		System.out.println(results.getRes3());
//		System.out.println(results.getRes4());
//		Integer[] resultArray = results.getResults();
//		for (int x=0; x<resultArray.length; x++) {
//			titles[x] = mrepo.findById(resultArray[x]).get().getTitle();
//		}
//		System.out.println("In getResultJson");
//		for (String s : titles) {
//			System.out.println(s);
//		}
//		r.setTitles(titles);
//		r.setGoodResult(results.getGoodResult()); 
//		return r;
//	}
	
	@RequestMapping(value = "/postStringData/{uid}/{input}/{feeling}/{track}", method = {RequestMethod.POST, RequestMethod.GET})
	public RecResultJson postQuery(@PathVariable("uid") Integer userId, @PathVariable("input") String input,
			@PathVariable("feeling") String feeling, @PathVariable("track") String track) {
		String url = "http://localhost:5000/enterquery/" 
			+ userId + "/" + input + "/" + feeling + "/" + track;
		RestTemplate restTemplate = new RestTemplate();
		SearchResult results = new SearchResult();
		results = restTemplate.getForObject(url, SearchResult.class);
		
		MealEntry[] resultArray = new MealEntry[] {
				mService.findMealEntryById(results.getRes0()),
				mService.findMealEntryById(results.getRes1()),
				mService.findMealEntryById(results.getRes2()),
				mService.findMealEntryById(results.getRes3()),
				mService.findMealEntryById(results.getRes4()),
		};
		
		RecResultJson r = new RecResultJson(resultArray, results.getGoodResult());
		
//		String[] titles = new String[resultArray.length];
//		for (int x=0; x<resultArray.length; x++) {
//			titles[x] = mrepo.findById(resultArray[x]).get().getTitle();
//		}
//		System.out.println("In getResultJson");
//		for (String s : titles) {
//			System.out.println(s);
//		}
//		r.setTitles(titles);
//		r.setGoodResult(results.getGoodResult()); 
		return r;
	}

	//pass mealEntryTable to Flask
	@RequestMapping(value = "/passData/{uid}", method = {RequestMethod.POST, RequestMethod.GET})
	public EntriesDataTable passDataToFlask(@PathVariable("uid") String userId) {
		System.out.println("userId: " + userId);
		User user = uService.findUser(Integer.parseInt(userId));
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
