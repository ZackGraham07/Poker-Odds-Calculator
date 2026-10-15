import java.util.*;

public class Hand {
    private Card card1;
    private Card card2;

    public Hand(Card card1, Card card2) {
        this.card1 = card1;
        this.card2 = card2;
    }

    public String toString() {
        return "The " + card1 + " and the " + card2;
    }

    public Card getCard1() {return card1;}

    public Card getCard2() {return card2;}

    public void setCard1(Card card1) {this.card1 = card1;}

    public void setCard2(Card card2) {this.card2 = card2;}

    public double calcWinOdds(int numPlayers) {
        int[] result = runCalc(numPlayers);
        return (result[0] * 100.0) / result[3];   // wins / total
    }

    public double calculateLosingOdds(int numPlayers) {
        int[] result = runCalc(numPlayers);
        return (result[1] * 100.0) / result[3];   // losses / total
    }

    public double calculateTyingOdds(int numPlayers) {
        int[] result = runCalc(numPlayers);
        return (result[2] * 100.0) / result[3];   // ties / total
    }

    private int[] runCalc(int numPlayers) {
        int sims = 20000; // Number of simulations
        int wins = 0;
        int losses = 0;
        int ties = 0;
        int total = 0;

        Random rand = new Random();
        for (int i = 0; i < sims; i++) {
            Deck deck = new Deck();
            deck.removeCard(card1);
            deck.removeCard(card2);

            List<Card[]> opponents = new ArrayList<>();
            for (int j = 0; j < numPlayers - 1; j++) {
                Card oppCard1 = deck.drawRandomCard(rand);
                Card oppCard2 = deck.drawRandomCard(rand);
                opponents.add(new Card[]{oppCard1, oppCard2});
            }

            List<Card> board = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                board.add(deck.drawRandomCard(rand));
            }

            int playerScore = evaluate(card1, card2, board);

            int bestOppScore = evaluate(opponents.get(0)[0], opponents.get(0)[1], board);

            for (int j = 1; j < opponents.size(); j++) {
                Card[] opp = opponents.get(j);
                int score = evaluate(opp[0], opp[1], board);
                if (score > bestOppScore) bestOppScore = score;
            }

            if (playerScore > bestOppScore) {
                wins++;
            } else if (playerScore < bestOppScore) {
                losses++;
            } else {
                ties++;
            }

        }
        total = wins + losses + ties;
        return new int[]{wins, ties, losses, total};
    }

    private int evaluate(Card c1, Card c2, List<Card> board) {
        int score = c1.getRank() + c2.getRank();
        for (Card c : board) score += c.getRank();
        return score;
    }
}

