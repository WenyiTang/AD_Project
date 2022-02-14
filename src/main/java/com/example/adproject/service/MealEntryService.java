package com.example.adproject.service;

import java.util.List;

import com.example.adproject.model.MealEntry;
import com.example.adproject.model.User;

import org.springframework.stereotype.Component;

@Component
public interface MealEntryService {
	
//	@Autowired
//	private MealEntryRepo repository;
//	
//	public List<MealEntry> getAllEntry(){
//		return repository.findAll();
//	}

	public List<MealEntry> findEntryByAuthor(Integer userId);
	
	public MealEntry findMealEntryById(Integer id);
	
	public List<MealEntry> findMealEntryByUser(User user);

    public void likeEntryByObject(User user, MealEntry mealEntry);

    public void likeEntryById(Integer userId, Integer mealEntryId);

    public void unlikeEntryByObject(User user, MealEntry mealEntry);

    public void unlikeEntryById(Integer userId, Integer mealEntryId);

    public List<User> getLikersById(Integer mealEntryId);

    public void removeAllLikesById(Integer mealEntryId);

    public Integer getTotalNumberOfLikesById(Integer mealEntryId);

    public Boolean hasUserLikedThis(Integer userId, Integer mealEntryId);

    public Boolean hasUserFlaggedThis(Integer userId, Integer mealEntryId);

    public List<MealEntry> getAllVisibleFriendEntries(Integer userId);

    public List<MealEntry> getVisibleMealEntryByUserId(Integer userId);

    public List<MealEntry> getMealEntryForFeedByPage(Integer userId, Integer pageNo, Integer pageLength);

 


    

}
