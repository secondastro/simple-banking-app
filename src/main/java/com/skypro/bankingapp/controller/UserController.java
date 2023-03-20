package com.skypro.bankingapp.controller;

import com.skypro.bankingapp.dto.UserDTO;
import com.skypro.bankingapp.dto.request.ChangePasswordRequest;
import com.skypro.bankingapp.dto.request.CreateUserRequest;
import com.skypro.bankingapp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/user")
public class UserController {
    private final  UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public Collection<UserDTO> getAllUsers(){
        return  this.userService.getAllUsers();

    }
    @PostMapping("/")
    public UserDTO createUser(CreateUserRequest userRequest){
        return this.userService.addUser(userRequest);
    }

    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequest passwordRequest) {
        this.userService.updatePassword(
                "",
                passwordRequest.oldPassword(),
                passwordRequest.newPassword());
        return ResponseEntity.accepted().build();
    }
    @DeleteMapping("/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable("username") String username){
        this.userService.removeUser(username);
       return ResponseEntity.noContent().build();
    }
}
