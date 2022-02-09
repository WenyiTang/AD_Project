package com.example.adproject.repo;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

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
import com.example.adproject.model.MealEntry;
import com.example.adproject.model.Report;
import com.example.adproject.model.User;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AdProjectApplication.class)
@TestMethodOrder(OrderAnnotation.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AdminReportTest {
	
	@Autowired
	private ReportRepo rrepo;
	
//	@Test
//	@Order(1)
//	public void test() {
//		List<Report> reports = rrepo.findPendingNProgressReports();
//		reports.sort(Comparator.comparing(Report::getStatus)
//				.thenComparing(Report::getDateReported));
//		for (Report r : reports) {
//			System.out.println(r.getStatus() + " " + r.getDateReported());
//		}
//	}
	
//	@Autowired
//	private AdminRepo arepo;
//	@Autowired
//	private ReportRepo rrepo;
//	@Autowired
//	private MealEntryRepo mrepo;
//	@Autowired
//	private UserRepo urepo;
//	
//	@Test
//	@Order(1)
//	public void createAdmin() {
//		Admin a1 = new Admin();
//		a1.setName("Patrick");
//		arepo.save(a1);
//		
//		Admin a2 = new Admin();
//		a2.setName("Spongebob");
//		arepo.save(a2);
//	}
//	
//	@Test
//	@Order(2)
//	public void createReport() {
//		User u1 = new User();
//		urepo.save(u1);
//		User u2 = new User();
//		urepo.save(u2);
//		MealEntry m1 = new MealEntry();
//		mrepo.save(m1);
//		MealEntry m2 = new MealEntry();
//		mrepo.save(m2);
//		Report r1 = new Report();
//		r1.setReporter(u1);
//		r1.setMealEntry(m1);
//		r1.setStatus(ReportEnum.PENDING);
//		r1.setDateReported(LocalDate.now());
//		rrepo.save(r1);
//		Report r2 = new Report();
//		r2.setReporter(u2);
//		r2.setMealEntry(m2);
//		r2.setStatus(ReportEnum.PENDING);
//		r2.setDateReported(LocalDate.now());
//		rrepo.save(r2);
//	}
	
//	@Test
//	@Order(3)
//	public void resolveReportInappropriate() {
//		Report myreport = rrepo.findById(1).get();
//		Admin me = arepo.findById(2).get();
//		myreport.setComments("inappropriate");
//		myreport.setStatus(ReportEnum.RESOLVED);
//		myreport.setDateResolved(LocalDate.now());
//		myreport.setResolvedBy(me);
//		rrepo.save(myreport);
//	}
//	
//	@Test
//	@Order(4)
//	public void resolveReportOK() {
//		Report myreport = rrepo.findById(2).get();
//		Admin me = arepo.findById(1).get();
//		myreport.setStatus(ReportEnum.RESOLVED);
//		myreport.setDateResolved(LocalDate.now());
//		myreport.setResolvedBy(me);
//		rrepo.save(myreport);
//	}
}
