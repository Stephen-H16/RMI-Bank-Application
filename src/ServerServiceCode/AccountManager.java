package ServerServiceCode;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Stephen Hoey
 */
public class AccountManager extends UnicastRemoteObject implements AccountManagerInterface {

    private List<BankAccount> accounts = new ArrayList<BankAccount>();

    public AccountManager() throws RemoteException {
        super();
    }

    private BankAccount locateAccount(String firstName, String lastName, String password) {
        for (BankAccount b : accounts) {
            if (b.getFirstName().equals(firstName) && b.getLastName().equals(lastName) && b.getPassword().equals(password)) {
                return b;
            }
        }
        return null;
    }

    public boolean register(String firstName, String lastName, String address, String password) throws RemoteException {
        BankAccount b = new BankAccount(firstName, lastName, address, password);
        if (!accounts.contains(b)) {
            accounts.add(b);
            return true;
        } else {
            return false;
        }
    }

    public boolean login(String firstName, String lastName, String password) throws RemoteException {
        BankAccount b = locateAccount(firstName, lastName, password);
        if (b != null) {
            b.setLoggedIn(true);
            return true;
        } else {
            return false;
        }
    }

    public boolean withdraw(double amount, String firstName, String lastName, String password) throws RemoteException {
        BankAccount b = locateAccount(firstName, lastName, password);

        if (b != null) {
            return b.withdraw(amount);
        } else {
            return false;
        }
    }

    public boolean deposit(double amount, String firstName, String lastName, String password) throws RemoteException {
        BankAccount b = locateAccount(firstName, lastName, password);

        if (b != null) {
            b.deposit(amount);
            return true;
        } else {
            return false;
        }
    }

    public double getBalance(String firstName, String lastName, String password) throws RemoteException {
        BankAccount b = locateAccount(firstName, lastName, password);

        if (b != null) {
            return b.getBalance();
        } else {
            return -1;
        }
    }

    public double calcInterest(String firstName, String lastName, String password) throws RemoteException {
        BankAccount b = locateAccount(firstName, lastName, password);

        if (b != null) {
            return b.calcInterest();
        } else {
            return -1;
        }
    }
}
