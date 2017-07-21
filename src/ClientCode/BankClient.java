package ClientCode;

import java.rmi.Naming;
import java.util.Scanner;

/**
 *
 * @author Stephen Hoey
 */
public class BankClient {

    public static void main(String[] args) {
        try {
            int RMIPort;
            String hostName;

            String firstName = null;
            String secondName = null;

            Scanner input = new Scanner(System.in);
            // Get the information for the registry
            System.out.println("Enter the RMIRegistry host name:");
            hostName = input.nextLine();
            System.out.println("Enter the RMIregistry port number:");
            RMIPort = input.nextInt();
            input.nextLine();

            // find the remote object and cast it to an interface object
            String registryURL = "rmi://" + hostName + ":" + RMIPort + "/accountManager";
            ServerServiceCode.AccountManagerInterface h = (ServerServiceCode.AccountManagerInterface) Naming.lookup(registryURL);
            System.out.println("Lookup completed ");

            // Start logic of your client
            boolean exit = false;
            boolean loggedIn = false;
            System.out.println("Welcome to the MG Banking Application");
            while (!exit) {
                System.out.println("Which of the following would you like to do?");
                System.out.println("1) Join the bank");
                System.out.println("2) Log in to your account");
                System.out.println("3) Exit");
                String choice = input.nextLine();
                if (choice.equals("1")) {
                    System.out.println("**************Registration************");
                    System.out.println("Please enter your first name:");
                    String fName = input.nextLine();
                    System.out.println("Please enter your second name:");
                    String sName = input.nextLine();
                    System.out.println("Please enter your password:");
                    String pass = input.nextLine();
                    System.out.println("Please enter your address:");
                    String address = input.nextLine();
                    System.out.println("**************************************");
                    boolean verdict = h.register(fName, sName, address, pass);
                    if (verdict) {
                        System.out.println("Congratulations, you have registered successfully!");
                        // Move the user into the log in and use bank account menu
                        choice = "2";
                        // Set the user to be logged in & store their name information
                        loggedIn = true;
                        firstName = fName;
                        secondName = sName;
                    } else {
                        System.out.println("Sorry, that user already exists. Please log in or register a new account.");
                    }
                }
                // Allow user to skip through log in step by making this an if instead of an else if
                if (choice.equals("2")) {
                    while (!loggedIn) {
                        System.out.println("***************Logging In**************");
                        System.out.println("Please log in to proceed");
                        System.out.println("Please enter your first name");
                        String fName = input.nextLine();
                        System.out.println("Please enter your second name");
                        String sName = input.nextLine();
                        System.out.println("Please enter your password");
                        String pass = input.nextLine();
                        System.out.println("*************************************");

                        // call remote method
                        boolean verdict = h.login(fName, sName, pass);
                        if (verdict) {
                            // If the user was logged in successfully, set them to be logged in 
                            // and store their name information 
                            loggedIn = true;
                            firstName = fName;
                            secondName = sName;
                        } else {
                            System.out.println("Sorry, that login information was not found. Please try again.");
                        }
                    }

                    // Start menu functionality.
                    System.out.println("Welcome to the MG Bank, " + firstName + ".");
                    String menuChoice = "";
                    while (!menuChoice.equals("5")) {
                        System.out.println("What would you like to do?");
                        System.out.println("1) View your balance");
                        System.out.println("2) Withdraw money");
                        System.out.println("3) Deposit money");
                        System.out.println("4) Calculate the interest your account will earn");
                        System.out.println("5) Exit the bank application.");

                        menuChoice = input.nextLine();
                        if (menuChoice.equals("1")) {
                            System.out.println("Please enter your password: ");
                            String password = input.nextLine();
                            // call method from remote object
                            double balance = h.getBalance(firstName, secondName, password);

                            if (balance != -1) {
                                System.out.println("Your current balance is: e" + balance);
                            } else {
                                System.out.println("You do not have permission to do that. Are you sure you entered the right password?");
                            }
                        } else if (menuChoice.equals("2")) {
                            System.out.println("How much would you like to withdraw?");
                            double amount = input.nextDouble();
                            input.nextLine();

                            System.out.println("Please enter your password: ");
                            String password = input.nextLine();
                            // call method from remote object
                            boolean successful = h.withdraw(amount, firstName, secondName, password);
                            if (successful) {
                                System.out.println("You successfully withdrew e" + amount + " from your account.");
                            } else {
                                System.out.println("Sorry, this transaction could not be performed. Do you have sufficient funds to do this?");
                            }
                        } else if (menuChoice.equals("3")) {
                            System.out.println("How much would you like to deposit?");
                            double amount = input.nextDouble();
                            input.nextLine();

                            System.out.println("Please enter your password: ");
                            String password = input.nextLine();
                            // call method from remote object
                            boolean successful = h.deposit(amount, firstName, secondName, password);
                            if (successful) {
                                System.out.println("You successfully deposited e" + amount + " into your account.");
                            } else {
                                System.out.println("Sorry, this transaction could not be performed. Did you supply the correct password?");
                            }
                        } else if (menuChoice.equals("4")) {
                            System.out.println("Please enter your password: ");
                            String password = input.nextLine();
                            // call method from remote object
                            double interest = h.calcInterest(firstName, secondName, password);

                            if (interest != -1) {
                                System.out.println("The current amount of interest to be earned is: e" + interest);
                            } else {
                                System.out.println("You do not have permission to do that. Are you sure you entered the right password?");
                            }
                        } else if (menuChoice.equals("5")) {
                            exit = true;
                        } else {
                            System.out.println("Please choose a valid option from the menu listed.");
                        }
                        System.out.println("");
                    }
                } else if (choice.equals("3")) {
                    exit = true;
                } else {
                    System.out.println("Please enter a selection from the menu options listed.");
                }
            }
            System.out.println("Thank you for using the MG Banking Application.");
            System.out.println("Program concluded.");

        } // end try
        catch (Exception e) {
            System.out.println("Exception in Client: " + e);
        } // end catch
    }

}
