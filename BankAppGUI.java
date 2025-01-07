// BankAppGUI.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BankAppGUI {
    private JFrame frame;
    private JTextField accountNumberField, holderNameField, amountField;
    private JLabel balanceLabel;
    private BankAccount currentAccount;

    public BankAppGUI() {
        initializeGUI();
    }

    private void initializeGUI() {
        frame = new JFrame("Bank Management System");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));

        // Account details
        panel.add(new JLabel("Account Number:"));
        accountNumberField = new JTextField();
        panel.add(accountNumberField);

        panel.add(new JLabel("Account Holder Name:"));
        holderNameField = new JTextField();
        panel.add(holderNameField);

        // Balance
        panel.add(new JLabel("Current Balance:"));
        balanceLabel = new JLabel("0.0");
        panel.add(balanceLabel);

        // Transaction amount
        panel.add(new JLabel("Amount:"));
        amountField = new JTextField();
        panel.add(amountField);

        // Buttons
        JButton createAccountButton = new JButton("Create Account");
        createAccountButton.addActionListener(e -> createAccount());
        panel.add(createAccountButton);

        JButton depositButton = new JButton("Deposit");
        depositButton.addActionListener(e -> deposit());
        panel.add(depositButton);

        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.addActionListener(e -> withdraw());
        panel.add(withdrawButton);

        JButton transferButton = new JButton("Transfer");
        transferButton.addActionListener(e -> transfer());
        panel.add(transferButton);

        frame.add(panel);
        frame.setVisible(true);
    }

    private void createAccount() {
        String accountNumber = accountNumberField.getText();
        String holderName = holderNameField.getText();
        currentAccount = new BankAccount(accountNumber, holderName, 0.0);
        JOptionPane.showMessageDialog(frame, "Account Created Successfully!");
        updateBalance();
    }

    private void deposit() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            currentAccount.deposit(amount);
            JOptionPane.showMessageDialog(frame, "Deposit Successful!");
            updateBalance();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Error: " + e.getMessage());
        }
    }

    private void withdraw() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            currentAccount.withdraw(amount);
            JOptionPane.showMessageDialog(frame, "Withdrawal Successful!");
            updateBalance();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Error: " + e.getMessage());
        }
    }

    private void transfer() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            BankAccount recipient = new BankAccount("TEMP123", "Recipient", 0.0);
            currentAccount.transferTo(recipient, amount);
            JOptionPane.showMessageDialog(frame, "Transfer Successful!");
            updateBalance();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Error: " + e.getMessage());
        }
    }

    private void updateBalance() {
        if (currentAccount != null) {
            balanceLabel.setText(String.valueOf(currentAccount.getBalance()));
        }
    }

    public static void main(String[] args) {
        new BankAppGUI();
    }
}
=