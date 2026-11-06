import java.util.*;

/**
 * Represents a player's two-card poker hand.
 * Contains logic for hand evaluation and odds calculation.
 */

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
    // Run simulated poker hands to estimate probabilities
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

            int playerScore = evaluateHandStrength(card1, card2, board);

            int bestOppScore = evaluateHandStrength(opponents.get(0)[0], opponents.get(0)[1], board);

            for (int j = 1; j < opponents.size(); j++) {
                Card[] opp = opponents.get(j);
                int score = evaluateHandStrength(opp[0], opp[1], board);
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

    // Calculates the rank of the player's poker hand
    private int evaluateHandStrength(Card c1, Card c2, List<Card> board){

        // Store all seven cards involved in the hand
        List<Card> cards = new ArrayList<>();

        cards.add(c1);
        cards.add(c2);
        cards.addAll(board);

        // Pass all cards to the hand-ranking algorithm
        return evaluateHand(cards);
    }

    // Determine the strongest hand category present in the seven cards
    private int evaluateHand(List<Card> cards){
        int[] rankCounts = new int[15];

        for(Card c : cards){
            rankCounts[c.getRank()]++;
        }

        boolean pair = false;
        boolean trips = false;
        boolean quads = false;

        int pairCount = 0;
        int highCard = 0;

        for(int rank = 2; rank <= 14; rank++){
            if(rankCounts[rank] > 0){
                highCard = rank;
            }

            if(rankCounts[rank] == 4){
                quads = true;
            }

            else if(rankCounts[rank] == 3){
                trips = true;
            }

            else if(rankCounts[rank] == 2){
                pair = true;
                pairCount++;
            }
        }

        if(quads){
            return 7000 + highCard;
        }

       else if(trips && pair){
            return 6000 + highCard;
       }

       else if(trips){
            return 4000 + highCard;
       }

       else if(pairCount >= 2){
            return 3000 + highCard;
       }

       else if(pair){
            return 2000 + highCard;
       }

        return highCard;
    }

    public String handCategory(){
        int r1 = card1.getRank();
        int r2 = card2.getRank();

        boolean pair = r1 == r2;
        boolean suited = card1.getSuit().equals(card2.getSuit());

        int high = Math.max(r1, r2);
        int low = Math.min(r1, r2);

        if ((pair && high >= 11) || (high == 14 && low == 13 && suited)){
            return "Premium";
        }

        else if ((pair && high >= 8) || (high == 14 && low >= 11) || (high == 13 && low >= 12 && suited)){
            return "Strong";
        }

        else if (pair || suited || (high - low) == 1) {
            return "Medium";
        }

        else {
            return "Weak";
        }
    }

    public String handAdvice(){
        switch(handCategory()){
            case "Premium":
                return "Excellent starting hand. Usually worth raising.";

            case "Strong":
                return "Strong hand with good winning potential.";

            case "Medium":
                return "Playable hand depending on position and opponents.";

            default:
                return "Weak starting hand. Consider folding.";
        }
    }
}

