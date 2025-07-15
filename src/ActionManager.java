import java.util.*;

public class ActionManager {

    private static final Scanner scanner = new Scanner(System.in);
    private static Player currentPlayer;
    private static boolean isDraw;
    private static boolean isSkipped;
    private static ArrayList<Player> players;
    private static boolean isClockwise = true;
    private static int currentIndex = 0;
    private static Player drawFourInstigator = null;
    private static List<Card> drawFourHandSnapshot = new ArrayList<>();
    private static Card drawFourTopCard = null; // Um die Top Card vor DrawFour Card zu speichern

    public static void setCurrentPlayer(Player player) {
        currentPlayer = player;
    }

    public static void initializePlayers(ArrayList<Player> playerList) {
        players = playerList;
    }

    public static void skipNextPlayer() {
        setSkipped(true);
    }

    public static void reverseOrder() {
        isClockwise = !isClockwise;
        System.out.println("Turn order reversed! Now playing " + (isClockwise ? "clockwise" : "counter-clockwise"));
    }

    public static void advanceTurn() {
        int step = isClockwise ? 1 : -1;
        currentIndex = (currentIndex + step + players.size()) % players.size();
    }

    public static void drawTwo(Player player) {
        System.out.println(player.getPlayerName() + " must draw 2 cards. Your turn is skipped.");
        System.out.println("_____________");
        player.PlayerDrawsCard();
        player.PlayerDrawsCard();
    }

    public static void drawFourCheck(Player player) {
        List<Card> handBeforeDrawFour = getDrawFourHandSnapshot();
        Card topCardAtDrawFour = getDrawFourTopCard();

        if (CardDeck.wasJustDrawn(DiscardPile.showTopCard())) {
            clearDrawFourHandSnapshot();
            return; // Wenn DrawFour Karte gerade erst gezogen wurde kann es kein schummeln sein sie zu legen
        }

        for (Card c : handBeforeDrawFour) {
            if (c instanceof ActionCard ac && ac.getAction() == ActionCard.Action.DRAW_FOUR) {
                continue; // andere DrawFour Karten werden nicht beachtet
            }

            if (CardValidity.isValidCardAgainst(c, topCardAtDrawFour)) {
                player.setCheater(true);
                break;
            }
        }

        clearDrawFourHandSnapshot(); // Liste wird zurückgesetzt
    }

    public static Card.Colour chooseColour(Player player) {
        if (player instanceof HumanPlayer) {
            System.out.println(player.getPlayerName() + ", choose a color (" + "\u001B[31m" + "Red" + "\u001B[0m" + ", " +
                    "\u001B[32m" + "Green" + "\u001B[0m" + ", " + "\u001B[34m" + "Blue" + "\u001B[0m" + ", " +
                    "\u001B[33m" + "Yellow" + "\u001B[0m" + "):");
            while (true) {
                String input = scanner.nextLine().trim().toUpperCase();
                try {
                    Card.Colour chosen = Card.Colour.valueOf(input);
                    if (chosen != Card.Colour.WILD) {
                        return chosen;
                    } else {
                        System.out.println("Cannot choose WILD. Pick from Red, Green, Blue, Yellow.");
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid input. Try again:");
                }
            }
        } else { //COM Player Logik
            List<Card.Colour> coloursInHand = new ArrayList<>();
            for (Card card : player.getHand()) {
                Card.Colour colour = card.getColour();
                if (colour != Card.Colour.WILD && !coloursInHand.contains(colour)) {
                    coloursInHand.add(colour);
                }
            }

            if (coloursInHand.isEmpty()) {
                return Card.Colour.RED; //Hardcoded Auswahl falls COM keine Farbkarten auf der Hand hat (sollte nicht oft vorkommen)
            }

            Random rand = new Random();
            return coloursInHand.get(rand.nextInt(coloursInHand.size()));
        }
    }

    public static Player getCurrentPlayer() {
        return players.get(currentIndex);
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

    public static void setIsClockwise(boolean isClockwise) {
        ActionManager.isClockwise = isClockwise;
    }

    public static void setDrawFourInstigator(Player player) {
        drawFourInstigator = player;
    }

    public static Player getDrawFourInstigator() {
        return drawFourInstigator;
    }

    public static void clearDrawFourInstigator() {
        drawFourInstigator = null;
    }

    public static void setDrawFourHandSnapshot(List<Card> hand) {
        drawFourHandSnapshot = new ArrayList<>(hand);
    }

    public static List<Card> getDrawFourHandSnapshot() {
        return drawFourHandSnapshot;
    }

    public static void clearDrawFourHandSnapshot() {
        drawFourHandSnapshot.clear();
    }

    public static void setDrawFourTopCard(Card card) {
        drawFourTopCard = card;
    }

    public static Card getDrawFourTopCard() {
        return drawFourTopCard;
    }
}
