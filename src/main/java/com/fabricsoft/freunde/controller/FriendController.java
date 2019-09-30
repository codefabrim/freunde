package com.fabricsoft.freunde.controller;




import com.fabricsoft.freunde.model.Friend;
import com.fabricsoft.freunde.service.FriendService;
import com.fabricsoft.freunde.util.ErrorMessage;
import com.fabricsoft.freunde.util.FieldErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.bind.ValidationException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class FriendController {

    @Autowired
    FriendService friendsService;

    //the Url mappings here

    //    CREATE
    @PostMapping("/friend")
    Friend create(@Valid @RequestBody Friend cfriend) {
                return friendsService.save(cfriend);
        }



        @ResponseStatus(HttpStatus.BAD_REQUEST)
        @ExceptionHandler(MethodArgumentNotValidException.class)
        List<FieldErrorMessage> exceptionHandler(MethodArgumentNotValidException e){
            List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors() ;
            List<FieldErrorMessage> fieldErrorMessages = fieldErrors.stream()
                    .map(fieldError -> new FieldErrorMessage(fieldError.getField(), fieldError.getDefaultMessage()))
                    .collect(Collectors.toList());

            return fieldErrorMessages;
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