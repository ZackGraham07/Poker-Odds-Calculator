import java.util.*;

/**
 * Represents a standard 52-card deck.
 * Supports card removal and random card drawing
 */

public class Deck {
    private List<Card> cards;

    // Generate all 52 cards in the deck
    public Deck() {
        cards = new ArrayList<>();
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        for (String suit : suits) {
            for (int rank = 2; rank <= 14; rank++) { // 2-10, J=11, Q=12, K=13, A=14
                cards.add(new Card(rank, suit));
            }
        }
    }

    // Remove a known card so it cannot appear again
    public void removeCard(Card c) {
        for (int i = 0; i < cards.size(); i++) {
            Card card = cards.get(i);
            if (card.getRank() == c.getRank() && card.getSuit().equals(c.getSuit())) {
                cards.remove(i);
                break;
            }
        }
    }

    // Draw a random card from the deck and remove it
    public Card drawRandomCard(Random rand) {
        int index = rand.nextInt(cards.size());
        return cards.remove(index);
    }
}
