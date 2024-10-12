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
public class RegisterGUI extends JFrame implements ActionListener {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton registerButton;
    private JButton backButton;

    public RegisterGUI() {
        setTitle("Register");
        setLayout(new GridLayout(3, 2));

        // Create UI components
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        registerButton = new JButton("Register");
        backButton = new JButton("Back");

        // Add action listeners
        registerButton.addActionListener(this);
        backButton.addActionListener(e -> {
            new LoginGUI(); // Go back to login
            dispose(); // Close the register window
        });

        // Add components to the frame
        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(registerButton);
        add(backButton);

        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null); // Center on screen
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        boolean registered = DatabaseManager.registerUser(username, password);
        if (registered) {
            JOptionPane.showMessageDialog(this, "Registration successful! You can now log in.");
            new LoginGUI(); // Go back to login
            dispose(); // Close the register window
        } else {
            JOptionPane.showMessageDialog(this, "Registration failed. Please try a different username.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
