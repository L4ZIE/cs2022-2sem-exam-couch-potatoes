package be;

public class EditLog {
    int id;
    int accountID;
    String refNumber;
    String date;

    public int getId() {
        return id;
    }

    public int getAccountID() {
        return accountID;
    }

    public String getRefNumber() {
        return refNumber;
    }

    public String getDate() {
        return date;
    }

    public EditLog(int id, int accountID, String refNumber, String date) {
        this.id = id;
        this.accountID = accountID;
        this.refNumber = refNumber;
        this.date = date;
    }

    @Override
    public String toString() {
        return id + " " + accountID + " " + refNumber + " " + date;
    }
}
