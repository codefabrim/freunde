package com.fabricsoft.freunde.controller;




import com.fabricsoft.freunde.model.Friend;
import com.fabricsoft.freunde.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class FriendController {

    @Autowired
    FriendService friendsService;

    //the Url mappings here

    //    CREATE
    @PostMapping("/friend")
    Friend create(@RequestBody Friend cfriend) {
        return friendsService.save(cfriend);
    }

    //    READ
    @GetMapping("/friend")
    Iterable<Friend> read() {
        return friendsService.findAll();
    }

    //    UPDATE
    @PutMapping("/friend")
    Friend update(@RequestBody Friend ufriend) {
        return friendsService.save(ufriend);
    }


    //    DELETE
    @DeleteMapping("/friend/{id}")
    void delete(@PathVariable Integer id) {
        friendsService.deleteById(id);

    }

    @GetMapping("/friend/{id}")
    Optional<Friend> findById(@PathVariable Integer id) {
        return friendsService.findById(id);
    }


    @GetMapping("/friend/search")
    Iterable<Friend> findByQuery(
            @RequestParam("first") String firstName,
            @RequestParam("last") String lastName) {
        return friendsService.findByFirstNameAndLastName(firstName, lastName);
    }


}