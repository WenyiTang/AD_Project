package com.example.adproject.controller;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.adproject.highchart.EmployeeService;
import com.example.adproject.model.MealEntry;
import com.example.adproject.repo.MealEntryRepo;
import com.example.adproject.service.MealEntryService;

@Controller
public class HomeController {
	

	
	@GetMapping("/home")
	public String PieChart(Model model) {
		model.addAttribute("OnTrack",90);
		model.addAttribute("OffTrack",10);

        return "home";
    }
	
	@GetMapping("/dashboard")
	public String multipleChart() {

        return "dashboard";
    }
	
	@Autowired
	private MealEntryService service;
	
	@GetMapping("/barChart")
	public String getAllEmployee(Model model) {
		//List<String> nameList = service.getAllEmployee().stream().map(x->x.getName()).collect(Collectors.toList());
		//List<Integer> ageList = service.getAllEmployee().stream().map(x->x.getAge()).collect(Collectors.toList());
		
		long countOnT= service.getAllEntry().stream()
							.filter(x-> x.getTrackScore() == 1)
							.count();
		
		long countOffT= service.getAllEntry().stream()
				.filter(x-> x.getTrackScore() == 0)
				.count();
		
		//model.addAttribute("name", nameList);
		//model.addAttribute("age", ageList);
		model.addAttribute("onTrack", countOnT);
		model.addAttribute("offTrack", countOffT);
		return "barChart";
	}
	

}
