package com.journalapp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PaymentInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private final double discountRatio;
    private List<PaymentTransaction> transactions;

    public PaymentInfo(double discountRatio) {
        if (discountRatio < 0 || discountRatio > 1) {
             throw new IllegalArgumentException("Discount ratio must be between 0.0 and 1.0.");
        }
        this.discountRatio = discountRatio;
        this.transactions = new ArrayList<>();
    }

    public void recordPayment(double amount, Calendar paymentDate) {
        if (amount > 0 && paymentDate != null) {
            this.transactions.add(new PaymentTransaction(amount, paymentDate));
        } else {
             System.err.println("Warning: Attempted to record non-positive payment or null date.");
        }
    }

    public double getReceivedPayment() {
        double total = 0.0;
        for (PaymentTransaction transaction : transactions) {
            total += transaction.getAmount();
        }
        return total;
    }

    public List<PaymentTransaction> getTransactions() {
        return transactions;
    }

    public double getDiscountRatio() {
        return discountRatio;
    }
}
