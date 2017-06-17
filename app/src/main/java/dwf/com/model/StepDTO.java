package dwf.com.model;

/**
 * Created by Charlie Winnardd on 5/25/2017.
 */

public class StepDTO {
    int runId;
    double accountValue;
    double monthlySavings;

    public int getRunId() {
        return runId;
    }
    public void setRunId(int runId) {
        this.runId = runId;
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
