package com.example.adproject.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.example.adproject.helper.FeelingEnum;
import com.example.adproject.model.MealEntry;
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
	MealEntryRepo meRepo;
	
	@PostMapping("/test")
	public String testmethod(@RequestParam String arg) {
		return "test method success";
	}
	
	
	@PostMapping("/uploadMealEntry")
	public boolean uploadMealEntry(@RequestParam MultipartFile multipartFile,
								   @RequestParam String imageFileName,
								   @RequestParam String imageURL,
								   @RequestParam String mealTitle,
								   @RequestParam String feeling,
								   @RequestParam String trackScore,
								   @RequestParam String timeStamp) {

		//STILL NEED USER DETAILS
		if (multipartFile == null || multipartFile.isEmpty()) {
			return false;
		}
		
		try {
			InputStream inputStream = multipartFile.getInputStream();
			Path uploadPath = Paths.get(uploadPathStr);
			if (uploadPath.toFile().exists() == false) {
				uploadPath.toFile().mkdirs();
			}
			Files.copy(inputStream,  Paths.get(uploadPathStr).resolve(imageFileName), StandardCopyOption.REPLACE_EXISTING);
			System.out.println(imageFileName);
			System.out.println(mealTitle);
			System.out.println(imageURL);
			System.out.println(feeling);
			System.out.println(trackScore);
			System.out.println(timeStamp);

			FeelingEnum feeling_ = getFeelingEnumVal(feeling);
			int trackScore_ = Integer.parseInt(trackScore);
			DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
			LocalDateTime timeStamp_ = LocalDateTime.parse(timeStamp, format);

			//get user
			//get goal
			MealEntry newMealEntry = new MealEntry();
			newMealEntry.setImageURL(imageURL);
			newMealEntry.setFilename(imageFileName);
			newMealEntry.setTitle(mealTitle);
			newMealEntry.setFeeling(feeling_);
			newMealEntry.setTrackScore(trackScore_);
			newMealEntry.setTimeStamp(timeStamp_);
			//newMealEntry.setAuthor();
			//newMealEntry.setGoal();
			meRepo.saveAndFlush(newMealEntry);

			return true;
		}
		catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	private FeelingEnum getFeelingEnumVal(String feeling) {
		if (feeling.equalsIgnoreCase("crying")) {
			return FeelingEnum.CRYING;
		}
		if (feeling.equalsIgnoreCase("pensive")) {
			return FeelingEnum.PENSIVE;
		}
		if (feeling.equalsIgnoreCase("happy")) {
			return FeelingEnum.HAPPY;
		}
		if (feeling.equalsIgnoreCase("joyful")) {
			return FeelingEnum.JOYFUL;
		}
		else {
			return null;
		}
	}
}
