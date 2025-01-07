import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class RegistrationPage {
    private JFrame frame;
    private JTextField accountNumberField, holderNameField, loginAccountNumberField;
    private final String FILE_NAME = "accounts.txt"; // File to store account details

    public RegistrationPage() {
        initializeGUI();
    }

    private void initializeGUI() {
        frame = new JFrame("Bank Management System - Registration & Login");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Title
        JLabel titleLabel = new JLabel("Bank Management System", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Tabs for Registration and Login
        JTabbedPane tabbedPane = new JTabbedPane();

        // Registration Panel
        JPanel registerPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        registerPanel.add(new JLabel("Account Number:"));
        accountNumberField = new JTextField();
        registerPanel.add(accountNumberField);

        registerPanel.add(new JLabel("Account Holder Name:"));
        holderNameField = new JTextField();
        registerPanel.add(holderNameField);

        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(e -> registerAccount());
        registerPanel.add(registerButton);

        tabbedPane.addTab("Register", registerPanel);

        // Login Panel
        JPanel loginPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        loginPanel.add(new JLabel("Account Number:"));
        loginAccountNumberField = new JTextField();
        loginPanel.add(loginAccountNumberField);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> login());
        loginPanel.add(loginButton);

        tabbedPane.addTab("Login", loginPanel);

        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private void registerAccount() {
        String accountNumber = accountNumberField.getText().trim();
        String holderName = holderNameField.getText().trim();

        if (accountNumber.isEmpty() || holderName.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please provide valid account details.");
            return;
        }

        if (isAccountExists(accountNumber)) {
            JOptionPane.showMessageDialog(frame, "Account already exists!");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(accountNumber + "," + holderName + ",0.0");
            writer.newLine();
            JOptionPane.showMessageDialog(frame, "Account Registered Successfully!");
            accountNumberField.setText("");
            holderNameField.setText("");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error saving account: " + e.getMessage());
        }
    }

    private void login() {
        String accountNumber = loginAccountNumberField.getText().trim();

        if (accountNumber.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter your account number.");
            return;
        }

        BankAccount account = loadAccountFromFile(accountNumber);

        if (account == null) {
            JOptionPane.showMessageDialog(frame, "Account not found. Please register first.");
        } else {
            JOptionPane.showMessageDialog(frame, "Login Successful!");
            frame.dispose(); // Close the registration/login page
            new BankAppGUI(account); // Open the main banking page
        }
    }

    private boolean isAccountExists(String accountNumber) {
        return loadAccountFromFile(accountNumber) != null;
    }

    private BankAccount loadAccountFromFile(String accountNumber) {
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return null; // File doesn't exist, no accounts stored yet
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3 && parts[0].equals(accountNumber)) {
                    String holderName = parts[1];
                    double balance = Double.parseDouble(parts[2]);
                    return new BankAccount(accountNumber, holderName, balance);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error reading account file: " + e.getMessage());
        }
        return null; // Account not found
    }

    public static void main(String[] args) {
        new RegistrationPage();
    }
}
