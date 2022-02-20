package com.example.adproject.security;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.adproject.model.User;
import com.example.adproject.service.UserNotFoundException;
import com.example.adproject.service.UserService;

import net.bytebuddy.utility.RandomString;

@Controller
public class ForgotPasswordController {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private UserService uService;

	@GetMapping("/forgot_password")
	public String loadForgotPasswordForm() {
		return "forgot_password_form";
	}

	@PostMapping("/forgot_password")
	public String processForgotPassword(HttpServletRequest request, Model model) {
		String email = request.getParameter("email");
		String token = RandomString.make(30);

		try {
			uService.updateResetPasswordToken(token, email);
			String resetPasswordLink = Utility.getSiteURL(request) + "/reset_password?token=" + token;
			sendEmail(email, resetPasswordLink);
			model.addAttribute("message", "Reset password link has been sent to email. Please check.");
		} catch (UserNotFoundException e) {
			model.addAttribute("error", e.getMessage()); 
		} catch (UnsupportedEncodingException | MessagingException e) {
			model.addAttribute("error", "Error while sending email"); 
		}
		
		return "forgot_password_form";
	}

	private void sendEmail(String user_email, String resetPasswordLink)
			throws MessagingException, UnsupportedEncodingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom("contact@fooddiary.com", "Food Diary App Support");
		helper.setTo(user_email);

		String subject = "Reset your password";

		String content = "<p>Hi there,</p> "
				+"<p>We've received your request to reset your password.</p>"
				+ "<p>Please click on the link below to reset your password:</p>" 
				+ "<p><a href=\"" + resetPasswordLink + "\">Reset Password here</a></p>" + "<br>"
				+ "<p>Please ignore this email if you did not make this request.</p>";

		helper.setSubject(subject);

		helper.setText(content, true);

		mailSender.send(message);

	}

	@GetMapping("/reset_password")
	public String showResetPasswordForm(@Param(value = "token")String token, Model model) {
		User user = uService.getByResetPasswordToken(token); 
		model.addAttribute("token", token); 
		
		if (user == null) {
			model.addAttribute("message", "Invalid Token"); 
			return "message"; 
		}
		
		return "reset_password_form"; 
	}

	@PostMapping("/reset_password")
	public String processResetPassword(HttpServletRequest request, Model model) {
		String token = request.getParameter("token");
		String password = request.getParameter("password"); 
		
		User user = uService.getByResetPasswordToken(token);
		model.addAttribute("title", "Reset your password"); 
		
		if (user == null) {
			model.addAttribute("message", "Invalid Token"); 
			return "message"; 
		} else {
			uService.updatePassword(user, password); 
			
			model.addAttribute("message", "Password successfully changed"); 
		}
		
		return "message"; 
	}
}
