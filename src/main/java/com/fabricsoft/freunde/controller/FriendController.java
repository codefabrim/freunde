package com.fabricsoft.freunde.controller;




import com.fabricsoft.freunde.model.Friend;
import com.fabricsoft.freunde.service.FriendService;
import com.fabricsoft.freunde.util.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;
import java.util.Optional;

@RestController
public class FriendController {

    @Autowired
    FriendService friendsService;

    //the Url mappings here

    //    CREATE
    @PostMapping("/friend")
    Friend create(@RequestBody Friend cfriend) throws ValidationException {
//        if (Integer.valueOf(cfriend.getAge()) != null &&
        if (cfriend.getAge() != 0 &&
            cfriend.getFirstName() != null &&
            cfriend.getLastName() != null)
                return friendsService.save(cfriend);
        else
                throw new ValidationException("Friend cannot be created");
         }

         @ResponseStatus(HttpStatus.BAD_REQUEST)
         @ExceptionHandler(ValidationException.class)
         ErrorMessage exceptionHandler(ValidationException e){
            return new ErrorMessage ("400", e.getMessage());

         }


    //    READ
    @GetMapping("/friend")
    Iterable<Friend> read() {
        return friendsService.findAll();
    }

    //    UPDATE
    @PutMapping("/friend")
    ResponseEntity<Friend> update(@RequestBody Friend ufriend) {

        if (friendsService.findById(ufriend.getId()).isPresent())
            return new ResponseEntity(friendsService.save(ufriend), HttpStatus.OK);
        else
            return new ResponseEntity(ufriend, HttpStatus.BAD_REQUEST);
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
            @RequestParam(value= "first", required = false) String firstName,
            @RequestParam(value = "last", required = false) String lastName) {

        if (firstName != null && lastName != null) {
             return friendsService.findByFirstNameAndLastName(firstName, lastName);

        } else if (firstName != null) {
            return friendsService.findByFirstName(firstName);

        } else if (lastName != null) {
            return friendsService.findByLastName(lastName);

        } else {
            return friendsService.findAll();

        }

    }

}