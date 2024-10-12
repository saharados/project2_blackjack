/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blackjackgame;


import java.awt.BorderLayout;
import javax.swing.*;

public class ScoreDisplayGUI extends JFrame {
    public ScoreDisplayGUI(int score) {
        setTitle("Your Score");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        // Create a label to display the score
        JLabel scoreLabel = new JLabel("Your final score is: " + score);
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        scoreLabel.setFont(scoreLabel.getFont().deriveFont(20f)); // Increase font size

        // Create a button to close the application
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> System.exit(0)); // Close the application

        // Add components to the frame
        add(scoreLabel, BorderLayout.CENTER);
        add(closeButton, BorderLayout.SOUTH);

        setVisible(true); // Make the frame visible
    }
}

