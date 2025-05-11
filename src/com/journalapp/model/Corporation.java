package com.journalapp.model;

public class Corporation extends Subscriber { // Removed implements Serializable
    private static final long serialVersionUID = 1L;

    private int bankCode;
    private String bankName;
    private int IssueDay;
    private int issueMonth;
    private int issueYear;
    private int accountNumber;

    public Corporation(String name, String address, int bankCode, String bankName, int IssueDay, int issueMonth, int issueYear, int accountNumber) {
        super(name, address);
        if (bankCode <= 0) {
            throw new IllegalArgumentException("Bank code must be positive.");
        }
        if (bankName == null || bankName.trim().isEmpty()) {
            throw new IllegalArgumentException("Bank name cannot be null or empty.");
        }
        if (IssueDay < 1 || IssueDay > 31) {
             throw new IllegalArgumentException("Issue day must be between 1 and 31.");
        }
         if (issueMonth < 1 || issueMonth > 12) {
            throw new IllegalArgumentException("Issue month must be between 1 and 12.");
        }
         if (issueYear < 1000 || issueYear > 9999) {
             throw new IllegalArgumentException("Issue year must be a 4-digit number.");
         }
        if (accountNumber <= 0) {
            throw new IllegalArgumentException("Account number must be positive.");
        }

        this.bankCode = bankCode;
        this.bankName = bankName;
        this.IssueDay = IssueDay;
        this.issueMonth = issueMonth;
        this.issueYear = issueYear;
        this.accountNumber = accountNumber;
    }

    @Override
    public String getBillingInformation() {
        return "Bank: " + bankName + " (Code: " + bankCode + "), Account: " + accountNumber +
               ", Last Payment Reference Date: " + IssueDay + "/" + issueMonth + "/" + issueYear;
    }

    public int getBankCode() {
        return bankCode;
    }

    public String getBankName() {
        return bankName;
    }

    public int getIssueDay() {
        return IssueDay;
    }

    public int getIssueMonth() {
        return issueMonth;
    }

    public int getIssueYear() {
        return issueYear;
    }

    public int getAccountNumber() {
        return accountNumber;
    }
}
