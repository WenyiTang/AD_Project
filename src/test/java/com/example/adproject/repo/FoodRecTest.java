package com.example.adproject.repo;

import org.junit.jupiter.api.TestMethodOrder;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.adproject.AdProjectApplication;
import com.example.adproject.model.MealEntry;
import com.example.adproject.service.MealEntryService;
import com.example.adproject.service.MealEntryServiceImpl;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AdProjectApplication.class)
@TestMethodOrder(OrderAnnotation.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class FoodRecTest {

	@Autowired
	MealEntryService mService;

	@Test
	@Order(1)
	public void getMealEntryByUser() {
		List<MealEntry> m = mService.findMealEntryByUserId(1);
		System.out.println(m.size());
	}
}
