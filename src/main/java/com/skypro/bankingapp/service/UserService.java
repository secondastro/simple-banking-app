package com.skypro.bankingapp.service;

import com.skypro.bankingapp.dto.UserDTO;
import com.skypro.bankingapp.dto.request.CreateUserRequest;
import com.skypro.bankingapp.exception.InvalidPasswordException;
import com.skypro.bankingapp.exception.UserExistsException;
import com.skypro.bankingapp.exception.UserNotFoundException;
import com.skypro.bankingapp.model.Account;
import com.skypro.bankingapp.model.Currency;
import com.skypro.bankingapp.model.User;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    private final Map<String, User> users = new HashMap<>();

    public UserDTO addUser(CreateUserRequest userRequest) {
        if (!userRequest.password().equals(userRequest.repeatPassword())){
            throw new InvalidPasswordException("Пароли не совпадают");
        }
        if (users.containsKey(userRequest.username())) {
            throw new UserExistsException();
        }
        //validate(userRequest);
        User user = userRequest.toUser();
        users.put(user.getUsername(), user);
        createNewUserAccount(user);
        return UserDTO.fromUser(user);
    }

    private void validate(CreateUserRequest userRequest) {
    }

    public User updateUser(String username, String firstName, String lastName) {
        if (!users.containsKey(username)) {
            throw new UserNotFoundException();
        }
        User user = users.get(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        return user;
    }

    public void updatePassword(String username, String password, String newPassword) {
        if (!users.containsKey(username)) {
            throw new UserNotFoundException();
        }
        User user = users.get(username);
        if (!user.getPassword().equals(password)) {
            throw new InvalidPasswordException("Пароли не совпадают");
        }
        user.setPassword(newPassword);
    }

    public void removeUser(String username) {
        if (!users.containsKey(username)) {
            throw new UserNotFoundException();
        }
       users.remove(username);
    }

    public User getUser(String username) {
        if (!users.containsKey(username)) {
            throw new UserNotFoundException();
        }
        return users.get(username);
    }

    public Collection<UserDTO> getAllUsers() {
        return users.values().stream()
                .map(UserDTO::fromUser)
                .toList();
    }

    private void createNewUserAccount(User user) {
        user.addAccount(new Account(UUID.randomUUID().toString(), 0.0, Currency.RUB));
        user.addAccount(new Account(UUID.randomUUID().toString(), 0.0, Currency.EUR));
        user.addAccount(new Account(UUID.randomUUID().toString(), 0.0, Currency.USD));
    }
}
