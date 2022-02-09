package com.example.adproject.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.adproject.helper.ReportEnum;
import com.example.adproject.helper.ReportOutcome;
import com.example.adproject.model.MealEntry;
import com.example.adproject.model.Report;
import com.example.adproject.model.User;
import com.example.adproject.service.MealEntryService;
import com.example.adproject.service.ReportService;
import com.example.adproject.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminReportController {
	
	@Autowired
	ReportService rService;
	@Autowired
	MealEntryService mService;
	@Autowired
	UserService uService;
	
	private Integer reportCount;
	
	@RequestMapping("/")
	public String adminHome(Model model) {
		model.addAttribute("reportCount", reportCount);
		return "index";
	}
	
	@RequestMapping("/pendingreports")
	public String pendingReports(Model model, Principal principal) {
		User admin = uService.findUserByUsername(principal.getName());
		List<Report> reports = rService.findPendingNProgressReports(admin);
		reports.sort(Comparator.comparing(Report::getStatus)
				.thenComparing(Report::getDateReported));
		model.addAttribute("pendingReports", reports);
		reportCount = rService.findPendingNProgressReports(admin).size();
		model.addAttribute("reportCount", reportCount);
		return "./admin/pending-reports";
	}
	
	@GetMapping("/resolvereport/{rid}")
	public String resolveReport(@PathVariable("rid") Integer reportId, Model model, Principal principal) {
		Report report = rService.findReportById(reportId);
		User admin = uService.findUserByUsername(principal.getName());
		model.addAttribute("report", report);
		ReportOutcome ro = new ReportOutcome();
		model.addAttribute("outcome", ro);
		reportCount = rService.findPendingNProgressReports(admin).size();
		model.addAttribute("reportCount", reportCount);
		Integer entryId = report.getMealEntry().getId();
		String reason = report.getReason();
		List<Report> reportsOfSameId = rService.findPendingNProgressReportsIdReason(entryId, reason);
		for (Report r : reportsOfSameId) {
			r.setStatus(ReportEnum.IN_PROGRESS);
			r.setResolvedBy(admin);
			rService.save(r);
		}
		return "./admin/resolve-report";
	}
	
	@GetMapping("/cancelresolve/{rid}")
	public String cancelResolve(@PathVariable("rid") Integer reportId, Model model, Principal principal) {
		Report report = rService.findReportById(reportId);
		User admin = uService.findUserByUsername(principal.getName());
		reportCount = rService.findPendingNProgressReports(admin).size();
		model.addAttribute("reportCount", reportCount);
		Integer entryId = report.getMealEntry().getId();
		String reason = report.getReason();
		List<Report> reportsOfSameId = rService.findPendingNProgressReportsIdReason(entryId, reason);
		for (Report r : reportsOfSameId) {
			r.setStatus(ReportEnum.PENDING);
			r.setResolvedBy(null);
			rService.save(r);
		}
		return "redirect:/admin/pendingreports";
	}
	
	@RequestMapping(value = "/submitresolvedecision/{rid}", method = RequestMethod.POST)
	public String resolveReportDecision(@PathVariable("rid") Integer reportId, Principal principal, @ModelAttribute("outcome") @Valid ReportOutcome outcome, 
			BindingResult result, Model model) {
		Report report = rService.findReportById(reportId);
		if (result.hasErrors()) {
			model.addAttribute("report", report);
			model.addAttribute("outcome", outcome);
			model.addAttribute("reportCount", reportCount);
			return "./admin/resolve-report";
		}
		MealEntry mealEntry = report.getMealEntry();
		if (outcome.getInappropriate() == true) {
			mealEntry.setVisibility(false);
			report.setComments(outcome.getComments());
		}
		report.setDateResolved(LocalDate.now());
		report.setStatus(ReportEnum.RESOLVED);
		User admin = uService.findUserByUsername(principal.getName());
		report.setResolvedBy(admin);
		rService.save(report);
		
		//resolve reports with same mealEntry id and reason
		Integer entryId = report.getMealEntry().getId();
		String reason = report.getReason();
		List<Report> reportsOfSameId = rService.findPendingNProgressReportsIdReason(entryId, reason);
		for (Report r : reportsOfSameId) {
			r.setDateResolved(LocalDate.now());
			r.setStatus(ReportEnum.RESOLVED);
			r.setResolvedBy(admin);
			rService.save(r);
		}
		return "redirect:/admin/pendingreports";
	}
}
