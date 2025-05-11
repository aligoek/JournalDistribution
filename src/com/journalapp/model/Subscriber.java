package com.journalapp.model;

import java.io.Serializable;

public abstract class Subscriber implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String address;

    public Subscriber(String name, String address) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Subscriber name cannot be null or empty.");
        }
         if (address == null || address.trim().isEmpty()) {
            throw new IllegalArgumentException("Subscriber address cannot be null or empty.");
        }
        this.name = name;
        this.address = address;
    }

    public abstract String getBillingInformation();

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }
}
