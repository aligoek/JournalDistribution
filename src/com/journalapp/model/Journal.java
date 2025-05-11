// src/com/journalapp/model/Journal.java
package com.journalapp.model;

import java.util.Objects; // Import Objects for hashCode and equals

/**
 * Represents a journal with its core information.
 * ISSN is the unique identifier for a journal.
 */
public class Journal {
    // Fields based on the UML diagram, all marked as final.
    private final String name;       // The name of the journal.
    private final String issn;       // The International Standard Serial Number (unique identifier).
    private final int frequency;     // The number of issues published per year.
    private final double issuePrice; // The price of a single issue.

    /**
     * Constructs a new Journal object.
     *
     * @param name       The name of the journal.
     * @param issn       The ISSN of the journal (must be unique).
     * @param frequency  The frequency of publication per year.
     * @param issuePrice The price per issue.
     */
    public Journal(String name, String issn, int frequency, double issuePrice) {
        // Basic validation for required fields
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Journal name cannot be null or empty.");
        }
        if (issn == null || issn.trim().isEmpty()) {
            throw new IllegalArgumentException("Journal ISSN cannot be null or empty.");
        }
         if (frequency <= 0) {
            throw new IllegalArgumentException("Journal frequency must be positive.");
        }
         if (issuePrice < 0) {
            throw new IllegalArgumentException("Journal issue price cannot be negative.");
        }


        this.name = name;
        this.issn = issn;
        this.frequency = frequency;
        this.issuePrice = issuePrice;
    }

    /**
     * Gets the name of the journal.
     * @return The journal name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the ISSN of the journal.
     * @return The journal ISSN.
     */
    public String getIssn() {
        return issn;
    }

    /**
     * Gets the publication frequency of the journal.
     * @return The frequency per year.
     */
    public int getFrequency() {
        return frequency;
    }

    /**
     * Gets the price of a single issue of the journal.
     * @return The issue price.
     */
    public double getIssuePrice() {
        return issuePrice;
    }

    // The UML shows an addSubscription method here, but the logic for managing
    // subscriptions is more appropriately handled in the Distributor class,
    // which holds collections of journals and subscribers. We will implement
    // the core addSubscription logic in the Distributor.

    /**
     * Compares this Journal object to another object for equality.
     * Journals are considered equal if their ISSNs are the same.
     *
     * @param o The object to compare with.
     * @return true if the objects are equal (have the same ISSN), false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Journal journal = (Journal) o;
        // ISSN is the unique identifier, so equality is based on ISSN.
        return Objects.equals(issn, journal.issn);
    }

    /**
     * Returns a hash code value for the Journal object.
     * The hash code is based on the ISSN, ensuring consistency with the equals method.
     *
     * @return The hash code for this journal.
     */
    @Override
    public int hashCode() {
        return Objects.hash(issn);
    }
}
