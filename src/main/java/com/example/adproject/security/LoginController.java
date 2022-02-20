package com.example.adproject.security;

import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.List;
import java.util.Set;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.adproject.model.Goal;
import com.example.adproject.model.MealEntry;
import com.example.adproject.model.Role;
import com.example.adproject.model.User;
import com.example.adproject.repo.GoalRepo;
import com.example.adproject.repo.MealEntryRepo;
import com.example.adproject.service.ReportService;
import com.example.adproject.service.UserService;

@Controller
public class LoginController {
	
	
	@Autowired
	private GoalRepo gRepo;
	
	@Autowired
	private MealEntryRepo mRepo;
	
	@Autowired
	private UserService uService;
	
	@Autowired
	ReportService rService;

	@Autowired
	OTPService otpService;

	@Autowired
	JavaMailSender mailSender;

	@GetMapping("/")
	public String viewHomePage(Model model, Principal principal) {
		
		Integer userId = uService.findUserByUsername(principal.getName()).getId();
		long countOnT = mRepo.findIPEntryByAuthor(userId).stream()
						.filter(x->x.getTrackScore()==1)
						.count();
		long countOffT = mRepo.findIPEntryByAuthor(userId).stream()
						.filter(x->x.getTrackScore() == 0)
						.count();
		long countJoy = mRepo.findIPEntryByAuthor(userId).stream()
				.filter(x->x.getFeeling().toString()== "JOY")
				.count();
		long countHappy = mRepo.findIPEntryByAuthor(userId).stream()
				.filter(x->x.getFeeling().toString()== "HAPPY")
				.count();
		long countPensive = mRepo.findIPEntryByAuthor(userId).stream()
				.filter(x->x.getFeeling().toString()== "PENSIVE")
				.count();
		long countCry = mRepo.findIPEntryByAuthor(userId).stream()
				.filter(x->x.getFeeling().toString()== "CRY")
				.count();

		
		Goal currentGoal= gRepo.findCurrentGoal(userId);
		List<Goal> completedGoal = gRepo.findCompletedGoals(userId);
		List<MealEntry> allEntries = mRepo.findIPEntryByAuthor(userId);
		

		model.addAttribute("onTrack", countOnT);
		model.addAttribute("offTrack", countOffT);
		model.addAttribute("currentGoal", currentGoal);
		model.addAttribute("completedGoal", completedGoal);
		model.addAttribute("allEntries", allEntries);

		model.addAttribute("joy", countJoy);
		model.addAttribute("happy", countHappy);
		model.addAttribute("pensive", countPensive);
		model.addAttribute("cry", countCry);
		
		//if user is admin
		User loggedin = uService.findUserByUsername(principal.getName());
		Set<Role> roles = loggedin.getRoles();
		for (Role r : roles) {
			if (r.getType().equalsIgnoreCase("ADMIN")) {
//				Integer reportCount = rService.findPendingNProgressReports(loggedin).size();
//				model.addAttribute("reportCount", reportCount);
//				return "redirect:/admin/pendingreports";
				return "redirect:/generateOTP";
			}
		}

		model.addAttribute("title", "Food Diary - Home"); 
		return "index"; 
	}
	
	@GetMapping("/login")
	public String loadLoginPage() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); 
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			return "login"; 
		}
		
		return "redirect:/"; 
	}
	
	@GetMapping("/logout")
	public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null){    
	        new SecurityContextLogoutHandler().logout(request, response, auth);
	    }
	    return "redirect:/login?logout";
	}

	@GetMapping("/generateOTP")
	public String generateOTP(HttpServletRequest request, Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();

		String otp = otpService.generateOTP(username).toString();
		String user_email = uService.findUserByUsername(username).getEmail();

		try {
			sendEmail(user_email, otp);
		} catch (UnsupportedEncodingException | MessagingException e) {
			model.addAttribute("error", "Error while sending email");
			System.out.println("Error while sending email");
		}

		return "otp_page";
	}

	@PostMapping("/validateOTP")
	public String validateOtp(@RequestParam("otp") Integer otp, Model model, Principal principal) {
		final String FAIL = "Verification code is invalid";

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		User loggedin = uService.findUserByUsername(principal.getName());

		if (otp >= 0) {
			int serverOtp = otpService.getOtp(username);
			if (serverOtp > 0) {
				if (otp == serverOtp) {
					otpService.clearOtp(username);
					Integer reportCount = rService.findPendingNProgressReports(loggedin).size();
					model.addAttribute("reportCount", reportCount);
					return "redirect:/admin/pendingreports";
				} else {
					model.addAttribute("message", FAIL);
					return "otp_page";
				}
			} else {
				model.addAttribute("message", FAIL);
				return "otp_page";
			}
		}
		return "otp_page";
	}

	@RequestMapping("/redirect")
	public String redirectLogin(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null){
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/login";
	}

	private void sendEmail(String user_email, String otp)
			throws MessagingException, UnsupportedEncodingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom("contact@fooddiary.com", "Food Diary App Support");
		helper.setTo(user_email);

		String subject = "Food Diary App Verify Login ";

		String content = "<p>Hi there,</p> "
				+"<p>For authentication, please use the verification code below to login:</p>"
				+ "<p style=\"font-size:20px;\"><b>" + otp + "</b></p>" + "<br>"
				+ "<p>Please note OTP is only valid for 15 minutes</p>";

		helper.setSubject(subject);

		helper.setText(content, true);

		mailSender.send(message);
	}

}
