package com.journalapp;

import com.journalapp.model.*; // Import all model classes
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class MainFrame extends JFrame {

    private final Distributor distributor; // Marked as final
    private final JTextArea outputArea;    // Marked as final

    // Input fields for adding Journal
    private JTextField journalNameField;
    private JTextField journalIssnField;
    private JTextField journalFrequencyField;
    private JTextField journalIssuePriceField;

    // Input fields for adding Individual Subscriber
    private JTextField individualNameField;
    private JTextField individualAddressField;
    private JTextField individualCCNrField;
    private JTextField individualExpireMonthField;
    private JTextField individualExpireYearField;
    private JTextField individualCCVField;

    // Input fields for adding Corporation Subscriber
    private JTextField corporationNameField;
    private JTextField corporationAddressField;
    private JTextField corporationBankCodeField;
    private JTextField corporationBankNameField;
    private JTextField corporationIssueDayField;
    private JTextField corporationIssueMonthField;
    private JTextField corporationIssueYearField;
    private JTextField corporationAccountNumberField;

    // Input fields for adding Subscription
    private JTextField subIssnField;
    private JTextField subSubscriberNameField; // Assuming search by name for simplicity
    private JTextField subStartMonthField;
    private JTextField subStartYearField;
    private JTextField subCopiesField;
    private JTextField subDiscountField;
    private JTextField subPaymentAmountField; // For accepting payment

    // Input fields for Listing/Reporting
    private JTextField listMonthField;
    private JTextField listYearField;
    private JTextField listIssnField;
    private JTextField listSubscriberNameField;
    private JTextField reportExpiryMonthField;
    private JTextField reportExpiryYearField;
    private JTextField reportStartYearField;
    private JTextField reportEndYearField;
    private JTextField stateFileNameField;


    public MainFrame() {
        // Initialize the Distributor
        distributor = new Distributor();

        // Set up the main frame
        setTitle("Journal Distribution Information System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null); // Center the window

        // Use a JTabbedPane to organize different functionalities
        JTabbedPane tabbedPane = new JTabbedPane();

        // Create panels for each function
        JPanel addPanel = createAddPanel();
        JPanel listPanel = createListPanel();
        JPanel reportPanel = createReportPanel();
        JPanel statePanel = createStatePanel();

        // Add panels to the tabbed pane
        tabbedPane.addTab("Add Data", addPanel);
        tabbedPane.addTab("List Data", listPanel);
        tabbedPane.addTab("Reports", reportPanel);
        tabbedPane.addTab("Save/Load State", statePanel);

        // Output area at the bottom
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setPreferredSize(new Dimension(780, 150));

        // Connect the output area to the distributor
        distributor.setOutputArea(outputArea);

        // Main content pane
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(tabbedPane, BorderLayout.CENTER);
        contentPane.add(scrollPane, BorderLayout.SOUTH);

        // Make the frame visible
        setVisible(true);
    }

    // --- Panel Creation Methods ---

    private JPanel createAddPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Padding
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Section for Adding Journal
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(new JLabel("Add New Journal"), gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        gbc.gridx = 0; panel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1; journalNameField = new JTextField(20); panel.add(journalNameField, gbc);

        gbc.gridy++;
        gbc.gridx = 0; panel.add(new JLabel("ISSN:"), gbc);
        gbc.gridx = 1; journalIssnField = new JTextField(20); panel.add(journalIssnField, gbc);

        gbc.gridy++;
        gbc.gridx = 0; panel.add(new JLabel("Frequency:"), gbc);
        gbc.gridx = 1; journalFrequencyField = new JTextField(20); panel.add(journalFrequencyField, gbc);

        gbc.gridy++;
        gbc.gridx = 0; panel.add(new JLabel("Issue Price:"), gbc);
        gbc.gridx = 1; journalIssuePriceField = new JTextField(20); panel.add(journalIssuePriceField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        JButton addJournalButton = new JButton("Add Journal");
        addJournalButton.addActionListener(new AddJournalActionListener());
        panel.add(addJournalButton, gbc);

        // Separator
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        panel.add(new JSeparator(), gbc);

        // Section for Adding Individual Subscriber
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        panel.add(new JLabel("Add New Individual Subscriber"), gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        gbc.gridx = 0; panel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1; individualNameField = new JTextField(20); panel.add(individualNameField, gbc);

        gbc.gridy++;
        gbc.gridx = 0; panel.add(new JLabel("Address:"), gbc);
        gbc.gridx = 1; individualAddressField = new JTextField(20); panel.add(individualAddressField, gbc);

        gbc.gridy++;
        gbc.gridx = 0; panel.add(new JLabel("Credit Card Nr:"), gbc);
        gbc.gridx = 1; individualCCNrField = new JTextField(20); panel.add(individualCCNrField, gbc);

        gbc.gridy++;
        gbc.gridx = 0; panel.add(new JLabel("Expiry Month:"), gbc);
        gbc.gridx = 1; individualExpireMonthField = new JTextField(20); panel.add(individualExpireMonthField, gbc);

        gbc.gridy++;
        gbc.gridx = 0; panel.add(new JLabel("Expiry Year:"), gbc);
        gbc.gridx = 1; individualExpireYearField = new JTextField(20); panel.add(individualExpireYearField, gbc);

        gbc.gridy++;
        gbc.gridx = 0; panel.add(new JLabel("CCV:"), gbc);
        gbc.gridx = 1; individualCCVField = new JTextField(20); panel.add(individualCCVField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        JButton addIndividualButton = new JButton("Add Individual Subscriber");
        addIndividualButton.addActionListener(new AddIndividualActionListener());
        panel.add(addIndividualButton, gbc);

         // Separator
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        panel.add(new JSeparator(), gbc);

        // Section for Adding Corporation Subscriber
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        panel.add(new JLabel("Add New Corporation Subscriber"), gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        gbc.gridx = 0; panel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1; corporationNameField = new JTextField(20); panel.add(corporationNameField, gbc);

        gbc.gridy++;
        gbc.gridx = 0; panel.add(new JLabel("Address:"), gbc);
        gbc.gridx = 1; corporationAddressField = new JTextField(20); panel.add(corporationAddressField, gbc);

        gbc.gridy++;
        gbc.gridx = 0; panel.add(new JLabel("Bank Code:"), gbc);
        gbc.gridx = 1; corporationBankCodeField = new JTextField(20); panel.add(corporationBankCodeField, gbc);

        gbc.gridy++;
        gbc.gridx = 0; panel.add(new JLabel("Bank Name:"), gbc);
        gbc.gridx = 1; corporationBankNameField = new JTextField(20); panel.add(corporationBankNameField, gbc);

        gbc.gridy++;
        gbc.gridx = 0; panel.add(new JLabel("Issue Day:"), gbc);
        gbc.gridx = 1; corporationIssueDayField = new JTextField(20); panel.add(corporationIssueDayField, gbc);

        gbc.gridy++;
        gbc.gridx = 0; panel.add(new JLabel("Issue Month:"), gbc);
        gbc.gridx = 1; corporationIssueMonthField = new JTextField(20); panel.add(corporationIssueMonthField, gbc);

        gbc.gridy++;
        gbc.gridx = 0; panel.add(new JLabel("Issue Year:"), gbc);
        gbc.gridx = 1; corporationIssueYearField = new JTextField(20); panel.add(corporationIssueYearField, gbc);

        gbc.gridy++;
        gbc.gridx = 0; panel.add(new JLabel("Account Number:"), gbc);
        gbc.gridx = 1; corporationAccountNumberField = new JTextField(20); panel.add(corporationAccountNumberField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        JButton addCorporationButton = new JButton("Add Corporation Subscriber");
        addCorporationButton.addActionListener(new AddCorporationActionListener());
        panel.add(addCorporationButton, gbc);

        // Separator
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        panel.add(new JSeparator(), gbc);

        // Section for Adding Subscription
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        panel.add(new JLabel("Add New Subscription"), gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        gbc.gridx = 0; panel.add(new JLabel("Journal ISSN:"), gbc);
        gbc.gridx = 1; subIssnField = new JTextField(20); panel.add(subIssnField, gbc);

        gbc.gridy++;
        gbc.gridx = 0; panel.add(new JLabel("Subscriber Name:"), gbc); // Using name for simplicity
        gbc.gridx = 1; subSubscriberNameField = new JTextField(20); panel.add(subSubscriberNameField, gbc);

        gbc.gridy++;
        gbc.gridx = 0; panel.add(new JLabel("Start Month (1-12):"), gbc);
        gbc.gridx = 1; subStartMonthField = new JTextField(20); panel.add(subStartMonthField, gbc);

        gbc.gridy++;
        gbc.gridx = 0; panel.add(new JLabel("Start Year:"), gbc);
        gbc.gridx = 1; subStartYearField = new JTextField(20); panel.add(subStartYearField, gbc);

        gbc.gridy++;
        gbc.gridx = 0; panel.add(new JLabel("Copies:"), gbc);
        gbc.gridx = 1; subCopiesField = new JTextField(20); panel.add(subCopiesField, gbc);

        gbc.gridy++;
        gbc.gridx = 0; panel.add(new JLabel("Discount Ratio (0.0-1.0):"), gbc);
        gbc.gridx = 1; subDiscountField = new JTextField(20); panel.add(subDiscountField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        JButton addSubscriptionButton = new JButton("Add Subscription");
        addSubscriptionButton.addActionListener(new AddSubscriptionActionListener());
        panel.add(addSubscriptionButton, gbc);

         // Separator
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        panel.add(new JSeparator(), gbc);

        // Section for Accepting Payment
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        panel.add(new JLabel("Accept Payment for Subscription"), gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        gbc.gridx = 0; panel.add(new JLabel("Journal ISSN:"), gbc);
        gbc.gridx = 1; final JTextField payIssnField = new JTextField(20); panel.add(payIssnField, gbc); // Marked as final

        gbc.gridy++;
        gbc.gridx = 0; panel.add(new JLabel("Subscriber Name:"), gbc); // Using name for simplicity
        gbc.gridx = 1; final JTextField paySubscriberNameField = new JTextField(20); panel.add(paySubscriberNameField, gbc); // Marked as final

        gbc.gridy++;
        gbc.gridx = 0; panel.add(new JLabel("Payment Amount:"), gbc);
        gbc.gridx = 1; final JTextField payPaymentAmountField = new JTextField(20); panel.add(payPaymentAmountField, gbc); // Marked as final

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        JButton acceptPaymentButton = new JButton("Accept Payment");
        acceptPaymentButton.addActionListener(new AcceptPaymentActionListener(payIssnField, paySubscriberNameField, payPaymentAmountField));
        panel.add(acceptPaymentButton, gbc);


        return panel;
    }

    private JPanel createListPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Section for Listing Sending Orders
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(new JLabel("List Sending Orders"), gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        gbc.gridx = 0; panel.add(new JLabel("Month (1-12):"), gbc);
        gbc.gridx = 1; listMonthField = new JTextField(20); panel.add(listMonthField, gbc);

        gbc.gridy++;
        gbc.gridx = 0; panel.add(new JLabel("Year:"), gbc);
        gbc.gridx = 1; listYearField = new JTextField(20); panel.add(listYearField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        JButton listAllOrdersButton = new JButton("List All Sending Orders");
        listAllOrdersButton.addActionListener(new ListAllSendingOrdersActionListener());
        panel.add(listAllOrdersButton, gbc);

        gbc.gridy++;
        gbc.gridx = 0; panel.add(new JLabel("Journal ISSN (for specific journal):"), gbc);
        gbc.gridx = 1; listIssnField = new JTextField(20); panel.add(listIssnField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        JButton listJournalOrdersButton = new JButton("List Sending Orders for Journal");
        listJournalOrdersButton.addActionListener(new ListJournalSendingOrdersActionListener());
        panel.add(listJournalOrdersButton, gbc);

        // Separator
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        panel.add(new JSeparator(), gbc);

        // Section for Listing Subscriptions
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        panel.add(new JLabel("List Subscriptions"), gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        gbc.gridx = 0; panel.add(new JLabel("Subscriber Name:"), gbc);
        gbc.gridx = 1; listSubscriberNameField = new JTextField(20); panel.add(listSubscriberNameField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        JButton listSubscriberSubsButton = new JButton("List Subscriptions by Subscriber");
        listSubscriberSubsButton.addActionListener(new ListSubscriberSubscriptionsActionListener());
        panel.add(listSubscriberSubsButton, gbc);

        gbc.gridy++;
        gbc.gridx = 0; panel.add(new JLabel("Journal ISSN:"), gbc);
        gbc.gridx = 1; final JTextField listSubsIssnField = new JTextField(20); panel.add(listSubsIssnField, gbc); // Marked as final

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        JButton listJournalSubsButton = new JButton("List Subscriptions by Journal");
        listJournalSubsButton.addActionListener(new ListJournalSubscriptionsActionListener(listSubsIssnField));
        panel.add(listJournalSubsButton, gbc);

        // Separator
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        panel.add(new JSeparator(), gbc);

        // Section for Listing Incomplete Payments
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        JButton listIncompletePaymentsButton = new JButton("List Subscriptions with Incomplete Payments");
        listIncompletePaymentsButton.addActionListener(new ListIncompletePaymentsActionListener());
        panel.add(listIncompletePaymentsButton, gbc);


        return panel;
    }

    private JPanel createReportPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(new JLabel("Generate Report"), gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        gbc.gridx = 0; panel.add(new JLabel("Subscriptions Expiring After (Month):"), gbc);
        gbc.gridx = 1; reportExpiryMonthField = new JTextField(20); panel.add(reportExpiryMonthField, gbc);

        gbc.gridy++;
        gbc.gridx = 0; panel.add(new JLabel("Subscriptions Expiring After (Year):"), gbc);
        gbc.gridx = 1; reportExpiryYearField = new JTextField(20); panel.add(reportExpiryYearField, gbc);

        gbc.gridy++;
        gbc.gridx = 0; panel.add(new JLabel("Annual Payments Start Year:"), gbc);
        gbc.gridx = 1; reportStartYearField = new JTextField(20); panel.add(reportStartYearField, gbc);

        gbc.gridy++;
        gbc.gridx = 0; panel.add(new JLabel("Annual Payments End Year:"), gbc);
        gbc.gridx = 1; reportEndYearField = new JTextField(20); panel.add(reportEndYearField, gbc);


        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        JButton generateReportButton = new JButton("Generate Report");
        generateReportButton.addActionListener(new GenerateReportActionListener());
        panel.add(generateReportButton, gbc);


        return panel;
    }

    private JPanel createStatePanel() {
         JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(new JLabel("Save/Load Distributor State"), gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        gbc.gridx = 0; panel.add(new JLabel("File Name:"), gbc);
        gbc.gridx = 1; stateFileNameField = new JTextField("distributor_state.ser", 20); panel.add(stateFileNameField, gbc); // Default file name


        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        JButton saveStateButton = new JButton("Save State");
        saveStateButton.addActionListener(new SaveStateActionListener());
        panel.add(saveStateButton, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        JButton loadStateButton = new JButton("Load State");
        loadStateButton.addActionListener(new LoadStateActionListener());
        panel.add(loadStateButton, gbc);


        return panel;
    }

    // --- Action Listener Classes ---

    private class AddJournalActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String name = journalNameField.getText();
                String issn = journalIssnField.getText();
                int frequency = Integer.parseInt(journalFrequencyField.getText());
                double issuePrice = Double.parseDouble(journalIssuePriceField.getText());

                Journal journal = new Journal(name, issn, frequency, issuePrice);
                distributor.addJournal(journal);

                // Clear fields after adding
                journalNameField.setText("");
                journalIssnField.setText("");
                journalFrequencyField.setText("");
                journalIssuePriceField.setText("");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(MainFrame.this, "Invalid number format. Please enter valid numbers for Frequency and Issue Price.", "Input Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                 JOptionPane.showMessageDialog(MainFrame.this, ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                 JOptionPane.showMessageDialog(MainFrame.this, "An unexpected error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                 ex.printStackTrace();
            }
        }
    }

    private class AddIndividualActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String name = individualNameField.getText();
                String address = individualAddressField.getText();
                String creditCardNr = individualCCNrField.getText();
                int expireMonth = Integer.parseInt(individualExpireMonthField.getText());
                int expireYear = Integer.parseInt(individualExpireYearField.getText());
                int CCV = Integer.parseInt(individualCCVField.getText());

                Individual individual = new Individual(name, address, creditCardNr, expireMonth, expireYear, CCV);
                distributor.addSubscriber(individual);

                // Clear fields
                individualNameField.setText("");
                individualAddressField.setText("");
                individualCCNrField.setText("");
                individualExpireMonthField.setText("");
                individualExpireYearField.setText("");
                individualCCVField.setText("");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(MainFrame.this, "Invalid number format. Please enter valid numbers for expiry month, year, and CCV.", "Input Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                 JOptionPane.showMessageDialog(MainFrame.this, ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                 JOptionPane.showMessageDialog(MainFrame.this, "An unexpected error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                 ex.printStackTrace();
            }
        }
    }

    private class AddCorporationActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String name = corporationNameField.getText();
                String address = corporationAddressField.getText();
                int bankCode = Integer.parseInt(corporationBankCodeField.getText());
                String bankName = corporationBankNameField.getText();
                int issueDay = Integer.parseInt(corporationIssueDayField.getText());
                int issueMonth = Integer.parseInt(corporationIssueMonthField.getText());
                int issueYear = Integer.parseInt(corporationIssueYearField.getText());
                int accountNumber = Integer.parseInt(corporationAccountNumberField.getText());

                Corporation corporation = new Corporation(name, address, bankCode, bankName, issueDay, issueMonth, issueYear, accountNumber);
                distributor.addSubscriber(corporation);

                // Clear fields
                corporationNameField.setText("");
                corporationAddressField.setText("");
                corporationBankCodeField.setText("");
                corporationBankNameField.setText("");
                corporationIssueDayField.setText("");
                corporationIssueMonthField.setText("");
                corporationIssueYearField.setText("");
                corporationAccountNumberField.setText("");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(MainFrame.this, "Invalid number format. Please enter valid numbers for bank code, issue date components, and account number.", "Input Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                 JOptionPane.showMessageDialog(MainFrame.this, ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                 JOptionPane.showMessageDialog(MainFrame.this, "An unexpected error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                 ex.printStackTrace();
            }
        }
    }

    private class AddSubscriptionActionListener implements ActionListener {
         @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String issn = subIssnField.getText();
                String subscriberName = subSubscriberNameField.getText();
                int startMonth = Integer.parseInt(subStartMonthField.getText());
                int startYear = Integer.parseInt(subStartYearField.getText());
                int copies = Integer.parseInt(subCopiesField.getText());
                double discountRatio = Double.parseDouble(subDiscountField.getText());

                // Find the journal and subscriber first
                Journal journal = distributor.searchJournal(issn);
                Subscriber subscriber = distributor.searchSubscriber(subscriberName); // Note: searchSubscriber finds by name

                if (journal == null) {
                    JOptionPane.showMessageDialog(MainFrame.this, "Journal with ISSN " + issn + " not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                 if (subscriber == null) {
                    JOptionPane.showMessageDialog(MainFrame.this, "Subscriber with name " + subscriberName + " not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Create DateInfo
                DateInfo dates = new DateInfo(startMonth, startYear);

                // Create the Subscription object
                Subscription subscription = new Subscription(dates, copies, journal, subscriber, discountRatio);

                // Add the subscription via the distributor
                distributor.addSubscription(issn, subscriber, subscription);

                // Clear fields
                subIssnField.setText("");
                subSubscriberNameField.setText("");
                subStartMonthField.setText("");
                subStartYearField.setText("");
                subCopiesField.setText("");
                subDiscountField.setText("");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(MainFrame.this, "Invalid number format. Please enter valid numbers for dates, copies, and discount.", "Input Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                 JOptionPane.showMessageDialog(MainFrame.this, ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                 JOptionPane.showMessageDialog(MainFrame.this, "An unexpected error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                 ex.printStackTrace();
            }
        }
    }

     private class AcceptPaymentActionListener implements ActionListener {
        private final JTextField issnField; // Marked as final
        private final JTextField subscriberNameField; // Marked as final
        private final JTextField amountField; // Marked as final

        public AcceptPaymentActionListener(JTextField issnField, JTextField subscriberNameField, JTextField amountField) {
            this.issnField = issnField;
            this.subscriberNameField = subscriberNameField;
            this.amountField = amountField;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String issn = issnField.getText();
                String subscriberName = subscriberNameField.getText();
                double amount = Double.parseDouble(amountField.getText());

                // Find the subscription
                Subscription targetSubscription = null;
                for (Subscription sub : distributor.getAllSubscriptions()) {
                    if (sub.getJournal().getIssn().equals(issn) && sub.getSubscriber().getName().equals(subscriberName)) {
                        targetSubscription = sub;
                        break;
                    }
                }

                if (targetSubscription != null) {
                    targetSubscription.acceptPayment(amount);
                    outputArea.append("Payment of " + String.format("%.2f", amount) + " accepted for subscription to " + targetSubscription.getJournal().getName() + " by " + targetSubscription.getSubscriber().getName() + ".\n");
                    issnField.setText("");
                    subscriberNameField.setText("");
                    amountField.setText("");
                } else {
                    JOptionPane.showMessageDialog(MainFrame.this, "Subscription not found for the given Journal ISSN and Subscriber Name.", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(MainFrame.this, "Invalid number format for Payment Amount.", "Input Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                 JOptionPane.showMessageDialog(MainFrame.this, ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                 JOptionPane.showMessageDialog(MainFrame.this, "An unexpected error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                 ex.printStackTrace();
            }
        }
    }


    private class ListAllSendingOrdersActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int month = Integer.parseInt(listMonthField.getText());
                int year = Integer.parseInt(listYearField.getText());

                String result = distributor.listAllSendingOrders(month, year);
                outputArea.append(result);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(MainFrame.this, "Invalid number format for Month or Year.", "Input Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                 JOptionPane.showMessageDialog(MainFrame.this, ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                 JOptionPane.showMessageDialog(MainFrame.this, "An unexpected error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                 ex.printStackTrace();
            }
        }
    }

    private class ListJournalSendingOrdersActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String issn = listIssnField.getText();
                int month = Integer.parseInt(listMonthField.getText());
                int year = Integer.parseInt(listYearField.getText());

                String result = distributor.listSendingOrdersByJournalIssn(issn, month, year);
                outputArea.append(result);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(MainFrame.this, "Invalid number format for Month or Year.", "Input Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                 JOptionPane.showMessageDialog(MainFrame.this, ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                 JOptionPane.showMessageDialog(MainFrame.this, "An unexpected error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                 ex.printStackTrace();
            }
        }
    }

    private class ListIncompletePaymentsActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String result = distributor.listIncompletePayments();
                outputArea.append(result);
            } catch (Exception ex) {
                 JOptionPane.showMessageDialog(MainFrame.this, "An unexpected error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                 ex.printStackTrace();
            }
        }
    }

    private class ListSubscriberSubscriptionsActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String subscriberName = listSubscriberNameField.getText();
                String result = distributor.listSubscriptionsBySubscriberName(subscriberName);
                outputArea.append(result);
            } catch (Exception ex) {
                 JOptionPane.showMessageDialog(MainFrame.this, "An unexpected error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                 ex.printStackTrace();
            }
        }
    }

    private class ListJournalSubscriptionsActionListener implements ActionListener {
        private final JTextField issnField; // Marked as final

        public ListJournalSubscriptionsActionListener(JTextField issnField) {
            this.issnField = issnField;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String issn = issnField.getText();
                String result = distributor.listSubscriptionsByJournalIssn(issn);
                outputArea.append(result);
            } catch (Exception ex) {
                 JOptionPane.showMessageDialog(MainFrame.this, "An unexpected error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                 ex.printStackTrace();
            }
        }
    }


    private class GenerateReportActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int expiryMonth = Integer.parseInt(reportExpiryMonthField.getText());
                int expiryYear = Integer.parseInt(reportExpiryYearField.getText());
                int startReportYear = Integer.parseInt(reportStartYearField.getText());
                int endReportYear = Integer.parseInt(reportEndYearField.getText());

                // Create Calendar for expiry date threshold (assuming 1st of the month)
                Calendar expiryDateThreshold = new GregorianCalendar(expiryYear, expiryMonth - 1, 1); // Month is 0-indexed

                // The report method runs in a separate thread
                distributor.report(expiryDateThreshold, startReportYear, endReportYear);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(MainFrame.this, "Invalid number format for report dates/years.", "Input Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                 JOptionPane.showMessageDialog(MainFrame.this, ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                 JOptionPane.showMessageDialog(MainFrame.this, "An unexpected error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                 ex.printStackTrace();
            }
        }
    }

    private class SaveStateActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String fileName = stateFileNameField.getText();
            if (fileName == null || fileName.trim().isEmpty()) {
                 JOptionPane.showMessageDialog(MainFrame.this, "Please enter a file name to save the state.", "Input Error", JOptionPane.ERROR_MESSAGE);
                 return;
            }
            distributor.saveState(fileName);
        }
    }

    private class LoadStateActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
             String fileName = stateFileNameField.getText();
            if (fileName == null || fileName.trim().isEmpty()) {
                 JOptionPane.showMessageDialog(MainFrame.this, "Please enter a file name to load the state from.", "Input Error", JOptionPane.ERROR_MESSAGE);
                 return;
            }
            distributor.loadState(fileName);
        }
    }


    // --- Main Method ---
    public static void main(String[] args) {
        // Run the GUI creation on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame();
            }
        });
    }
}
