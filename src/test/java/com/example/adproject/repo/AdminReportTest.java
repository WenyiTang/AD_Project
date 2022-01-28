package com.example.adproject.repo;

import java.time.LocalDate;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.adproject.AdProjectApplication;
import com.example.adproject.helper.ReportEnum;
import com.example.adproject.model.Admin;
import com.example.adproject.model.MealEntry;
import com.example.adproject.model.Report;
import com.example.adproject.model.User;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AdProjectApplication.class)
@TestMethodOrder(OrderAnnotation.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AdminReportTest {
	
	@Autowired
	private AdminRepo arepo;
	@Autowired
	private ReportRepo rrepo;
	@Autowired
	private MealEntryRepo mrepo;
	@Autowired
	private UserRepo urepo;
	
	@Test
	@Order(1)
	public void createAdmin() {
		Admin a1 = new Admin();
		a1.setName("Patrick");
		arepo.save(a1);
		
		Admin a2 = new Admin();
		a2.setName("Spongebob");
		arepo.save(a2);
	}
	
	@Test
	@Order(2)
	public void createReport() {
		User u1 = new User();
		urepo.save(u1);
		User u2 = new User();
		urepo.save(u2);
		MealEntry m1 = new MealEntry();
		mrepo.save(m1);
		MealEntry m2 = new MealEntry();
		mrepo.save(m2);
		Report r1 = new Report();
		r1.setReporter(u1);
		r1.setMealEntry(m1);
		r1.setStatus(ReportEnum.PENDING);
		r1.setDateReported(LocalDate.now());
		rrepo.save(r1);
		Report r2 = new Report();
		r2.setReporter(u2);
		r2.setMealEntry(m2);
		r2.setStatus(ReportEnum.PENDING);
		r2.setDateReported(LocalDate.now());
		rrepo.save(r2);
	}
	
	@Test
	@Order(3)
	public void resolveReportInappropriate() {
		Report myreport = rrepo.findReportById(1);
		Admin me = arepo.findAdminById(2);
		myreport.setComments("inappropriate");
		myreport.setStatus(ReportEnum.RESOLVED);
		myreport.setDateResolved(LocalDate.now());
		myreport.setResolvedBy(me);
		rrepo.save(myreport);
	}
	
	@Test
	@Order(4)
	public void resolveReportOK() {
		Report myreport = rrepo.findReportById(2);
		Admin me = arepo.findAdminById(1);
		myreport.setStatus(ReportEnum.RESOLVED);
		myreport.setDateResolved(LocalDate.now());
		myreport.setResolvedBy(me);
		rrepo.save(myreport);
	}
}
