package dwf.com.model;

/**
 * Created by Charlie Winnardd on 5/29/2017.
 */

public class AccountDTO {
    int userId;
    String userName;
    String password;
    String firstName;
    double accountValue;
    double monthlySavings;

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public double getAccountValue() {
        return accountValue;
    }
    public void setAccountValue(double accountValue) {
        this.accountValue = accountValue;
    }
    public double getMonthlySavings() {
        return monthlySavings;
    }
    public void setMonthlySavings(double monthlySavings) {
        this.monthlySavings = monthlySavings;
    }
}
