/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blackjackgame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 *
 * @author saharamichaeladosdos
 */
public class LoginGUI extends JFrame implements ActionListener {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;

    public LoginGUI() {
        setTitle("Login");
        setLayout(new GridLayout(3, 2));

        // Create UI components
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        loginButton = new JButton("Login");
        registerButton = new JButton("Register");

        // Add action listeners
        loginButton.addActionListener(this);
        registerButton.addActionListener(e -> {
            new RegisterGUI();
            dispose(); // Close the login window
        });

        // Add components to the frame
        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(loginButton);
        add(registerButton);

        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null); // Center on screen
    }

@Override
public void actionPerformed(ActionEvent e) {
    String username = usernameField.getText();
    String password = new String(passwordField.getPassword());

    boolean loggedIn = DatabaseManager.loginUser(username, password);
    if (loggedIn) {
        JOptionPane.showMessageDialog(this, "Login successful! Welcome, " + username + "!");
        
        // Launch the Blackjack game GUI
        SwingUtilities.invokeLater(() -> {
            try {
                System.out.println("Launching BlackJackGUI2...");
                new BlackJackGUI2().setVisible(true); // Ensure it is visible
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error launching the game: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        dispose(); // Close the login window
    } else {
        JOptionPane.showMessageDialog(this, "Invalid username or password. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}

}
