package com.example.adproject.controller;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.example.adproject.helper.FeelingEnum;
import com.example.adproject.helper.StatusEnum;
import com.example.adproject.model.Goal;
import com.example.adproject.model.MealEntry;
import com.example.adproject.model.User;
import com.example.adproject.repo.GoalRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.adproject.repo.MealEntryRepo;
import com.example.adproject.repo.UserRepo;

@RestController
@RequestMapping("/api")
public class MealEntryController {
	
	@Value("${upload.file.path}")
	private String uploadPathStr;
	
	@Autowired
	UserRepo uRepo;

	@Autowired
	GoalRepo gRepo;
	
	@Autowired
	MealEntryRepo meRepo;
	
	@PostMapping("/test")
	public String testmethod(@RequestParam String arg) {
		return "test method success";
	}
	
	
	@PostMapping("/uploadMealEntry")
	public String uploadMealEntry(@RequestParam MultipartFile multipartFile,
								   @RequestParam String imageFileName,
								   @RequestParam String imageURL,
								   @RequestParam String mealTitle,
								   @RequestParam String description,
								   @RequestParam String feeling,
								   @RequestParam String trackScore,
								   @RequestParam String timeStamp,
								   @RequestParam String userId,
								   @RequestParam String flagged,
								   @RequestParam String visibility) {

		//STILL NEED USER DETAILS
		if (multipartFile == null || multipartFile.isEmpty()) {
			return "MultipartFileFailure";
		}
		
		try {
			InputStream inputStream = multipartFile.getInputStream();
			Path uploadPath = Paths.get(uploadPathStr);
			if (uploadPath.toFile().exists() == false) {
				uploadPath.toFile().mkdirs();
			}
			Files.copy(inputStream,  Paths.get(uploadPathStr).resolve(imageFileName), StandardCopyOption.REPLACE_EXISTING);
			System.out.println(imageFileName);
			System.out.println(imageURL);
			System.out.println(mealTitle);
			System.out.println(description);
			System.out.println(feeling);
			System.out.println(trackScore);
			System.out.println(timeStamp);

			FeelingEnum feeling_ = getFeelingEnumVal(feeling);
			int trackScore_ = Integer.parseInt(trackScore);
			DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
			LocalDateTime timeStamp_ = LocalDateTime.parse(timeStamp, format);

			int userIdInt = Integer.parseInt(userId);
			User user_ = uRepo.findById(userIdInt).get();
			Goal userCurrentGoal = gRepo.findCurrentGoal(userIdInt);
			if (userCurrentGoal == null) {
				return "nocurrentgoalset";
			}
			int userCurrentGoalIdInt = userCurrentGoal.getId();

			MealEntry newMealEntry = new MealEntry();
			newMealEntry.setImageURL(imageURL);
			newMealEntry.setFilename(imageFileName);
			newMealEntry.setTitle(mealTitle);
			newMealEntry.setDescription(description);
			newMealEntry.setFeeling(feeling_);
			newMealEntry.setTrackScore(trackScore_);
			newMealEntry.setTimeStamp(timeStamp_);
			newMealEntry.setAuthor(user_);
			newMealEntry.setGoal(userCurrentGoal);
			newMealEntry.setFlagged(Boolean.parseBoolean(flagged));
			newMealEntry.setVisibility(Boolean.parseBoolean(visibility));
			meRepo.saveAndFlush(newMealEntry);
			int userTargetCount = gRepo.findGoalIdTargetCount(userCurrentGoalIdInt);
			List<Integer> userGoalTrackScore = meRepo.findMealEntryTrackScoreByUserAndGoalId(userIdInt, userCurrentGoalIdInt);
			System.out.println(userTargetCount);
			System.out.println(userGoalTrackScore);
			String responseString = sendDataToFlaskWMA(userTargetCount, userGoalTrackScore);

			boolean completed = checkAndSetGoalCompletion(userCurrentGoal, userGoalTrackScore);
			if (completed == true) {
				responseString = "goalcompleted";
			}

			return responseString;
		}
		catch (IOException e) {
			e.printStackTrace();
			return "IOException";
		}
	}

	private String sendDataToFlaskWMA(int targetCount, List<Integer> trackScore) {
		String responseString = null;
		try {
			URL flaskUrl = new URL("http://testdeploy4-env.eba-4ipwwsa4.us-east-1.elasticbeanstalk.com/suggestnextmeal");
			HttpURLConnection httpURLConnection = (HttpURLConnection) flaskUrl.openConnection();
			httpURLConnection.setConnectTimeout(10000);
			httpURLConnection.setReadTimeout(10000);
			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setRequestProperty("Content-Type", "application/json: charset=UTF-8");
			httpURLConnection.setRequestProperty("Accept", "application/json");
			httpURLConnection.setDoOutput(true);

			InputStream status = httpURLConnection.getErrorStream();
			System.out.println(status);

			ObjectMapper jsonMapper = new ObjectMapper();
			ObjectNode jsonData = jsonMapper.createObjectNode();

			jsonData.put("targetCount", jsonMapper.writeValueAsString(targetCount));
			jsonData.put("trackScore", jsonMapper.writeValueAsString(trackScore));

			String jsonString = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonData);
			System.out.println(jsonString);

			OutputStream ops = httpURLConnection.getOutputStream();
			ops.write(jsonString.getBytes(StandardCharsets.UTF_8));
			ops.close();

			InputStream ips = new BufferedInputStream(httpURLConnection.getInputStream());
			BufferedReader reader = new BufferedReader(new InputStreamReader(ips));
			StringBuilder response= new StringBuilder();
			String responseLine = null;
			while ((responseLine = reader.readLine()) != null) {
				response.append(responseLine);
			}
			responseString = response.toString();
			System.out.println(responseString);
		}
		catch (MalformedURLException e1) {
			e1.printStackTrace();
			System.err.println("Malformed URL Exception: check URL");
		}
		catch (IOException e2) {
			e2.printStackTrace();
			System.err.println("Post Error: Host Exception");
		}
		return responseString;
	}

	/*
	private void testsenddata(int targetCount, List<Integer> trackScore) {
		try {
			//step 1: test connection (Passed)
			URL testurl = new URL("http://127.0.0.1:5000/suggestnextmeal");
			HttpURLConnection httpURLConnection = (HttpURLConnection) testurl.openConnection();
			httpURLConnection.setConnectTimeout(10000);
			httpURLConnection.setReadTimeout(10000);
			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setRequestProperty("Content-Type", "application/json: charset=UTF-8");
			httpURLConnection.setRequestProperty("Accept", "application/json");
			httpURLConnection.setDoOutput(true);
			InputStream status = httpURLConnection.getErrorStream();
			System.out.println(status);

			//step 3: test if JSON object is being sent (Failed)
			//Flask not receiving Json data
			String targetCountString = String.valueOf(targetCount);
			String trackScoreString = trackScore.toString();
			System.out.println(trackScoreString);
			String jsonString = "{\"targetCount\":"+"\""+targetCountString+"\","+"\"trackScore\":"+"\""+trackScoreString+"\"}";
			System.out.println(jsonString);
			OutputStream ops = httpURLConnection.getOutputStream();
			ops.write(jsonString.getBytes(StandardCharsets.UTF_8));
			ops.close();

			//step 2: test get Flask response (Passed)
			InputStream ips = new BufferedInputStream(httpURLConnection.getInputStream());
			BufferedReader reader = new BufferedReader(new InputStreamReader(ips));
			StringBuilder response= new StringBuilder();
			String responseLine = null;
			while ((responseLine = reader.readLine()) != null) {
				response.append(responseLine);
			}
			System.out.println(response.toString());
		}
		catch (MalformedURLException e1) {
			e1.printStackTrace();
			System.err.println("Malformed URL Exception: check URL");
		}
		catch (IOException e2) {
			e2.printStackTrace();
			System.err.println("Post Error: Host Exception");
		}

	}
	 */

	private boolean checkAndSetGoalCompletion(Goal userCurrentGoal, List<Integer> userGoalTrackScore) {
		int totalMealCount = userCurrentGoal.getTotalMealCount();
		int numEntries = userGoalTrackScore.size();
		if (numEntries == totalMealCount) {
			userCurrentGoal.setStatus(StatusEnum.COMPLETED);
			gRepo.saveAndFlush(userCurrentGoal);
			return true;
		}
		else {
			return false;
		}
	}

	private FeelingEnum getFeelingEnumVal(String feeling) {
		if (feeling.equalsIgnoreCase("cry")) {
			return FeelingEnum.CRY;
		}
		if (feeling.equalsIgnoreCase("pensive")) {
			return FeelingEnum.PENSIVE;
		}
		if (feeling.equalsIgnoreCase("happy")) {
			return FeelingEnum.HAPPY;
		}
		if (feeling.equalsIgnoreCase("joy")) {
			return FeelingEnum.JOY;
		}
		else {
			return null;
		}
	}
}
