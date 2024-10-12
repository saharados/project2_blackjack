package blackjackgame;



public class BlackJack {
    private Deck deck;
    private Player player;
    private Dealer dealer;
    private int playerScore;  // Track player's score

    public BlackJack(Deck deck, Player player, Dealer dealer) {
        if (deck == null || player == null || dealer == null) {
            throw new IllegalArgumentException("Deck, Player, and Dealer cannot be null");
        }
        
        this.deck = deck;
        this.player = player;
        this.dealer = dealer;
        this.playerScore = 0; // Initialize score
    }

    public void start() {
        deck.shuffle();
        player.clearHand();
        dealer.clearHand();
        player.addCard(deck.drawCard());
        player.addCard(deck.drawCard());
        dealer.addCard(deck.drawCard());
        dealer.addCard(deck.drawCard());
    }

    public void playerHit() {
        if (deck != null && player != null) {
            player.addCard(deck.drawCard());
        } else {
            System.out.println("Cannot hit, deck or player is not initialized.");
        }
    }

    public void playerStand() {
        while (dealer.shouldHit()) {
            dealer.addCard(deck.drawCard());
        }
    }

    public String getResult() {
        if (player.isBusted()) {
            return "Player Busted! Dealer Wins!";
        } else if (dealer.isBusted()) {
            playerScore += 1;  // Player wins when dealer busts
            return "Dealer Busted! Player Wins!";
        } else if (player.getHandValue() > dealer.getHandValue()) {
            playerScore += 1;  // Player wins
            return "Player Wins!";
        } else if (player.getHandValue() < dealer.getHandValue()) {
            return "Dealer Wins!";
        } else {
            return "It's a Tie!";
        }
    }

    public String getGameState() {
        return "Player Hand: " + player.showHand() + " (" + player.getHandValue() + ")\n" +
               "Dealer Hand: " + dealer.showHand() + " (" + dealer.getHandValue() + ")";
    }

    public int getPlayerScore() {
        return playerScore;  // Return player's current score
    }

    public void resetPlayerScore() {
        playerScore = 0;  // Reset score when needed
    }
}
