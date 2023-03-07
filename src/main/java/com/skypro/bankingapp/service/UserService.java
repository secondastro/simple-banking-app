package com.skypro.bankingapp.service;

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
    private final Map<String, User> userMap = new HashMap<>();

    public User addUser(User user) {
        if (userMap.containsKey(user.getUsername())) {
            throw new UserExistsException();
        }
        userMap.put(user.getUsername(), user);
        return createNewUserAccount(user);
    }

    public User updateUser(String username, String firstName, String lastName) {
        if (!userMap.containsKey(username)) {
            throw new UserNotFoundException();
        }
        User user = userMap.get(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        return user;
    }

    public void updatePassword(String username, String password, String newPassword) {
        if (!userMap.containsKey(username)) {
            throw new UserNotFoundException();
        }
        User user = userMap.get(username);
        if (!user.getPassword().equals(password)) {
            throw new InvalidPasswordException();
        }
        user.setPassword(newPassword);
    }

    public User removeUser(String username) {
        if (!userMap.containsKey(username)) {
            throw new UserNotFoundException();
        }
        return userMap.remove(username);
    }

    public User getUser(String username) {
        if (!userMap.containsKey(username)) {
            throw new UserNotFoundException();
        }
        return userMap.get(username);
    }

    public Collection<User> getAllUsers() {
        return userMap.values();
    }

    private User createNewUserAccount(User user) {
        user.addAccount(new Account(UUID.randomUUID().toString(), 0.0, Currency.RUB));
        user.addAccount(new Account(UUID.randomUUID().toString(), 0.0, Currency.EUR));
        user.addAccount(new Account(UUID.randomUUID().toString(), 0.0, Currency.USD));
        return user;
    }
}
