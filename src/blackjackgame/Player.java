package blackjackgame;


import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private List<Card> hand;

    public Player(String name) {
        this.name = name;
        hand = new ArrayList<>(); // Initialize hand
    }

    public void clearHand() {
        hand.clear(); // Clear the player's hand
    }

    public void addCard(Card card) {
        if (card != null) {
            hand.add(card); // Add card to player's hand
        } else {
            throw new IllegalArgumentException("Card cannot be null");
        }
    }

    public List<Card> getHand() {
        return hand; // Return player's hand
    }

    public int getHandValue() {
        int value = 0;
        int aces = 0;

        for (Card card : hand) {
            String rank = card.getRank();
            if ("King".equals(rank) || "Queen".equals(rank) || "Jack".equals(rank)) {
                value += 10;
            } else if ("Ace".equals(rank)) {
                aces++;
                value += 11; // Count Ace as 11 initially
            } else {
                value += Integer.parseInt(rank); // Add numeric value
            }
        }

        // Adjust for Aces
        while (value > 21 && aces > 0) {
            value -= 10; // Convert Ace from 11 to 1
            aces--;
        }

        return value;
    }

    public boolean isBusted() {
        return getHandValue() > 21; // Check if the player is busted
    }

    public String showHand() {
        if (hand.isEmpty()) {
            return "No cards in hand"; // Handle empty hand
        }
        return hand.toString(); // String representation of the player's hand
    }
}
