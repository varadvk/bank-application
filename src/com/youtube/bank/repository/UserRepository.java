package com.youtube.bank.repository;

import com.youtube.bank.entity.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UserRepository {

    private static Set<User> users = new HashSet<>();

    static {

        User user1 = new User("admin", "admin", "1234567", "admin", 0.0);
        User user2 = new User("user2", "user2", "12345678", "user", 1000.0);
        User user3 = new User("user3", "user3", "12345679", "user", 2000.0);
        User user4 = new User("user4", "user4", "12345670", "user", 2000.0);

        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
    }

    public boolean transferAmount(String userId, String payeeUserId, Double amount) {

        boolean isDebit = debit(userId, amount);
        boolean isCredit = credit(payeeUserId, amount);

        return isDebit && isCredit;

    }

    private boolean debit(String userId, Double amount) {
        User user = getUser(userId);
        Double accountBalance = user.getAccountBalance();

        users.remove(user);

        Double finalBalance = accountBalance - amount;
        user.setAccountBalance(finalBalance);

        return users.add(user);
    }

    private boolean credit(String userId, Double amount) {
        User user = getUser(userId);
        Double accountBalance = user.getAccountBalance();

        users.remove(user);

        Double finalBalance = accountBalance + amount;
        user.setAccountBalance(finalBalance);

        return users.add(user);
    }

    public User getUser(String userId) {
        List<User> result = users.stream().filter(user -> user.getUsername().equals(userId)).collect(Collectors.toList());
        if(! result.isEmpty()) {
            return result.get(0);
        }
        return null;
    }

    public Double checkBankBalance(String userId) {
        List<User> result = users.stream().filter(user -> user.getUsername().equals(userId)).collect(Collectors.toList());

        if(!result.isEmpty()) {
            return result.get(0).getAccountBalance();
        } else {
            return null;
        }

    }

    public void printUsers() {
        System.out.println(users);
    }

    public User login(String username, String password) {
        List<User> finalList = users.stream()
                .filter(user -> user.getUsername().equals(username) && user.getPassword().equals(password))
                .collect(Collectors.toList());

        if(!finalList.isEmpty()) {
            return finalList.get(0);
        } else {
            return null;
        }
    }

    public boolean addNewCustomer(String username, String password, String contact) {
        User user = new User(username, password, contact, "user", 500.0);
        return users.add(user);
    }

}
