package com.journalapp.model;

public class Individual extends Subscriber { // Removed implements Serializable
    private static final long serialVersionUID = 1L;

    private String creditCardNr;
    private int expireMonth;
    private int expireYear;
    private int CCV;

    public Individual(String name, String address, String creditCardNr, int expireMonth, int expireYear, int CCV) {
        super(name, address);
        if (creditCardNr == null || creditCardNr.trim().isEmpty()) {
            throw new IllegalArgumentException("Credit card number cannot be null or empty.");
        }
        if (expireMonth < 1 || expireMonth > 12) {
            throw new IllegalArgumentException("Expiry month must be between 1 and 12.");
        }
         if (expireYear < 1000 || expireYear > 9999) {
             throw new IllegalArgumentException("Expiry year must be a 4-digit number.");
         }
         if (CCV < 100 || CCV > 9999) {
              throw new IllegalArgumentException("CCV must be a 3 or 4-digit number.");
         }

        this.creditCardNr = creditCardNr;
        this.expireMonth = expireMonth;
        this.expireYear = expireYear;
        this.CCV = CCV;
    }

    @Override
    public String getBillingInformation() {
        return "Credit Card: " + creditCardNr + ", Expires: " + expireMonth + "/" + expireYear;
    }

    public String getCreditCardNr() {
        return creditCardNr;
    }

    public int getExpireMonth() {
        return expireMonth;
    }

    public int getExpireYear() {
        return expireYear;
    }

    public int getCCV() {
        return CCV;
    }
}
