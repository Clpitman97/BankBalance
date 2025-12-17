package bank;

import javax.swing.*;
import java.awt.*;

public class BankApp extends JFrame {

    private BankAccount account;
    private String userName;

    // Name components - disappears after entry
    private JLabel nameLabel;
    private JTextField nameField;

    // Starting balance
    private JLabel startBalanceLabel;
    private JTextField startBalanceField;

    // Deposit
    private JLabel depositLabel;
    private JTextField depositField;
    private JButton depositBtn;

    // Withdraw
    private JLabel withdrawLabel;
    private JTextField withdrawField;
    private JButton withdrawBtn;

    // Balance, exit
    private JLabel balanceLabel;
    private JButton exitBtn;

    public BankApp() {
        // Improvement: JFrame configuration organized in one location
        setTitle("Bank Balance");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Main layout
        JPanel main = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Name row
        nameLabel = new JLabel("Name:");
        nameField = new JTextField(18);

        // Improvement: Name is stored after enter
        nameField.addActionListener(e -> handleNameEntered());

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        main.add(nameLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1;
        gbc.gridwidth = 3;
        main.add(nameField, gbc);
        gbc.gridwidth = 1;

        // Starting Balance
        startBalanceLabel = new JLabel("Enter Starting Balance:");
        startBalanceField = new JTextField(18);
        startBalanceField.setEnabled(false); // entry locked until name entered

        // Improvement: Account is created when starting balance is entered
        startBalanceField.addActionListener(e -> handleStartingBalanceEntered());

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        main.add(startBalanceLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1;
        gbc.gridwidth = 3;
        main.add(startBalanceField, gbc);
        gbc.gridwidth = 1;

        // Deposit / Withdraw
        depositLabel = new JLabel("Deposit Amount:");
        depositField = new JTextField(10);
        depositField.setEnabled(false);

        depositBtn = new JButton("Deposit");
        depositBtn.setEnabled(false);
        depositBtn.addActionListener(e -> handleDeposit());

        withdrawLabel = new JLabel("Withdrawal Amount:");
        withdrawField = new JTextField(10);
        withdrawField.setEnabled(false);

        withdrawBtn = new JButton("Withdraw");
        withdrawBtn.setEnabled(false);
        withdrawBtn.addActionListener(e -> handleWithdraw());

        // Deposit
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        main.add(depositLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 2; gbc.weightx = 1;
        main.add(depositField, gbc);

        // Withdraw
        gbc.gridx = 2; gbc.gridy = 2; gbc.weightx = 0;
        main.add(withdrawLabel, gbc);

        gbc.gridx = 3; gbc.gridy = 2; gbc.weightx = 1;
        main.add(withdrawField, gbc);

        // Buttons under deposit/withdraw
        gbc.gridx = 1; gbc.gridy = 3; gbc.weightx = 0;
        main.add(depositBtn, gbc);

        gbc.gridx = 3; gbc.gridy = 3; gbc.weightx = 0;
        main.add(withdrawBtn, gbc);

        // Balance/Exit
        balanceLabel = new JLabel("Current Balance: $0.00");

        exitBtn = new JButton("Exit");
        exitBtn.setEnabled(false);
        exitBtn.addActionListener(e -> handleExit());

        gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 1;
        gbc.gridwidth = 3;
        main.add(balanceLabel, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 3; gbc.gridy = 4; gbc.weightx = 0;
        main.add(exitBtn, gbc);

        // Final frame setup
        setContentPane(main);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // User input: Name
    private void handleNameEntered() {
        userName = nameField.getText().trim();

        if (userName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your name.");
            return;
        }

        // Improvement: Dynamic window title using user's name
        setTitle(userName + "'s Bank Balance");

        // Hide name entry and entered
        nameLabel.setVisible(false);
        nameField.setVisible(false);

        // Unlocks starting balance field
        startBalanceField.setEnabled(true);
        startBalanceField.requestFocusInWindow();

        revalidate();
        repaint();
    }

    // User input: Starting balance
    private void handleStartingBalanceEntered() {
        try {
            double start = Double.parseDouble(startBalanceField.getText().trim());

            account = new BankAccount(start);
            updateBalance();

            // Greys out starting balance
            startBalanceField.setEnabled(false);

            // All other fields unlocked
            depositField.setEnabled(true);
            withdrawField.setEnabled(true);
            depositBtn.setEnabled(true);
            withdrawBtn.setEnabled(true);
            exitBtn.setEnabled(true);

            depositField.requestFocusInWindow();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Enter a valid starting balance.");
        }
    }

    private void handleDeposit() {
        if (account == null) return;

        try {
            double amt = Double.parseDouble(depositField.getText().trim());
            account.deposit(amt);
            depositField.setText("");
            updateBalance();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Enter a valid deposit amount.");
        }
    }

    private void handleWithdraw() {
        if (account == null) return;

        try {
            double amt = Double.parseDouble(withdrawField.getText().trim());

            if (amt > account.getBalance()) {
                JOptionPane.showMessageDialog(this, "Insufficient funds.");
            } else {
                account.withdraw(amt);
            }

            withdrawField.setText("");
            updateBalance();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Enter a valid withdrawal amount.");
        }
    }

    private void handleExit() {
        if (account != null) {
            JOptionPane.showMessageDialog(this,
                    "Final Balance: $" + String.format("%.2f", account.getBalance()));
        }
        System.exit(0);
    }

    private void updateBalance() {
        if (account != null) {
            balanceLabel.setText("Current Balance: $" + String.format("%.2f", account.getBalance()));
        }
    }

    public static void main(String[] args) {
        new BankApp();
    }
}
