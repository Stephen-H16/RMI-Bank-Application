package ServerServiceCode;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Stephen Hoey
 */
public interface AccountManagerInterface extends Remote {
    /*
     1) Adding an account
     2) Logging in to an account
     3) Withdraw money from an account
     4) Deposit money into an account
     5) Get the balance within an account
     6) Calculate the interest to be earned by the balance
     7) Change the interest rate for all accounts    
     */

    public boolean register(String firstName, String lastName, String address, String password) throws RemoteException;

    public boolean login(String firstName, String lastName, String password) throws RemoteException;

    public boolean withdraw(double amount, String firstName, String lastName, String password) throws RemoteException;

    public boolean deposit(double amount, String firstName, String lastName, String password) throws RemoteException;

    public double getBalance(String firstName, String lastName, String password) throws RemoteException;

    public double calcInterest(String firstName, String lastName, String password) throws RemoteException;
}
