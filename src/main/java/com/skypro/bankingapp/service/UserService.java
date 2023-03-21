package com.skypro.bankingapp.service;

import com.skypro.bankingapp.dto.UserDTO;
import com.skypro.bankingapp.dto.request.CreateUserRequest;
import com.skypro.bankingapp.exception.InvalidPasswordException;
import com.skypro.bankingapp.exception.UserExistsException;
import com.skypro.bankingapp.exception.UserNotFoundException;
import com.skypro.bankingapp.model.Currency;
import com.skypro.bankingapp.model.User;
import com.skypro.bankingapp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final AccountService accountService;

    public UserService(UserRepository userRepository, AccountService accountService) {
        this.userRepository = userRepository;
        this.accountService = accountService;
    }

    public UserDTO addUser(CreateUserRequest userRequest) {
        if (!userRequest.password().equals(userRequest.repeatPassword())){
            throw new InvalidPasswordException("Пароли не совпадают");
        }
        if (userRepository.findById(userRequest.username()).isPresent()) {
            throw new UserExistsException();
        }
        //validate(userRequest);
        User user = userRequest.toUser();
        userRepository.save(user);
        createNewUserAccount(user);
        return UserDTO.fromUser(user);
    }

    private void validate(CreateUserRequest userRequest) {
    }

    public void updatePassword(String username, String password, String newPassword) {
        User user = userRepository.findById(username).orElseThrow(UserNotFoundException::new);
        if (!user.getPassword().equals(password)) {
            throw new InvalidPasswordException("Пароли не совпадают");
        }
        user.setPassword(newPassword);
        userRepository.save(user);
    }

    public void removeUser(String username) {
        User user = userRepository.findById(username).orElseThrow(UserNotFoundException::new);
        userRepository.delete(user);
    }

    public User getUser(String username) {
        return userRepository.findById(username).orElseThrow(UserNotFoundException::new);
    }

    public Collection<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserDTO::fromUser)
                .toList();
    }

    private void createNewUserAccount(User user) {
        user.addAccount(accountService.createAccount(user, Currency.RUB));
        user.addAccount(accountService.createAccount(user, Currency.USD));
        user.addAccount(accountService.createAccount(user, Currency.EUR));
    }
}
