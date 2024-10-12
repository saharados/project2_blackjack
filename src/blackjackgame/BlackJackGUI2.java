package blackjackgame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class BlackJackGUI2 extends JFrame {
    private JTextArea gameDisplay;
    private JButton hitButton, standButton, quitButton, newGameButton; // Added New Game button
    private Deck deck;
    private Player player;
    private Dealer dealer;
    private BlackJack game;
    private JPanel cardPanel;
    private JLabel playerCardLabel1, playerCardLabel2, dealerCardLabel1, dealerCardLabel2;
    private boolean dealerCardHidden = true; // Tracks if the dealer's second card is hidden
    private int score = 0; // Player's score variable
    private JLabel scoreLabel; // Label to display the score

    public BlackJackGUI2() {
        setTitle("Blackjack Game");
        setSize(600, 400); // Adjusted size to accommodate images
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Set the background color of the main window
        getContentPane().setBackground(new Color(53, 101, 77)); // Green background like a casino table

        // Game display area
        gameDisplay = new JTextArea();
        gameDisplay.setEditable(false);
        gameDisplay.setBackground(new Color(53, 101, 77)); // Lighter green for display
        gameDisplay.setForeground(Color.WHITE); // White text for visibility
        add(new JScrollPane(gameDisplay), BorderLayout.CENTER);

        // Panel for buttons
        JPanel buttonPanel = new JPanel();
        hitButton = new JButton("Hit");
        standButton = new JButton("Stand");
        quitButton = new JButton("Quit");  // New Quit button
        newGameButton = new JButton("New Game"); // New Game button

        buttonPanel.add(hitButton);
        buttonPanel.add(standButton);
        buttonPanel.add(newGameButton); // Add New Game button to panel
        buttonPanel.add(quitButton);  // Add Quit button to panel
        buttonPanel.setBackground(new Color(34, 139, 34)); // Same as the window background
        add(buttonPanel, BorderLayout.SOUTH);

        // Panel for card images
        cardPanel = new JPanel();
        cardPanel.setLayout(new GridLayout(2, 2, 10, 10)); // 2 rows, 2 columns with gaps
        playerCardLabel1 = new JLabel();
        playerCardLabel2 = new JLabel();
        dealerCardLabel1 = new JLabel();
        dealerCardLabel2 = new JLabel();
        cardPanel.add(playerCardLabel1);
        cardPanel.add(playerCardLabel2);
        cardPanel.add(dealerCardLabel1);
        cardPanel.add(dealerCardLabel2);
        cardPanel.setBackground(new Color(34, 139, 34)); // Match background

        add(cardPanel, BorderLayout.NORTH);

        // Score label to display current score
        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setForeground(Color.WHITE); // Set the score label text color
        add(scoreLabel, BorderLayout.EAST); // Add score label to the right side of the window

        // Initialize game components
        deck = new Deck();
        player = new Player("Player");
        dealer = new Dealer();
        game = new BlackJack(deck, player, dealer);

        // Add event listeners
        hitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playerHit();
            }
        });

        standButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playerStand();
            }
        });

        newGameButton.addActionListener(new ActionListener() { // New Game button action listener
            @Override
            public void actionPerformed(ActionEvent e) {
                startNewGame();
            }
        });

        quitButton.addActionListener(new ActionListener() {  // Quit button action listener
            @Override
            public void actionPerformed(ActionEvent e) {
                quitGame();
            }
        });

        startNewGame();
        setLocationRelativeTo(null);
    }

    private void startNewGame() {
        game.start();
        dealerCardHidden = true; // Reset hidden card state
        updateDisplay();
        updateCardImages();
        hitButton.setEnabled(true);
        standButton.setEnabled(true);
    }

    private void playerHit() {
        game.playerHit();
        if (player.isBusted()) {
            endGame("You Busted! Dealer Wins!");
        } else {
            updateDisplay();
            updateCardImages();
        }
    }

    private void playerStand() {
        game.playerStand();
        dealerCardHidden = false; // Reveal the dealer's hidden card
        endGame(game.getResult());
    }

    private void endGame(String result) {
        gameDisplay.append("\n" + result);
        hitButton.setEnabled(false);
        standButton.setEnabled(false);
        updateCardImages(); // Show final cards
        
        // Update score based on the result
        if (result.contains("Player Wins")) {
            score += 5; // Increase score by 5 if player wins
        } else if (result.contains("Dealer Wins")) {
            score -= 5; // Decrease score by 5 if dealer wins
        }
        
        scoreLabel.setText("Score: " + score); // Update score label
    }

    private void updateDisplay() {
        gameDisplay.setText(game.getGameState());
    }

    // Quit the current game and show "Thanks for Playing" window
    private void quitGame() {
        // Create a new ScoreDisplayGUI instance to show the player's score
        new ScoreDisplayGUI(score);

        // Optionally, disable buttons or perform any cleanup before closing the main game window
        hitButton.setEnabled(false);
        standButton.setEnabled(false);
        
        // Close the current game window after a short delay (optional)
        Timer timer = new Timer(3000, new ActionListener() { // Wait for 3 seconds
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the main game window
            }
        });
        timer.setRepeats(false); // Only run once
        timer.start(); // Start the timer
    }

    // Update the displayed card images for both the player and dealer
    private void updateCardImages() {
        int width = 100;  // Set appropriate width
        int height = 150; // Set appropriate height

        // Update player card images
        if (!player.getHand().isEmpty()) {
            Card playerCard1 = player.getHand().get(0);
            String playerCardPath1 = "/resources/cards/" + getRankCode(playerCard1.getRank()) + "-" + getSuitCode(playerCard1.getSuit()) + ".png";
            ImageIcon playerIcon1 = new ImageIcon(new ImageIcon(getClass().getResource(playerCardPath1)).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
            playerCardLabel1.setIcon(playerIcon1);

            if (player.getHand().size() > 1) {
                Card playerCard2 = player.getHand().get(1);
                String playerCardPath2 = "/resources/cards/" + getRankCode(playerCard2.getRank()) + "-" + getSuitCode(playerCard2.getSuit()) + ".png";
                ImageIcon playerIcon2 = new ImageIcon(new ImageIcon(getClass().getResource(playerCardPath2)).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
                playerCardLabel2.setIcon(playerIcon2);
            }
        }

        // Update dealer card images
        if (!dealer.getHand().isEmpty()) {
            Card dealerCard1 = dealer.getHand().get(0);
            String dealerCardPath1 = "/resources/cards/" + getRankCode(dealerCard1.getRank()) + "-" + getSuitCode(dealerCard1.getSuit()) + ".png";
            ImageIcon dealerIcon1 = new ImageIcon(new ImageIcon(getClass().getResource(dealerCardPath1)).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
            dealerCardLabel1.setIcon(dealerIcon1);

            if (dealerCardHidden) {
                // Show back of card for hidden dealer card
                ImageIcon dealerHiddenIcon = new ImageIcon(new ImageIcon(getClass().getResource("/resources/cards/BACK.png")).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
                dealerCardLabel2.setIcon(dealerHiddenIcon);
            } else {
                // Show actual dealer card when revealed
                Card dealerCard2 = dealer.getHand().get(1);
                String dealerCardPath2 = "/resources/cards/" + getRankCode(dealerCard2.getRank()) + "-" + getSuitCode(dealerCard2.getSuit()) + ".png";
                ImageIcon dealerIcon2 = new ImageIcon(new ImageIcon(getClass().getResource(dealerCardPath2)).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
                dealerCardLabel2.setIcon(dealerIcon2);
            }
        }
    }

    // Helper method to convert rank names to corresponding codes
    private String getRankCode(String rank) {
        switch (rank) {
            case "Ace":
                return "A";
            case "2":
                return "2";
            case "3":
                return "3";
            case "4":
                return "4";
            case "5":
                return "5";
            case "6":
                return "6";
            case "7":
                return "7";
            case "8":
                return "8";
            case "9":
                return "9";
            case "10":
                return "10";
            case "Jack":
                return "J";
            case "Queen":
                return "Q";
            case "King":
                return "K";
            default:
                return "";
        }
    }

    // Helper method to convert suit names to corresponding codes
    private String getSuitCode(String suit) {
        switch (suit) {
            case "Hearts":
                return "H";
            case "Diamonds":
                return "D";
            case "Clubs":
                return "C";
            case "Spades":
                return "S";
            default:
                return "";
        }
    }

    // Main method to run the GUI
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new BlackJackGUI2().setVisible(true);
            }
        });
    }
}
