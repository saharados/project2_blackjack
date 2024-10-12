package blackjackgame;


import java.util.ArrayList;
import java.util.List;

public class Dealer {
    private List<Card> hand;

    public Dealer() {
        hand = new ArrayList<>(); // Initialize hand
    }

    public void clearHand() {
        hand.clear(); // Clear the dealer's hand
    }

    public void addCard(Card card) {
        if (card != null) {
            hand.add(card); // Add card to dealer's hand
        } else {
            throw new IllegalArgumentException("Card cannot be null");
        }
    }

    public List<Card> getHand() {
        return hand; // Return dealer's hand
    }

    public boolean shouldHit() {
        return getHandValue() < 17; // Dealer hits if hand value is less than 17
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
        return getHandValue() > 21; // Check if the dealer is busted
    }

    public String showHand() {
        if (hand.isEmpty()) {
            return "No cards in hand"; // Handle empty hand
        }
        return hand.toString(); // String representation of the dealer's hand
    }
}
