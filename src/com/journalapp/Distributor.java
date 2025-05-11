package com.journalapp;

import com.journalapp.model.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class Distributor implements Serializable {
    private static final long serialVersionUID = 1L;

    private Hashtable<String, Journal> journals;
    private Vector<Subscriber> subscribers;
    private Vector<Subscription> allSubscriptions;

    private volatile transient boolean reportRunning = false;
    private final transient Object reportLock = new Object();

    private transient JTextArea outputArea;


    public Distributor() {
        this.journals = new Hashtable<>();
        this.subscribers = new Vector<>();
        this.allSubscriptions = new Vector<>();
    }

    public void setOutputArea(JTextArea outputArea) {
        this.outputArea = outputArea;
    }

    private void appendOutput(String text) {
        if (outputArea != null) {
            SwingUtilities.invokeLater(() -> outputArea.append(text));
        } else {
            System.out.print(text);
        }
    }


    public boolean addJournal(Journal journal) {
        if (journal != null && !journals.containsKey(journal.getIssn())) {
            journals.put(journal.getIssn(), journal);
            appendOutput("Journal '" + journal.getName() + "' added.\n");
            return true;
        }
        appendOutput("Failed to add journal (null or ISSN already exists).\n");
        return false;
    }

    public Journal searchJournal(String issn) {
        return journals.get(issn);
    }

    public boolean addSubscriber(Subscriber subscriber) {
        if (subscriber != null) {
            for (Subscriber existingSubscriber : subscribers) {
                if (existingSubscriber.getName().equals(subscriber.getName()) && existingSubscriber.getAddress().equals(subscriber.getAddress())) {
                    appendOutput("Subscriber '" + subscriber.getName() + "' at '" + subscriber.getAddress() + "' already exists.\n");
                    return false;
                }
            }
            boolean added = subscribers.add(subscriber);
            if (added) {
                 appendOutput("Subscriber '" + subscriber.getName() + "' added.\n");
            } else {
                 appendOutput("Failed to add subscriber '" + subscriber.getName() + "'.\n");
            }
            return added;
        }
        appendOutput("Failed to add subscriber (null).\n");
        return false;
    }

    public Subscriber searchSubscriber(String name) {
        for (Subscriber subscriber : subscribers) {
            if (subscriber.getName().equals(name)) {
                return subscriber;
            }
        }
        return null;
    }

    public boolean addSubscription(String issn, Subscriber subscriber, Subscription subscription) {
        Journal journal = searchJournal(issn);

        if (journal == null || subscriber == null || subscription == null ||
            !subscription.getJournal().getIssn().equals(issn) || !subscription.getSubscriber().equals(subscriber)) {
            appendOutput("Failed to add subscription: Journal or Subscriber not found, or Subscription object inconsistent.\n");
            return false;
        }

        for (Subscription existingSubscription : allSubscriptions) {
            if (existingSubscription.getJournal().equals(journal) && existingSubscription.getSubscriber().equals(subscriber)) {
                existingSubscription.increaseCopies();
                appendOutput("Existing subscription found. Copies increased for Journal: " + journal.getName() + " and Subscriber: " + subscriber.getName() + " to " + existingSubscription.getCopies() + ".\n");
                return true;
            }
        }

        boolean added = allSubscriptions.add(subscription);
        if (added) {
             appendOutput("New subscription added for Journal: " + journal.getName() + " and Subscriber: " + subscriber.getName() + ".\n");
        } else {
             appendOutput("Failed to add new subscription.\n");
        }
        return added;
    }

    public String listAllSendingOrders(int month, int year) {
        StringBuilder sendingOrders = new StringBuilder("--- Sending Orders for Month: " + month + ", Year: " + year + " ---\n");
        boolean foundOrders = false;

        for (Subscription subscription : allSubscriptions) {
            if (subscription.canSend(month, year)) {
                 sendingOrders.append("- Journal: ").append(subscription.getJournal().getName())
                              .append(" (ISSN: ").append(subscription.getJournal().getIssn()).append(")")
                              .append(" to Subscriber: ").append(subscription.getSubscriber().getName())
                              .append(" (Copies: ").append(subscription.getCopies()).append(")\n");
                 foundOrders = true;
            }
        }

        if (!foundOrders) {
             sendingOrders.append("No sending orders for this month and year.\n");
        }
        sendingOrders.append("----------------------------------------\n");

        return sendingOrders.toString();
    }

    public String listSendingOrdersByJournalIssn(String issn, int month, int year) {
        StringBuilder sendingOrders = new StringBuilder("--- Sending Orders for Journal: " + issn + ", Month: " + month + ", Year: " + year + " ---\n");
        Journal journal = searchJournal(issn);

        if (journal == null) {
            return "Journal with ISSN " + issn + " not found.\n";
        }

        boolean foundOrders = false;
        for (Subscription subscription : allSubscriptions) {
            if (subscription.getJournal().getIssn().equals(issn)) {
                if (subscription.canSend(month, year)) {
                     sendingOrders.append("- to Subscriber: ").append(subscription.getSubscriber().getName())
                                  .append(" (Copies: ").append(subscription.getCopies()).append(")\n");
                     foundOrders = true;
                }
            }
        }

         if (!foundOrders) {
            sendingOrders.append("No sending orders for this journal in this month and year.\n");
        }
        sendingOrders.append("--------------------------------------------------\n");

        return sendingOrders.toString();
    }

    public String listIncompletePayments() {
         StringBuilder incompletePayments = new StringBuilder("--- Subscriptions with Incomplete Payments ---\n");
         boolean foundIncomplete = false;

        for (Subscription subscription : allSubscriptions) {
            double totalExpectedPayment = subscription.calculateExpectedPayment();

            double tolerance = 0.01;
            if (subscription.getPayment().getReceivedPayment() < totalExpectedPayment - tolerance) {
                 incompletePayments.append("- Subscriber: ").append(subscription.getSubscriber().getName())
                                   .append(", Journal: ").append(subscription.getJournal().getName())
                                   .append(", Received: ").append(String.format("%.2f", subscription.getPayment().getReceivedPayment()))
                                   .append(", Expected: ").append(String.format("%.2f", totalExpectedPayment)).append("\n");
                foundIncomplete = true;
            }
        }

         if (!foundIncomplete) {
             incompletePayments.append("No subscriptions with incomplete payments.\n");
        }
        incompletePayments.append("--------------------------------------------\n");

        return incompletePayments.toString();
    }

    public String listSubscriptionsBySubscriberName(String subscriberName) {
        StringBuilder subscriberSubscriptions = new StringBuilder("--- Subscriptions for Subscriber: " + subscriberName + " ---\n");

        boolean found = false;
        for (Subscription subscription : allSubscriptions) {
            if (subscription.getSubscriber().getName().equals(subscriberName)) {
                subscriberSubscriptions.append("- Journal: ").append(subscription.getJournal().getName())
                                       .append(" (ISSN: ").append(subscription.getJournal().getIssn()).append(")")
                                       .append(", Copies: ").append(subscription.getCopies())
                                       .append(", Period: ").append(subscription.getDates().getStartMonth()).append("/").append(subscription.getDates().getStartYear())
                                       .append(" to ").append(subscription.getDates().getEndMonth()).append("/").append(subscription.getDates().getEndYear())
                                       .append("\n");
                found = true;
            }
        }

         if (!found) {
             subscriberSubscriptions.append("No subscriptions found for this subscriber name.\n");
        }
        subscriberSubscriptions.append("------------------------------------------\n");

        return subscriberSubscriptions.toString();
    }

    public String listSubscriptionsByJournalIssn(String issn) {
        StringBuilder journalSubscriptions = new StringBuilder("--- Subscriptions for Journal (ISSN: " + issn + ") ---\n");
        Journal journal = searchJournal(issn);

        if (journal == null) {
            return "Journal with ISSN " + issn + " not found.\n";
        }

         boolean found = false;
        for (Subscription subscription : allSubscriptions) {
            if (subscription.getJournal().getIssn().equals(issn)) {
                 journalSubscriptions.append("- Subscriber: ").append(subscription.getSubscriber().getName())
                                     .append(", Copies: ").append(subscription.getCopies())
                                     .append(", Period: ").append(subscription.getDates().getStartMonth()).append("/").append(subscription.getDates().getStartYear())
                                     .append(" to ").append(subscription.getDates().getEndMonth()).append("/").append(subscription.getDates().getEndYear())
                                     .append("\n");
                 found = true;
            }
        }

         if (!found) {
             journalSubscriptions.append("No subscriptions found for this journal.\n");
        }
        journalSubscriptions.append("-----------------------------------------\n");

        return journalSubscriptions.toString();
    }

    public void saveState(String fileName) {
        synchronized (reportLock) {
            while (reportRunning) {
                try {
                    appendOutput("Save state waiting for report to finish...\n");
                    reportLock.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.err.println("Save state interrupted while waiting for report: " + e.getMessage());
                    return;
                }
            }
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(journals);
            oos.writeObject(subscribers);
            oos.writeObject(allSubscriptions);
            appendOutput("Distributor state successfully saved to " + fileName + "\n");
        } catch (IOException e) {
            System.err.println("Error saving distributor state to " + fileName + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void loadState(String fileName) {
        synchronized (reportLock) {
            while (reportRunning) {
                try {
                    appendOutput("Load state waiting for report to finish...\n");
                    reportLock.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.err.println("Load state interrupted while waiting for report: " + e.getMessage());
                    return;
                }
            }
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            journals = (Hashtable<String, Journal>) ois.readObject();
            subscribers = (Vector<Subscriber>) ois.readObject();
            allSubscriptions = (Vector<Subscription>) ois.readObject();
            appendOutput("Distributor state successfully loaded from " + fileName + "\n");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading distributor state from " + fileName + ": " + e.getMessage());
            e.printStackTrace();
            if (e instanceof FileNotFoundException) {
                appendOutput("State file not found. Starting with empty state.\n");
            }
            this.journals = new Hashtable<>();
            this.subscribers = new Vector<>();
            this.allSubscriptions = new Vector<>();
             appendOutput("Initialized empty collections due to loading error.\n");
        }
    }

    public void report(Calendar expiryDateThreshold, int startYearRange, int endYearRange) {
        new Thread(() -> {
            synchronized (reportLock) {
                reportRunning = true;
                appendOutput("Report generation started...\n");
            }

            StringBuilder reportContent = new StringBuilder();
            reportContent.append("--- Distributor Report ---\n");
            reportContent.append("Generated on: ").append(formatDate(Calendar.getInstance())).append("\n");
            reportContent.append("------------------------------------\n");

            reportContent.append("\n--- Subscriptions Expiring After ").append(formatDate(expiryDateThreshold)).append(" ---\n");

            boolean foundExpiring = false;
            for (Subscription sub : allSubscriptions) {
                Calendar endCal = new GregorianCalendar(sub.getDates().getStartYear(), sub.getDates().getStartMonth() - 1, 1);
                endCal.add(Calendar.YEAR, 1);
                endCal.add(Calendar.DAY_OF_MONTH, -1);

                if (!endCal.before(expiryDateThreshold)) {
                    reportContent.append("- Journal: ").append(sub.getJournal().getName())
                                 .append(", Subscriber: ").append(sub.getSubscriber().getName())
                                 .append(", Expires: ").append(formatDate(endCal)).append("\n");
                    foundExpiring = true;
                }
            }
            if (!foundExpiring) {
                reportContent.append("No subscriptions expiring after the given date.\n");
            }
            reportContent.append("----------------------------------------------------\n");


            reportContent.append("\n--- Received Annual Payments in Year Range: ").append(startYearRange).append(" - ").append(endYearRange).append(" ---\n");

            Map<Integer, Double> annualPayments = new HashMap<>();
            for (int year = startYearRange; year <= endYearRange; year++) {
                annualPayments.put(year, 0.0);
            }

            boolean foundPaymentsInYearRange = false;
            for (Subscription sub : allSubscriptions) {
                for (PaymentTransaction transaction : sub.getPayment().getTransactions()) {
                    int paymentYear = transaction.getPaymentDate().get(Calendar.YEAR);
                    if (paymentYear >= startYearRange && paymentYear <= endYearRange) {
                        annualPayments.put(paymentYear, annualPayments.get(paymentYear) + transaction.getAmount());
                        foundPaymentsInYearRange = true;
                    }
                }
            }

            if (foundPaymentsInYearRange) {
                annualPayments.entrySet().stream()
                              .sorted(Map.Entry.comparingByKey())
                              .forEach(entry -> reportContent.append("Year ").append(entry.getKey()).append(": ").append(String.format("%.2f", entry.getValue())).append("\n"));
            } else {
                reportContent.append("No payments received within the specified year range.\n");
            }
            reportContent.append("----------------------------------------------------------\n");

            reportContent.append("\n--- End of Report ---");

            appendOutput(reportContent.toString() + "\n");


            synchronized (reportLock) {
                reportRunning = false;
                reportLock.notifyAll();
                appendOutput("Report generation finished.\n");
            }
        }).start();
    }

    private String formatDate(Calendar calendar) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        return sdf.format(calendar.getTime());
    }

    public Hashtable<String, Journal> getJournals() {
        return journals;
    }

    public Vector<Subscriber> getSubscribers() {
        return subscribers;
    }

    public Vector<Subscription> getAllSubscriptions() {
        return allSubscriptions;
    }
}
