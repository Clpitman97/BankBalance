package bank;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BankApp extends JFrame implements ActionListener {

    private BankAccount account;

    private JTextField startBalanceField;
    private JTextField amountField;
    private JLabel balanceLabel;

    private JButton createBtn;
    private JButton depositBtn;
    private JButton withdrawBtn;
    private JButton showBtn;
    private JButton exitBtn;

    public BankApp() {
        setTitle("Bank Balance");
        setSize(400, 220);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 5, 5));

        panel.add(new JLabel("Starting Balance:"));
        startBalanceField = new JTextField();
        panel.add(startBalanceField);

        createBtn = new JButton("Create Account");
        createBtn.addActionListener(this);
        panel.add(createBtn);

        showBtn = new JButton("Show Balance");
        showBtn.addActionListener(this);
        panel.add(showBtn);

        panel.add(new JLabel("Amount:"));
        amountField = new JTextField();
        panel.add(amountField);

        depositBtn = new JButton("Deposit");
        depositBtn.addActionListener(this);
        depositBtn.setEnabled(false);
        panel.add(depositBtn);

        withdrawBtn = new JButton("Withdraw");
        withdrawBtn.addActionListener(this);
        withdrawBtn.setEnabled(false);
        panel.add(withdrawBtn);

        balanceLabel = new JLabel("Balance: $0.00");
        panel.add(balanceLabel);

        exitBtn = new JButton("Exit");
        exitBtn.addActionListener(this);
        exitBtn.setEnabled(false);
        panel.add(exitBtn);

        add(panel);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == createBtn) {
            try {
                double start = Double.parseDouble(startBalanceField.getText());
                account = new BankAccount(start);
                updateBalance();
                depositBtn.setEnabled(true);
                withdrawBtn.setEnabled(true);
                exitBtn.setEnabled(true);
                createBtn.setEnabled(false);
                startBalanceField.setEnabled(false);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Enter starting balance.");
            }
        }

        else if (src == depositBtn) {
            try {
                double amt = Double.parseDouble(amountField.getText());
                account.deposit(amt);
                amountField.setText("");
                updateBalance();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Enter amount.");
            }
        }

        else if (src == withdrawBtn) {
            try {
                double amt = Double.parseDouble(amountField.getText());
                if (amt > account.getBalance()) {
                    JOptionPane.showMessageDialog(this, "Insufficient funds.");
                } else {
                    account.withdraw(amt);
                }
                amountField.setText("");
                updateBalance();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Enter amount.");
            }
        }

        else if (src == showBtn) {
            updateBalance();
        }

        else if (src == exitBtn) {
            JOptionPane.showMessageDialog(this,
                "Final Balance: $" + String.format("%.2f", account.getBalance()));
            System.exit(0);
        }
    }

    private void updateBalance() {
        if (account != null)
            balanceLabel.setText("Balance: $" + String.format("%.2f", account.getBalance()));
    }

    public static void main(String[] args) {
        new BankApp();
    }
}
