import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class ActionManager {

    private static final Scanner scanner = new Scanner(System.in); //try and avoid scanner issues in chooseColour
    private static Player currentPlayer;
    private static boolean isDraw;
    private static boolean isSkipped;

    public static void setCurrentPlayer(Player player) {
        currentPlayer = player;
    } //kept getting NullPointerException with Menu.currentPlayer


    public static void skipNextPlayer() {
        setSkipped(true);
    }

    public static void reverseOrder(Player[] players) {
        System.out.println("Play direction is reversed!");
        //Reverse Array direction
    }

    public static void drawTwo(Player player) {
        //Called on current player if topCard is Draw 2?
        System.out.println(player.getPlayerName() + " must draw 2 cards. Your turn is skipped.");
        player.PlayerDrawsCard();
        player.PlayerDrawsCard();
    }

    public static void drawFourCheck(Player player) {
        List<Card> hand = player.getHand();
        List<Card> validCards = new ArrayList<>();

        for (Card c : hand) {
            // Skip any Draw Four cards
            if (c instanceof ActionCard ac) {
                if (ac.getAction() == ActionCard.Action.DRAW_FOUR) {
                    continue;
                }
            }

            // Check if card is valid and add to validCards array
            if (CardValidity.isValidCard(c)) {
                validCards.add(c);
            }

            if (!validCards.isEmpty()) {
                player.setCheater(true); //set Cheater to true if there are valid cards in the array
            }

        }

        if (player.isCheater()) {
            System.out.println(player.getPlayerName() + " is cheating! They played Draw Four with other valid cards."); //just to show if it works
        }
    }

    public static void drawFour(Player player) {

        System.out.println(player.getPlayerName() + " must draw 4 cards! Your turn is skipped.");
        for (int i = 0; i < 4; i++) {
            player.PlayerDrawsCard();
        }
    }

    public static Card.Colour chooseColour(Player player) {
        if (player instanceof HumanPlayer) {

            //Card.Colour chosen = null;
            System.out.println(player.getPlayerName() + ", choose a color (Red, Green, Blue, Yellow):");
            while (true) {
                String input = scanner.nextLine().trim().toUpperCase();
                try {
                    Card.Colour chosen = Card.Colour.valueOf(input);
                    if (chosen != Card.Colour.WILD) {  //return only if colour picked is not WILD
                        return chosen;
                    } else {
                        System.out.println("Cannot choose WILD. Pick from Red, Green, Blue, Yellow.");
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid input. Try again:");
                }
            }
        } else {
            // CompPlayer logic
            List<Card.Colour> coloursInHand = new ArrayList<>(); //new ArrayList for colours COM has in its hand

            for (Card card : player.getHand()) {
                Card.Colour colour = card.getColour();
                if (colour != Card.Colour.WILD && !coloursInHand.contains(colour)) { //add only if not another WILD card and colour isn't in the ArrayList yet
                    coloursInHand.add(colour);
                }
            }

            if (coloursInHand.isEmpty()) {
                return Card.Colour.RED; // in case COM has only WILD cards in hand. Possibly just placeholder
            }

            Random rand = new Random();
            return coloursInHand.get(rand.nextInt(coloursInHand.size())); //randomly selects colour from Array
        }
    }

    public static Player getCurrentPlayer() {
        return currentPlayer;
    }

    public static boolean isDraw() {
        return isDraw;
    }

    public static void setDraw(boolean draw) {
        isDraw = draw;
    }

    public static boolean isSkipped() {
        return isSkipped;
    }

    public static void setSkipped(boolean skipped) {
        isSkipped = skipped;
    }
}
