package com.youtube.bank.service;

import com.youtube.bank.entity.User;
import com.youtube.bank.repository.UserRepository;

import java.util.Map;

public class UserService {

    private UserRepository userRepository = new UserRepository();

    public void printUsers() {
        userRepository.printUsers();
    }

    public User login(String username, String password) {
        return userRepository.login(username,password);
    }

    public boolean addNewCustomer(String username, String password, String contact) {
        return userRepository.addNewCustomer(username, password, contact);
    }

    public Double checkBankBalance(String userId) {
        return userRepository.checkBankBalance(userId);
    }

    public User getUser(String userId) {
        return userRepository.getUser(userId);
    }

    public boolean transferAmount(String userId, String payeeUserId, Double amount) {
        return userRepository.transferAmount(userId, payeeUserId, amount);
    }

    public void printTransactions(String userId) {
        userRepository.printTransactions(userId);
    }

    public void raiseChequeBookRequest(String userId) {
             userRepository.raiseChequeBookRequest(userId);
    }

    public Map<String, Boolean> getAllChequeBookRequest() {
        return userRepository.getAllChequeBookRequest();
    }
}
