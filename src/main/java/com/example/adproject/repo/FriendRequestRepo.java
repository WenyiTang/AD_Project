package com.example.adproject.repo;

import java.util.List;

import com.example.adproject.model.FriendRequest;
import com.example.adproject.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

@Component
public interface FriendRequestRepo extends JpaRepository<FriendRequest,Integer> {
    @Query ("SELECT f FROM FriendRequest f WHERE f.status = 'PENDING'")
    List<FriendRequest> findPendingRequests();

    @Query ("SELECT f FROM FriendRequest f WHERE f.status = 'PENDING' AND f.sender = :sender")
    List<FriendRequest> findPendingRequestsBySender(@Param("sender") User sender );

    @Query ("SELECT f FROM FriendRequest f WHERE f.status = 'PENDING' AND f.recipient = :recipient")
    List<FriendRequest> findPendingRequestsByRecipient(@Param("recipient") User recipient );

    @Query("SELECT f FROM FriendRequest f WHERE f.status = 'ACCEPTED' AND (f.recipient = :user OR f.sender = :user)" )
    List<FriendRequest> findAcceptedRequestsByUser(@Param("user") User user);

    @Query("SELECT f FROM FriendRequest f WHERE f.sender = :user OR f.recipient = :user")
    List<FriendRequest> findRequestsByUser(@Param("user") User user);

    @Query("SELECT f FROM FriendRequest f WHERE (f.sender = :firstUser AND f.recipient = :secondUser) OR (f.sender = :secondUser AND f.recipient = :firstUser)")
    List<FriendRequest> findExistingRequests(@Param("firstUser") User firstUser,@Param("secondUser") User secondUser);

    @Query("SELECT f FROM FriendRequest f WHERE f.status = 'ACCEPTED' AND (f.sender = :firstUser AND f.recipient = :secondUser) OR (f.sender = :secondUser AND f.recipient = :firstUser)")
    FriendRequest findAcceptedRequestByUsers(@Param("firstUser") User firstUser, @Param("secondUser") User secondUser);
}
