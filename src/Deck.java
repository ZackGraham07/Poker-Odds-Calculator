import java.util.*;

public class Deck {
    private List<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        for (String suit : suits) {
            for (int rank = 2; rank <= 14; rank++) { // 2-10, J=11, Q=12, K=13, A=14
                cards.add(new Card(rank, suit));
            }
        }
    }

    public void removeCard(Card c) {
        for (int i = 0; i < cards.size(); i++) {
            Card card = cards.get(i);
            if (card.getRank() == c.getRank() && card.getSuit().equals(c.getSuit())) {
                cards.remove(i);
                break;
            }
        }
    }

    public Card drawRandomCard(Random rand) {
        int index = rand.nextInt(cards.size());
        return cards.remove(index);
    }
}
