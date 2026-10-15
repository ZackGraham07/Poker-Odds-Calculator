import java.io.*;
import java.util.*;

public class PlayHand {
    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to the Poker Odds Calculator!");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of players in the game: ");
        int numPlayers = scanner.nextInt();
        if (numPlayers < 2) {
            System.out.println("Number of players must be at least 2.");
            return;
        }

        System.out.print("Enter the rank of the first card (2-14, 11=Jack, 12=Queen, 13=King, 14=Ace): ");
        int rank1 = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter the suit of the first card (Clubs(c), Diamonds(d), Hearts(h), Spades(s)): ");
        String suit1 = scanner.nextLine();
        System.out.print("Enter the rank of the second card (2-14, 11=Jack, 12=Queen, 13=King, 14=Ace): ");
        int rank2 = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter the suit of the second card (Clubs(c), Diamonds(d), Hearts(h), Spades(s)): ");
        String suit2 = scanner.nextLine();

        // Create the player's hand
        Card card1 = new Card(rank1, suit1);
        Card card2 = new Card(rank2, suit2);
        Hand playerHand = new Hand(card1, card2);

        System.out.println("Player's hand: " + playerHand);
        System.out.println("Now calculating odds against " + (numPlayers - 1) + " opponent(s)...");
        System.out.println("Winning odds: " + playerHand.calcWinOdds(numPlayers) + "%");
        System.out.println("Losing odds: " + playerHand.calculateLosingOdds(numPlayers) + "%");
        System.out.println("Tying odds: " + playerHand.calculateTyingOdds(numPlayers) + "%");
        System.out.println("Thank you for using the Poker Odds Calculator!");
    }
}
