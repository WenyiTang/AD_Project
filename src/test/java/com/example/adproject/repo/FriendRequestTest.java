package com.example.adproject.repo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;

import com.example.adproject.AdProjectApplication;
import com.example.adproject.model.FriendRequest;
import com.example.adproject.model.User;
import com.example.adproject.service.FriendRequestService;
import com.example.adproject.service.UserService;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AdProjectApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FriendRequestTest {
    @Autowired
	private UserRepo uRepo;

	@Autowired
	private FriendRequestRepo fRepo;

    @Autowired
    private UserService uService;

	@Autowired
	private FriendRequestService fService;

	/*
	 * @Test
	 * 
	 * @Order(1) void testInsertUsers(){ fRepo.deleteAll(); uRepo.deleteAll();
	 * ArrayList<User> users = new ArrayList<User>(); String[] friends =
	 * {"monica","phoebe","rachel","ross","chandler","joey"};
	 * 
	 * for (String friend : friends){ users.add(new User(friend,friend)); }
	 * 
	 * uRepo.saveAll(users);
	 * 
	 * List<User> insertedUsers = uRepo.findAll();
	 * assertEquals(insertedUsers.size(), users.size());
	 * 
	 * 
	 * }
	 * 
	 * @Test
	 * 
	 * @Order(2) void testSendRequestToSelf() { fRepo.deleteAll(); User sender =
	 * uRepo.findByUsername("joey"); User recipient = uRepo.findByUsername("joey");
	 * 
	 * 
	 * 
	 * fService.sendRequest(sender, recipient);
	 * 
	 * List<FriendRequest> requests = fRepo.findAll(); assertEquals(0,
	 * requests.size()); }
	 * 
	 * 
	 * 
	 * 
	 * 
	 * @Test
	 * 
	 * @Order(3) void testSendFriendRequest() { fRepo.deleteAll(); User sender =
	 * uRepo.findByUsername("chandler"); User recipient =
	 * uRepo.findByUsername("monica");
	 * 
	 * 
	 * if(!fService.sendRequest(sender, recipient)){ fail("sendRequest failed"); }
	 * 
	 * 
	 * List<FriendRequest> pendingRequests =
	 * fRepo.findPendingRequestsByRecipient(recipient);
	 * 
	 * assertEquals(1, pendingRequests.size());
	 * assertEquals(sender.getId(),pendingRequests.get(0).getSender().getId());
	 * assertEquals(recipient.getId(),
	 * pendingRequests.get(0).getRecipient().getId()); }
	 * 
	 * @Test
	 * 
	 * @Order(4) void testSendRepeatedRequests(){ fRepo.deleteAll(); User sender =
	 * uRepo.findByUsername("chandler"); User recipient =
	 * uRepo.findByUsername("monica"); fService.sendRequest(sender, recipient);
	 * 
	 * if(fService.sendRequest(sender, recipient)){
	 * fail("sender could send repeated request"); } else if
	 * (fService.sendRequest(recipient, sender)) {
	 * fail("recipient could send request to sender"); }
	 * 
	 * 
	 * }
	 * 
	 * 
	 * @Test
	 * 
	 * @Order(5) void testAcceptRequest() { User recipient =
	 * uRepo.findByUsername("monica");
	 * 
	 * List<FriendRequest> pendingRequests =
	 * fRepo.findPendingRequestsByRecipient(recipient); assertEquals(1,
	 * pendingRequests.size());
	 * 
	 * fService.acceptRequest(pendingRequests.get(0).getId()); User sender =
	 * pendingRequests.get(0).getSender();
	 * 
	 * List<FriendRequest> monicaAcceptedRequests =
	 * fRepo.findAcceptedRequestsByUser(recipient); List<FriendRequest>
	 * chandlerAcceptedRequests = fRepo.findAcceptedRequestsByUser(sender);
	 * 
	 * assertEquals(monicaAcceptedRequests.get(0).getId(),
	 * chandlerAcceptedRequests.get(0).getId());
	 * 
	 * }
	 * 
	 * @Test
	 * 
	 * @Order(6) void testSendMultipleRequests() {
	 * 
	 * List<User> users = uRepo.findAll(); User sender = users.get(0); // monica
	 * 
	 * for(int i = 1; i < users.size(); i++){ fService.sendRequest(sender,
	 * users.get(i)); }
	 * 
	 * List<FriendRequest> monicaRequests = fRepo.findRequestsByUser(sender);
	 * 
	 * assertEquals(users.size()-1, monicaRequests.size()); }
	 * 
	 * @Test
	 * 
	 * @Order(7) void testFindFriends() { User phoebe =
	 * uRepo.findByUsername("phoebe"); User ross = uRepo.findByUsername("ross");
	 * User joey = uRepo.findByUsername("joey");
	 * 
	 * ArrayList<User> users = new ArrayList<User>(); users.add(phoebe);
	 * users.add(ross); users.add(joey);
	 * 
	 * 
	 * // Accept monica's friend requests for (User user : users){
	 * List<FriendRequest> pendingRequests =
	 * fRepo.findPendingRequestsByRecipient(user);
	 * fService.acceptRequest(pendingRequests.get(0).getId()); }
	 * 
	 * User monica = uRepo.findByUsername("monica");
	 * 
	 * List<User> friendsOfMonica = uService.findFriendsOf(monica);
	 * System.out.println("Friends of monica:"); for(User friend : friendsOfMonica)
	 * { System.out.println(friend.getUsername()); }
	 * 
	 * assertEquals(4,friendsOfMonica.size()); }
	 * 
	 * @Test
	 * 
	 * @Order(8) void testRejectRequest(){ // At this stage, chandler, phoebe, joey
	 * and ross are friends with monica // monica's friend request to rachel is
	 * still pending // rachel's gonna reject monica's friend request
	 * 
	 * User rachel = uRepo.findByUsername("rachel"); List<FriendRequest>
	 * rachelPendingRequests = fRepo.findPendingRequestsByRecipient(rachel);
	 * fService.rejectRequest(rachelPendingRequests.get(0));
	 * 
	 * rachelPendingRequests = fRepo.findPendingRequestsByRecipient(rachel);
	 * 
	 * assertEquals(0, rachelPendingRequests.size());
	 * 
	 * 
	 * } //Uncomment and run the test below to clear the FriendRequest and User
	 * table //@Test
	 * 
	 * @Order(9) void clearFriendRequestAndUserTables() { fRepo.deleteAll();
	 * uRepo.deleteAll(); }
	 */
    
    @Test
    @Order(1)
    void testGetFriends() {
    	String username = "jill"; 
    	User u = uService.findUserByUsername(username);
    	List<User> friends = uService.findFriendsOf(u);
    	
    	assertEquals(5, friends.size());
    }

	@Test
	@Order(2)
	void testUsernameContains() {
		String username = "a";
		List<User> u = uRepo.findUserWithUsernameLike(username);
		assertEquals(6,u.size());
	}

	@Test
	@Order(3)
	void testFindPendingUsersByUser() {
		User u = uRepo.findByUsername("jill");
		List<User> users = fService.findPendingUsersByUser(u);
		assertEquals(1, users.size());
	}

	@Test
	@Order(4)
	void testFindRequest() {
		User sender = uRepo.findByUsername("peter");
		User recipient = uRepo.findByUsername("jill");
		FriendRequest fr = fService.findRequest(sender, recipient);
		assertEquals(7, fr.getId());
	}
}
