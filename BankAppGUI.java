
import javax.swing.*;
import java.awt.*;

public class BankAppGUI {
    private JFrame frame;
    private JTextField amountField;
    private JLabel balanceLabel;
    private BankAccount currentAccount;

    // Constructor to accept BankAccount
    public BankAppGUI(BankAccount account) {
        this.currentAccount = account; // Initialize the account
        initializeGUI();
    }

    private void initializeGUI() {
        frame = new JFrame("Bank Management System");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));

        // Display account details
        panel.add(new JLabel("Account Holder:"));
        panel.add(new JLabel(currentAccount.getAccountHolder()));

        panel.add(new JLabel("Account Number:"));
        panel.add(new JLabel(currentAccount.getAccountNumber()));

        // Balance
        panel.add(new JLabel("Current Balance:"));
        balanceLabel = new JLabel(String.valueOf(currentAccount.getBalance()));
        panel.add(balanceLabel);

        // Transaction amount
        panel.add(new JLabel("Amount:"));
        amountField = new JTextField();
        panel.add(amountField);

        // Buttons
        JButton depositButton = new JButton("Deposit");
        depositButton.addActionListener(e -> deposit());
        panel.add(depositButton);

        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.addActionListener(e -> withdraw());
        panel.add(withdrawButton);

        frame.add(panel);
        frame.setVisible(true);
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

    private void updateBalance() {
        balanceLabel.setText(String.valueOf(currentAccount.getBalance()));
    }
}
