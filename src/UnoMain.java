import java.util.ArrayList;
import java.util.Scanner;

public class UnoMain {
    public static void main(String[] args) {
        CardDeck helpDeck = new CardDeck();
        ArrayList<Card> deck = helpDeck.createDeck();
        ArrayList<Card> discardPile = new ArrayList<>();
        ArrayList<Player> players = playerAmount(deck); //method returns an ArrayList of players
        discardPile.add(deck.remove(0)); //starting discard Pile by moving the first card of the deck to it
        Card topCard = discardPile.get(0);

        while (true) {
            for (Player player : players) {
                System.out.println("\n" + player.getName() + "'s turn:");
                Card playedCard = player.playCard(topCard);
                discardPile.add(playedCard);
                topCard = playedCard;
                System.out.println(player.getName() + " played: " + playedCard);
            }
        }
    }

public static void dealCards(Player player, ArrayList<Card> deck) {
    for (int i = 0; i < 7; i++) {
        player.drawCard(deck.remove(0));
    }
}

public static ArrayList<Player> playerAmount(ArrayList<Card> deck) {
    Scanner input = new Scanner(System.in);
    int people;

    while (true) {
        System.out.println("How many people are playing? (1-4)");
        people = input.nextInt();

        if (people >= 1 && people <= 4) {
            break;
        }
        System.out.println("Invalid number of players. Please enter between 2 and 4.");
    }

    ArrayList<Player> players = new ArrayList<>(); //Array List for Players


    for (int i = 1; i <= people; i++) { //for the amount of Players
        System.out.println("Enter name for Player " + i + ": ");
        String name = input.next();
        players.add(new HumanPlayer(name));
    }


    for (Player player : players) { //Dealing 7 cards to each player from the main deck
        dealCards(player, deck);
    }

    // Showing each player's hand to see if it works
    // for (Player player : players) {
    // System.out.println(player.getName() + "'s hand: " + player.getHand());
    // }
    return players;
}
}

