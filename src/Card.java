public class Card {
    private int rank;
    private String suit;

    public Card(int rank, String suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public int getRank() {return rank;}
    public String getSuit() {return suit;}

    public void setRank(int rank) {this.rank = rank;}
    public void setSuit(String suit) {this.suit = suit;}

    @Override
    public String toString() {
        String rankName;
        switch (rank) {
            case 11: rankName = "Jack"; break;
            case 12: rankName = "Queen"; break;
            case 13: rankName = "King"; break;
            case 14: rankName = "Ace"; break;
            default: rankName = String.valueOf(rank);
        }

        String suitName;
        switch (suit) {
            case "c": suitName = "Clubs"; break;
            case "d": suitName = "Diamonds"; break;
            case "h": suitName = "Hearts"; break;
            case "s": suitName = "Spades"; break;
            default: suitName = suit;
        }

        return rankName + " of " + suitName;
    }
}
