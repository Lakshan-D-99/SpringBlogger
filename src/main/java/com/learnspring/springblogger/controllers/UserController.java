package com.learnspring.springblogger.controllers;

import com.learnspring.springblogger.dtos.UserDto;
import com.learnspring.springblogger.models.User;
import com.learnspring.springblogger.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api_v1/")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Get all the Users in our Database
    @GetMapping("users/all-users")
    public ResponseEntity<List<UserDto>> getAllTheUsers(
            @RequestParam(value = "pageNo",defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize
    ){
       return new ResponseEntity<>(userService.getAllUsers(pageNo,pageSize),HttpStatus.OK);
    }

    // Get a specific User from the Database
    @GetMapping("users/single-user/{userId}")
    public ResponseEntity<UserDto> getSingleUser(@PathVariable Long userId){
        return new ResponseEntity<>(userService.getSingleUserById(userId),HttpStatus.OK);
    }

    // Create a new User
    @PostMapping("users/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserDto> createNewUser(@RequestBody UserDto userDto){
       return new ResponseEntity<>(userService.createNewUser(userDto),HttpStatus.CREATED);
    }

    // Update and existing User
    @PutMapping("users/update/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long userId, @RequestBody UserDto newUser){
        return new ResponseEntity<>(userService.updateUserById(userId,newUser),HttpStatus.OK);
    }

    // Delete an existing User
    @DeleteMapping("users/delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
        return new ResponseEntity<>("This User has been deleted Successfully",HttpStatus.OK);
    }

}
