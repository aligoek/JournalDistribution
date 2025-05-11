package com.journalapp.model;

import java.io.Serializable;
import java.util.Calendar;

public class PaymentTransaction implements Serializable {
    private static final long serialVersionUID = 1L;

    private double amount;
    private Calendar paymentDate;

    public PaymentTransaction(double amount, Calendar paymentDate) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Payment amount must be positive.");
        }
        if (paymentDate == null) {
            throw new IllegalArgumentException("Payment date cannot be null.");
        }
        this.amount = amount;
        this.paymentDate = paymentDate;
    }

    public double getAmount() {
        return amount;
    }

    public Calendar getPaymentDate() {
        return paymentDate;
    }
}
