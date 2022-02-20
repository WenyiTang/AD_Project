package com.example.adproject.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.example.adproject.model.Goal;
import com.example.adproject.model.MealEntry;
import com.example.adproject.model.User;
import com.example.adproject.repo.GoalRepo;
import com.example.adproject.repo.MealEntryRepo;
import com.example.adproject.repo.UserRepo;
import com.example.adproject.service.GoalService;
import com.example.adproject.validator.ProfileValidator;

@Controller
@RequestMapping(value = "/user")

public class userController {

	@Autowired
	private com.example.adproject.service.UserService uService;

	@Autowired
	private GoalService gService;

	@Autowired
	UserRepo urepo;

	@Autowired
	GoalRepo grepo;

	@Autowired
	MealEntryRepo mRepo;

	@Autowired
	private ProfileValidator pValidator;

	@InitBinder("user")
	private void initRoleBinder(WebDataBinder binder) {
		binder.addValidators(pValidator);
	}

	@RequestMapping(value = "/myProfile/{id}", method = RequestMethod.GET)
	public ModelAndView editUserPage(Principal principal) {
		ModelAndView mav = new ModelAndView("staff-profile-edit");
		Integer userId = uService.findUserByUsername(principal.getName()).getId();
		User user = uService.findUser(userId);
		mav.addObject("user", user);
		return mav;
	}

	@RequestMapping(value = "/myProfile/{id}", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView editProfile(@ModelAttribute @Validated User user, BindingResult result, Principal principal)
			throws IOException {

		if (result.hasErrors()) {
			ModelAndView mav_error = new ModelAndView();
			String message_error = "Profile edit failed. Please change the information.";
			mav_error.addObject("message_error", message_error);
			mav_error.setViewName("staff-profile-edit");
			return mav_error;
		}

		ModelAndView mav = new ModelAndView();
		mav.addObject("user", user);

		Integer userId = uService.findUserByUsername(principal.getName()).getId();
		User updateUser = urepo.findById(userId).get();
		updateUser.setName(user.getName());
		updateUser.setDateOfBirth(user.getDateOfBirth());
		updateUser.setHeight(user.getHeight());
		updateUser.setWeight(user.getWeight());
		updateUser.setGender(user.getGender());

		urepo.saveAndFlush(updateUser);

		String message = "User was successfully updated.";
		System.out.println(message);

		mav.setViewName("redirect:/user/myProfile");
		return mav;
	}

	// change photo
	@RequestMapping(value = "/myProfile/photo/{id}", method = RequestMethod.GET)
	public ModelAndView editUserPhoto(Principal principal) {
		ModelAndView mav = new ModelAndView("profile-avatar");
		Integer userId = uService.findUserByUsername(principal.getName()).getId();
		User user = uService.findUser(userId);
		mav.addObject("user", user);
		return mav;
	}

	@RequestMapping(value = "/myProfile/photo/{id}", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView editProfilePhoto(@ModelAttribute @Validated User user, BindingResult result,
			@RequestParam("fileImage") MultipartFile multipartFile, Principal principal) throws IOException {

		ModelAndView mav = new ModelAndView();
		mav.addObject("user", user);

		// User's profile photo
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		user.setProfilePic(fileName);

		Integer userId = uService.findUserByUsername(principal.getName()).getId();
		User updateUser = urepo.findById(userId).get();
		updateUser.setProfilePic(fileName);
		urepo.saveAndFlush(updateUser);
		String uploadDir = "./images/" + updateUser.getId();

		Path uploadPath = Paths.get(uploadDir);

		// Saving user's profile pic into directory
		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}

		try {
			File profileImg = new File("images/" + updateUser.getId() + "/" + fileName);
			profileImg.createNewFile();
			FileOutputStream fout = new FileOutputStream(profileImg);
			fout.write(multipartFile.getBytes());
			fout.close();
		} catch (IOException e) {
			throw new IOException("Could not save uploaded file: " + fileName);
		}

		String message = "User photo was successfully updated.";
		System.out.println(message);

		mav.setViewName("redirect:/user/myProfile");
		return mav;
	}

	@RequestMapping(value = "/myProfile", method = RequestMethod.GET)
	public ModelAndView ViewMyProfile(@ModelAttribute User user, Principal principal) {
		Integer userId = uService.findUserByUsername(principal.getName()).getId();
//		UserSession usession = (UserSession) session.getAttribute("usession");
		ModelAndView mav = new ModelAndView("profile-view");
		user = uService.findUser(userId);
		List<Goal> completedGoal = grepo.findCompletedGoals(userId);

		// Calculate BMI
		double height = user.getHeight();
		double weight = user.getWeight();
		double bmiFloat = weight / (height * height / 10000);
		String bmi = String.format("%.1f", bmiFloat);

		mav.addObject("user", user);
		mav.addObject("bmi", bmi);
		mav.addObject("completedGoal", completedGoal);
		return mav;
	}

	// View current goal progress
	@RequestMapping(value = "/goals")
	public String goals(Model model, Principal principal) {

		Integer userId = uService.findUserByUsername(principal.getName()).getId();
		Goal currentgoal = grepo.findCurrentGoal(userId);
		long countOnT = mRepo.findIPEntryByAuthor(userId).stream().filter(x -> x.getTrackScore() == 1).count();
		long countOffT = mRepo.findIPEntryByAuthor(userId).stream().filter(x -> x.getTrackScore() == 0).count();

		long totalmeals = countOnT + countOffT;
		if(totalmeals ==0){
			return "./goal/goal-progress";
		}else
		{
			double percentCount = (countOnT * 100 / totalmeals);
			String percentCount1 = String.format("%.1f", percentCount);

			List<MealEntry> entries = mRepo.findVisibleMealEntryByAuthor(uService.findUserByUsername(principal.getName()));
			Collections.reverse(entries);
			model.addAttribute("entries", entries);
			model.addAttribute("onTrack", countOnT);
			model.addAttribute("offTrack", countOffT);
			model.addAttribute("totalMeals", totalmeals);
			model.addAttribute("percentCount", percentCount1);
			model.addAttribute("goal", currentgoal);
			return "./goal/goal-progress";
		}
		
	}

	// Save&Continue end of a goal
	@RequestMapping(value = "/goals/continue")
	public String Continue(Principal principal, Model model) {
		Integer userId = uService.findUserByUsername(principal.getName()).getId();
		Goal goaltoEnd = grepo.findCurrentGoal(userId);
		gService.cancelGoal(goaltoEnd);

		String msg = "Leave was successfully cancelled.";
		System.out.println(msg);
		return "./goal/goal-progress";

	}

	// View Past Goal
	@RequestMapping(value = "/pastgoals")
	public String pastgoals(Model model, Principal principal) {

		Integer userId = uService.findUserByUsername(principal.getName()).getId();
		List<Goal> pastgoal = grepo.findPastGoals(userId);
		model.addAttribute("pastgoal", pastgoal);
		return "./goal/goal-completed";
	}

}
