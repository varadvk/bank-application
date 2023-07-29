package com.youtube.bank.main;

import com.youtube.bank.entity.User;
import com.youtube.bank.service.UserService;

import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);
    static Main main = new Main();
    static UserService userService = new UserService();

    public static void main(String[] args) {


        while (true) {
            System.out.println("Enter your username");
            String username = scanner.next();

            System.out.println("Enter your password");
            String password = scanner.next();

            User user = userService.login(username, password);
            if (user != null && user.getRole().equals("admin")) {
                main.initAdmin();
            } else if(user != null && user.getRole().equals("user")) {
                main.initCustomer(user);
            } else {
                System.out.println("Login failed");
            }
        }
    }

    private void initAdmin() {

        boolean flag = true;

        while(flag) {
            System.out.println("1. Exit/Logout");
            System.out.println("2. Create a customer account");

            int selectedOption = scanner.nextInt();

            switch (selectedOption) {
                case 1:
                    flag = false;
                    System.out.println("You have successfully logged out...");
                    break;
                case 2:
                    main.addNewCustomer();
                    break;
                default:
                    System.out.println("Wrong choice");
            }
        }

    }

    private void addNewCustomer() {
        System.out.println("Enter username");
        String username = scanner.next();

        System.out.println("Enter password");
        String password = scanner.next();

        System.out.println("Enter contact number");
        String contact = scanner.next();

        boolean result = userService.addNewCustomer(username, password, contact);

        if (result) {
            System.out.println("Customer account is created...");
        } else {
            System.out.println("Customer account creation failed...");
        }
    }

    private void initCustomer(User user) {
        boolean flag = true;

        while (flag) {
            System.out.println("1. Exit/Logout");
            System.out.println("2. Check bank balance");
            System.out.println("3. Fund transfer");
            System.out.println("4. See all transactions");

            int selectedOption = scanner.nextInt();

            switch (selectedOption) {
                case 1:
                    flag = false;
                    System.out.println("You have successfully logged out...");
                    break;
                case 2:
                    Double balance = main.checkBankBalance(user.getUsername());
                    if(balance != null) {
                        System.out.println("Your bank balance is "+balance);
                    } else {
                        System.out.println("Check your username");
                    }
                    break;
                case 3:
                    main.fundTransfer(user);
                    break;
                case 4:
                    main.printTransactions(user.getUsername());
                    break;
                default:
                    System.out.println("Wrong choice");
            }
        }

    }

    private void printTransactions(String userId) {
        userService.printTransactions(userId);
    }

    private void fundTransfer(User userDetails) {

        System.out.println("Enter payee account user id");
        String payeeAccountId = scanner.next();

        User user = getUser(payeeAccountId);

        if(user != null) {
            System.out.println("Enter amount to transfer");
            Double amount = scanner.nextDouble();

            Double userAccountBalance = checkBankBalance(userDetails.getUsername());

            if(userAccountBalance >= amount) {
                boolean result = userService.transferAmount(userDetails.getUsername(), payeeAccountId, amount);

                if(result) {
                    System.out.println("Amount transferred successfully..");
                } else {
                    System.out.println("Transfer failed...");
                }
            } else {
                System.out.println("Your balance is insufficient: "+userAccountBalance);
            }


        } else {
            System.out.println("Please enter valid username...");
        }

    }

    private User getUser(String userId) {
        return userService.getUser(userId);
    }

    private Double checkBankBalance(String userId) {
          return userService.checkBankBalance(userId);
    }

}
