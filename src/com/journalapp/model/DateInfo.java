// src/com/journalapp/model/DateInfo.java
package com.journalapp.model;

/**
 * Represents the start and end dates for a journal subscription.
 * A subscription lasts for one year.
 */
public class DateInfo {
    // Fields based on the UML diagram.
    // These fields are marked as private.
    private int startMonth; // The starting month of the subscription (e.g., 1 for January, 12 for December)
    private int endMonth;   // The ending month of the subscription
    private int startYear;  // The starting year of the subscription

    /**
     * Constructs a new DateInfo object for a one-year subscription.
     *
     * @param startMonth The starting month (1-12).
     * @param startYear  The starting year.
     */
    public DateInfo(int startMonth, int startYear) {
        // Basic validation for month
        if (startMonth < 1 || startMonth > 12) {
            throw new IllegalArgumentException("Start month must be between 1 and 12.");
        }
        // Basic validation for year (can add more sophisticated checks if needed)
        if (startYear <= 0) {
             throw new IllegalArgumentException("Start year must be a positive value.");
        }

        this.startMonth = startMonth;
        this.startYear = startYear;

        // Calculate the end month and year for a one-year subscription.
        // A one-year subscription starting in month M of year Y ends in month M-1 of year Y+1.
        // If starting in January (1), it ends in December (12) of the same year + 1.
        // If starting in any other month M, it ends in month M-1 of the next year.
        if (startMonth == 1) {
            this.endMonth = 12;
        } else {
            this.endMonth = startMonth - 1;
        }
        // The end year is always the start year plus one for a one-year subscription.
        // The UML shows endMonth and startYear, but logically we need an endYear too.
        // Let's add a method to calculate/get the end year. The UML might be simplified.
        // We will calculate the end year based on the start year.
    }

    /**
     * Gets the starting month of the subscription.
     * @return The start month (1-12).
     */
    public int getStartMonth() {
        return startMonth;
    }

    /**
     * Gets the ending month of the subscription.
     * @return The end month (1-12).
     */
    public int getEndMonth() {
        return endMonth;
    }

    /**
     * Gets the starting year of the subscription.
     * @return The start year.
     */
    public int getStartYear() {
        return startYear;
    }

    /**
     * Calculates and returns the ending year of the one-year subscription.
     * @return The end year.
     */
    public int getEndYear() {
        return startYear + 1;
    }

    /**
     * Checks if a given month and year falls within the subscription period.
     *
     * @param checkMonth The month to check (1-12).
     * @param checkYear  The year to check.
     * @return true if the date is within the subscription period, false otherwise.
     */
    public boolean isWithinSubscription(int checkMonth, int checkYear) {
        // Basic validation for checkMonth
         if (checkMonth < 1 || checkMonth > 12) {
            throw new IllegalArgumentException("Check month must be between 1 and 12.");
        }

        int endYear = getEndYear();

        // Case 1: The check year is before the start year or after the end year.
        if (checkYear < startYear || checkYear > endYear) {
            return false;
        }

        // Case 2: The check year is the start year.
        if (checkYear == startYear) {
            // Must be in or after the start month.
            return checkMonth >= startMonth;
        }

        // Case 3: The check year is the end year.
        if (checkYear == endYear) {
            // Must be in or before the end month.
            return checkMonth <= endMonth;
        }

        // Case 4: The check year is strictly between the start year and the end year.
        // In a one-year subscription (startYear to startYear + 1), this case is only
        // possible if the subscription spans across two calendar years (e.g., starts Nov 2023, ends Oct 2024).
        // If checkYear is startYear + 1, it's within the period unless it's after the endMonth in the endYear.
        // The previous checks cover checkYear == startYear and checkYear == endYear.
        // For a one-year subscription, there are no years strictly *between* the start and end year.
        // This logic is simplified for a strict one-year duration.

        // The logic above should cover all cases for a one-year span.
        return false; // Should not be reached with the logic above, but as a fallback.
    }

    // You can add more utility methods here if needed, like calculating the number of months
    // remaining in the subscription from a given date.
}
