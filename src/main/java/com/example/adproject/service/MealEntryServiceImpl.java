package com.example.adproject.service;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.example.adproject.model.MealEntry;
import com.example.adproject.model.Report;
import com.example.adproject.model.User;
import com.example.adproject.repo.MealEntryRepo;
import com.example.adproject.repo.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class MealEntryServiceImpl implements MealEntryService {

	@Autowired
	private MealEntryRepo repository;

	@Transactional
	public List<MealEntry> findEntryByAuthor(Integer userId){
		return repository.findEntryByAuthor(userId);
	}

	

    @Autowired
    MealEntryRepo mRepo;

    @Autowired
    UserRepo uRepo;

    @Autowired
    ReportService rService;

    @Autowired
    UserService uService;
    
    @Override
    public MealEntry findMealEntryById(Integer id) {
		return mRepo.findById(id).get();
	}

    @Override
    public void likeEntryByObject(User user, MealEntry mealEntry) {
        if(user == null || mealEntry == null) {
            return;
        }

        mealEntry.getLikers().add(user);

        mRepo.saveAndFlush(mealEntry);
    }

    
    @Override
    public void likeEntryById(Integer userId, Integer mealEntryId) {
        User user = uRepo.findById(userId).get();
        MealEntry mealEntry = mRepo.findById(mealEntryId).get();

        if(user == null || mealEntry == null) {
            return;
        }
      
        likeEntryByObject(user, mealEntry);

        
    }

    @Override
    public void unlikeEntryByObject(User user, MealEntry mealEntry) {
        if(user == null || mealEntry == null) {
            return;
        }

        mealEntry.getLikers().remove(user);

        mRepo.saveAndFlush(mealEntry);
        
        
    }

    @Override
    public void unlikeEntryById(Integer userId, Integer mealEntryId) {
        User user = uRepo.findById(userId).get();
        MealEntry mealEntry = mRepo.findById(mealEntryId).get();

        if(user == null || mealEntry == null) {
            return;
        }
        unlikeEntryByObject(user, mealEntry);
        
    }


    @Override
    public List<User> getLikersById(Integer mealEntryId) {
        MealEntry mealEntry = mRepo.findById(mealEntryId).get();

        if (mealEntry == null) {
            return null;
        }

        return mealEntry.getLikers();

        

        
    }


    @Override
    public void removeAllLikesById(Integer mealEntryId) {
        MealEntry mealEntry = mRepo.findById(mealEntryId).get();

        if (mealEntry == null) {
            return;
        }

        mealEntry.getLikers().clear();
        mRepo.saveAndFlush(mealEntry);
        
    }


    @Override
    public Integer getTotalNumberOfLikesById(Integer mealEntryId) {
        MealEntry mealEntry = mRepo.findById(mealEntryId).get();

        if (mealEntry == null) {
            return -1;
        }

       
        return mealEntry.getLikers().size();
    }


    @Override
    public Boolean hasUserLikedThis(Integer userId, Integer mealEntryId) {
        List<User> likers = getLikersById(mealEntryId);

        for(User liker : likers) {
            if(liker.getId() == userId){
                return true;
            }
        }

        return false;
    }


    @Override
    public Boolean hasUserFlaggedThis(Integer userId, Integer mealEntryId) {
        List<Report> reports = rService.getReportsByMealEntryId(mealEntryId);
        User reporter = uRepo.findById(userId).get();
        if (reports == null || reporter == null) {
            return false;
        }
        for(Report report : reports) {
            if(report.getReporter().equals(reporter)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public List<MealEntry>  getAllVisibleFriendEntries(Integer userId) {
        // Probably not efficient as I need to query for all entries of all friends
        // might be good to search by day instead?
        User user = uRepo.findById(userId).get();
        if (user == null) {
            return null;
        }

        List<User> friends = uService.findFriendsOf(user);
        if(friends == null) {
            return null;
        }

        ArrayList<MealEntry> mealEntries = new ArrayList<MealEntry>();

        for(User friend : friends) {
            List<MealEntry> friendEntries = mRepo.findVisibleMealEntryByAuthor(friend);

            if(friendEntries != null) {
                mealEntries.addAll(friendEntries);
            }

        }


        return mealEntries;
    }


    @Override
    public List<MealEntry> getVisibleMealEntryByUserId(Integer userId) {
        User author = uRepo.findById(userId).get();

        if(author == null) {
            return null;
        }



        return mRepo.findVisibleMealEntryByAuthor(author);
    }


    @Override
    public List<MealEntry> getMealEntryForFeedByPage(Integer userId, Integer pageNo, Integer pageLength) {
        
        List<MealEntry> visibleFriendMealEntries = getAllVisibleFriendEntries(userId);

        if(visibleFriendMealEntries == null) {
            return null;
        }

        return visibleFriendMealEntries .stream()
                                        .sorted(Comparator.comparing(MealEntry::getTimeStamp).reversed())
                                        .skip(pageLength * pageNo)
                                        .limit(pageLength)
                                        .collect(Collectors.toList());
                              


        


    }

    


  


    
    

}
