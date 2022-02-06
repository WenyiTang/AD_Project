package com.example.adproject.viewBlog;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import com.example.adproject.AdProjectApplication;
import com.example.adproject.model.Report;
import com.example.adproject.repo.ReportRepo;
import com.example.adproject.service.ReportService;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AdProjectApplication.class)
@TestMethodOrder(OrderAnnotation.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ReportTest {

    @Autowired
    ReportService rService;

    @Autowired
    ReportRepo rRepo;

    @Test
    @Order(1)
    void testSubmitReport() {
        rRepo.deleteAll();
        Integer userId = 1;
        Integer mealEntryId = 1;
        String reason = "offensive language";

        rService.submitReport(userId, mealEntryId, reason);


        List<Report> reports = rService.getReportsByMealEntryId(mealEntryId);

        assertEquals(reports.size(),1);
        
    }

    @Test
    @Order(2)
    void testDeleteReportsByMealEntryId() {
        Integer mealEntryId = 1;
        rService.deleteReportsByMealEntryId(mealEntryId);
        List<Report> reports = rService.getReportsByMealEntryId(mealEntryId);

        assertEquals(reports.size(),0);



    }

    
}
