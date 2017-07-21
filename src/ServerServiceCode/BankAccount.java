package ServerServiceCode;

import java.util.Objects;

/**
 *
 * @author Stephen Hoey
 */
class BankAccount {

    private String firstName;
    private String lastName;
    private String address;
    private String password;
    private double balance;

    private boolean loggedIn = false;

    private static double interestRate = 0.5;

    public BankAccount(String firstName, String lastName, String address, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.password = password;
        balance = 0;
    }

    public BankAccount(String firstName, String lastName, String address, String password, double balance) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.password = password;
        this.balance = balance;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.firstName);
        hash = 79 * hash + Objects.hashCode(this.lastName);
        hash = 79 * hash + Objects.hashCode(this.address);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BankAccount other = (BankAccount) obj;
        if (!Objects.equals(this.firstName, other.firstName)) {
            return false;
        }
        if (!Objects.equals(this.lastName, other.lastName)) {
            return false;
        }
        if (!Objects.equals(this.address, other.address)) {
            return false;
        }
        return true;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getInterestRate() {
        return BankAccount.interestRate;
    }

    public synchronized void setInterestRate(double interestRate) {
        BankAccount.interestRate = interestRate;
    }

    @Override
    public String toString() {
        return "BankAccount{" + "firstName=" + firstName + ", lastName=" + lastName + ", address=" + address + ", password=" + password + ", balance=" + balance + '}';
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public boolean withdraw(double amount) {
        if (balance - amount >= 0) {
            balance = balance - amount;
            return true;
        } else {
            return false;
        }
    }

    public void deposit(double amount) {
        balance = balance + amount;
    }

    public double calcInterest() {
        return balance * BankAccount.interestRate;
    }
}
