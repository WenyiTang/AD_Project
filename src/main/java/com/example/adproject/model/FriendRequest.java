package com.example.adproject.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.example.adproject.helper.RequestEnum;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class FriendRequest {

    public FriendRequest(User sender, User recipient){
        this.sender = sender;
        this.recipient = recipient;
        this.status = RequestEnum.PENDING;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private RequestEnum status;

    @ManyToOne
    private User sender;

    @ManyToOne
    private User recipient;
    
}
