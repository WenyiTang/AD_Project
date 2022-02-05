package com.example.adproject.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.adproject.model.User;
import com.example.adproject.service.UserService;




@Controller
public class LoginController {
	
	
	@Autowired
	UserService uService;
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String getLoginForm() {
		return "login";
	}
	
	//check login credentials
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(@ModelAttribute(name="user") User user, Model model) {
		String username = user.getUsername();
		String password = user.getPassword();
		
		if ("admin".equals(username) && "admin".equals(password)) {
			return "home";
		}
		
		model.addAttribute("invalidCredentials",true);
		return"login";
	}

	
	@RequestMapping("/home")
	public String dashboard() {
		//User u = uService.authenticate(user.getName(), user.getPassword());
		//model.addAttribute("user", user);
		return "home";
			
	}
	
	//from cats
	
//	  @Autowired private UserService uService;
//	  
//	  
//	  @RequestMapping(value = "/") 
//	  public String home(Model model) {
//		  model.addAttribute("user", new User()); return "index"; }
//	  
//	  @RequestMapping(value = "/home") 
//	  public String logic(Model model) {
//		  model.addAttribute("user", new User()); return "login"; }
//	  
//	  @RequestMapping(value = "/login") 
//	  public String authenticate(@ModelAttribute("user") @Valid User user, BindingResult
//	  bindingResult, Model model, HttpSession session) { 
//		  UserSession usession = new UserSession(); 
//		  if (bindingResult.hasErrors()) { 
//			  return "login"; }
//		  else { 
//			  User u = uService.authenticate(user.getName(), user.getPassword());
//			  usession.setUser(u);
//	  
//			  session.setAttribute("usession", usession); return "home";
//		  }
//	  
//	  }


}
