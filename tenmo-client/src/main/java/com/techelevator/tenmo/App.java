package com.techelevator.tenmo;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transaction;
import com.techelevator.tenmo.model.UserCredentials;
import com.techelevator.tenmo.model.UserDto;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.ConsoleService;
import com.techelevator.tenmo.services.TEnmoService;

import java.math.BigDecimal;

public class App {
    private final TEnmoService tEnmoService = new TEnmoService();

    private static final String API_BASE_URL = "http://localhost:8080/";

    private final ConsoleService consoleService = new ConsoleService();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);

    private AuthenticatedUser currentUser;

    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    private void run() {
        consoleService.printGreeting();
        loginMenu();
        if (currentUser != null) {
            mainMenu();
        }
    }

    private void loginMenu() {
        int menuSelection = -1;
        while (menuSelection != 0 && currentUser == null) {
            consoleService.printLoginMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                handleRegister();
            } else if (menuSelection == 2) {
                handleLogin();
            } else if (menuSelection != 0) {
                System.out.println("Invalid Selection");
                consoleService.pause();
            }
        }
    }

    private void handleRegister() {
        System.out.println("Please register a new user account");
        UserCredentials credentials = consoleService.promptForCredentials();
        if (authenticationService.register(credentials)) {
            System.out.println("Registration successful. You can now login.");
        } else {
            consoleService.printErrorMessage();
        }
    }


    private void handleLogin() {
        UserCredentials credentials = consoleService.promptForCredentials();
        currentUser = authenticationService.login(credentials);
        String token = currentUser.getToken();
        if (currentUser == null) {
            consoleService.printErrorMessage();
        } else {
            tEnmoService.setAuthToken(token);
        }
    }

    private void mainMenu() {
        int menuSelection = -1;
        while (menuSelection != 0) {
            consoleService.printMainMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                viewCurrentBalance();
            } else if (menuSelection == 2) {
                viewTransferHistory();
            } else if (menuSelection == 3) {
                viewPendingRequests();
            } else if (menuSelection == 4) {
                sendBucks();
            } else if (menuSelection == 5) {
                requestBucks();
            } else if (menuSelection == 0) {
                continue;
            } else {
                System.out.println("Invalid Selection");
            }
            consoleService.pause();
        }
    }

    private void viewCurrentBalance() {
        BigDecimal balance = tEnmoService.viewCurrentBalance(currentUser.getUser().getId());
        if (balance != null) {
            System.out.println("Your current account balance is: $" + balance);
        } else {
            consoleService.printErrorMessage();
        }
    }

    private void viewTransferHistory() {
        Transaction[] transactions = tEnmoService.viewTransferHistory(currentUser.getUser().getId());
        if (transactions != null) {
            System.out.println("-------------------------------------------");
            System.out.println("Transfers");
            System.out.println("ID                  From/To                  Amount");
            System.out.println("-------------------------------------------");
            for (Transaction transaction : transactions) {
                if (transaction.getFromUserId() == currentUser.getUser().getId()) {
                    String toUser = getUserById(transaction.getToUserId());
                    System.out.println(transaction.getTransactionId() + "                  To: " + toUser + "      $" +
                            transaction.getAmount().toString());
                } else {
                    String fromUser = getUserById(transaction.getFromUserId());
                    System.out.println(transaction.getTransactionId() + "                  From: " + fromUser + "      $" +
                            transaction.getAmount().toString());
                }
            }
            System.out.println("-------------------------------------------");
            int viewTransferId = consoleService.promptForMenuSelection("Please enter transfer ID to view details (0 to cancel): ");
            Transaction viewTransaction = tEnmoService.viewTransaction(viewTransferId);
            if (viewTransaction != null) {
                System.out.println("-------------------------------------------");
                System.out.println("Transfer Details");
                System.out.println("-------------------------------------------");
                System.out.println("Id: " + viewTransaction.getTransactionId());
                System.out.println("From: " + getUserById(viewTransaction.getFromUserId()));
                System.out.println("To: " + getUserById(viewTransaction.getToUserId()));
                System.out.println("Type: Send");
                System.out.println("Status: Approved");
                System.out.println("Amount: $" + viewTransaction.getAmount());
            }
        } else {
            consoleService.printErrorMessage();
        }
    }

    private void viewPendingRequests() {
        // TODO Auto-generated method stub

    }

    private void sendBucks() {

    }

    private void requestBucks() {
        // TODO Auto-generated method stub

    }

    private String getUserById(int userId) {
        UserDto[] userlist = tEnmoService.listUsers();
        for (UserDto userDto : userlist) {
            if (userDto.getId() == userId) {
                return userDto.getUsername();
            }
        }
        return null;
    }

}
