package com.fabricsoft.freunde.service;


import com.fabricsoft.freunde.model.Friend;
import org.springframework.data.repository.CrudRepository;

public interface FriendService extends CrudRepository<Friend, Integer> {

        Iterable<Friend> findByFirstNameAndLastName(String firstName, String lastName);

       }