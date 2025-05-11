package com.journalapp.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Subscription implements Serializable {
    private static final long serialVersionUID = 1L;

    private final DateInfo dates;
    private PaymentInfo payment;
    private int copies;
    private final Journal journal;
    private final Subscriber subscriber;

    public Subscription(DateInfo dates, int copies, Journal journal, Subscriber subscriber, double discountRatio) {
        if (dates == null) {
            throw new IllegalArgumentException("Subscription dates cannot be null.");
        }
        if (copies <= 0) {
            throw new IllegalArgumentException("Number of copies must be positive.");
        }
        if (journal == null) {
            throw new IllegalArgumentException("Journal cannot be null.");
        }
        if (subscriber == null) {
            throw new IllegalArgumentException("Subscriber cannot be null.");
        }
         if (discountRatio < 0 || discountRatio > 1) {
             throw new IllegalArgumentException("Discount ratio must be between 0.0 and 1.0.");
        }

        this.dates = dates;
        this.copies = copies;
        this.journal = journal;
        this.subscriber = subscriber;
        this.payment = new PaymentInfo(discountRatio);
    }

    public void acceptPayment(double amount) {
        this.payment.recordPayment(amount, Calendar.getInstance());
    }

    public boolean canSend(int issueMonth, int issueYear) {
        // Calculate the 1-year subscription period start and end dates
        Calendar startDateCal = new GregorianCalendar(dates.getStartYear(), dates.getStartMonth() - 1, 1); // Month is 0-indexed

        Calendar endDateCal = new GregorianCalendar(dates.getStartYear(), dates.getStartMonth() - 1, 1);
        endDateCal.add(Calendar.YEAR, 1);
        endDateCal.add(Calendar.DAY_OF_MONTH, -1); // 1 year after start date minus 1 day

        // Create a Calendar for the end of the issue month
        Calendar issueEndDateCal = new GregorianCalendar(issueYear, issueMonth - 1, 1);
        issueEndDateCal.set(Calendar.DAY_OF_MONTH, issueEndDateCal.getActualMaximum(Calendar.DAY_OF_MONTH));

        // Check if the issue month/year is within the 1-year subscription period
        if (issueEndDateCal.before(startDateCal) || issueEndDateCal.after(endDateCal)) {
            return false; // Issue is outside the subscription period
        }

        // Calculate the number of days from the subscription start date to the end of the issue month
        long startMillis = startDateCal.getTimeInMillis();
        long issueEndMillis = issueEndDateCal.getTimeInMillis();

        long elapsedDays = (issueEndMillis - startMillis) / (1000 * 60 * 60 * 24);

        // Calculate the total duration of the subscription in days (approximately 365)
        long subscriptionDurationDays = (endDateCal.getTimeInMillis() - startMillis) / (1000 * 60 * 60 * 24) + 1;

        if (subscriptionDurationDays <= 0) {
             return false;
        }

        // Calculate the fraction of the subscription period that has passed
        double fractionOfYearPassed = (double) elapsedDays / subscriptionDurationDays;

        // Calculate the expected payment based on the fraction of the year passed
        double totalAnnualCost = journal.getIssuePrice() * journal.getFrequency() * copies;
        double discountedAnnualCost = totalAnnualCost * (1.0 - payment.getDiscountRatio());
        double expectedPaymentUpToMonth = discountedAnnualCost * fractionOfYearPassed;

        // Allow for a small floating-point tolerance when comparing payments
        double tolerance = 0.01;
        return payment.getReceivedPayment() >= (expectedPaymentUpToMonth - tolerance);
    }

    public void increaseCopies() {
        this.copies++;
    }

    public double calculateExpectedPayment() {
        double annualCost = journal.getIssuePrice() * journal.getFrequency() * copies;
        double discountedCost = annualCost * (1.0 - payment.getDiscountRatio());
        return discountedCost;
    }

    public DateInfo getDates() {
        return dates;
    }

    public PaymentInfo getPayment() {
        return payment;
    }

    public int getCopies() {
        return copies;
    }

    public Journal getJournal() {
        return journal;
    }

    public Subscriber getSubscriber() {
        return subscriber;
    }
}
