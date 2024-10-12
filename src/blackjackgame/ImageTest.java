package blackjackgame;

import javax.swing.*;
import java.awt.*;

public class ImageTest {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Test Images");
        frame.setLayout(new GridLayout(1, 2)); // Example for 2 images side by side

        // Array of image paths - Update paths to match your resources structure
        String[] imagePaths = {
            "/resources/cards/5-C.png",     // For 5 of Clubs
            "/resources/cards/10-H.png",    // For 10 of Hearts
            "/resources/cards/BACK.png"     // For the back of the card
        };

        for (String path : imagePaths) {
            JLabel label = new JLabel();
            ImageIcon icon = new ImageIcon(ImageTest.class.getResource(path));

            if (icon.getIconWidth() < 0) {
                System.err.println("Image not found: " + path);
            } else {
                label.setIcon(icon);
            }

            frame.add(label);
        }

        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Center the frame
        frame.setVisible(true);
    }
}
