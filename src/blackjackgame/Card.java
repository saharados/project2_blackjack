/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blackjackgame;

public class Card {
    private String rank;
    private String suit;

    public Card(String rank, String suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public String getRank() {
        return rank; // Return the rank of the card
    }

    public String getSuit() {
        return suit; // Return the suit of the card
    }

    @Override
    public String toString() {
        return rank + " of " + suit; // This will be used in showHand
    }

    public String getImagePath() {
        // Return the path to the image based on rank and suit
        return "/com/mycompany/blackjack/cards/" + rank + "-" + suit + ".png";
    }
}
