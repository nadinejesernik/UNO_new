import java.util.*;

public class ActionManager {

    private static final Scanner scanner = new Scanner(System.in); //try and avoid scanner issues in chooseColour
    private static Player currentPlayer;
    private static boolean isDraw;
    private static boolean isSkipped;
    private static ArrayList<Player> players;
    private static boolean isClockwise = true;
    private static int currentIndex = 0;
    private static Player drawFourInstigator = null;
    private static List<Card> drawFourHandSnapshot = new ArrayList<>();


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
        int step = isClockwise ? 1 : -1; //depending on isClockwise, turn is either advanced forward or backwards
        currentIndex = (currentIndex + step + players.size()) % players.size();
    }

    public static void drawTwo(Player player) {
        //Called on current player if topCard is Draw 2
        System.out.println(player.getPlayerName() + " must draw 2 cards. Your turn is skipped.");
        System.out.println("_____________");
        player.PlayerDrawsCard();
        player.PlayerDrawsCard();
    }

    public static void drawFourCheck(Player player) {
        List<Card> handBeforeDrawFour = getDrawFourHandSnapshot();
        List<Card> validCards = new ArrayList<>();

        for (Card c : handBeforeDrawFour) {
            if (c instanceof ActionCard ac && ac.getAction() == ActionCard.Action.DRAW_FOUR) {
                continue; // skip other Draw Four cards
            }

            if (CardValidity.isValidCard(c)) {
                validCards.add(c);
            }
        }

        if (!validCards.isEmpty()) {
            player.setCheater(true);
        }

        clearDrawFourHandSnapshot(); // Clean up after check
    }


    public static void drawFour(Player player) {

        System.out.println(player.getPlayerName() + " must draw 4 cards! Your turn is skipped.");
        System.out.println("_____________");
        for (int i = 0; i < 4; i++) {
            player.PlayerDrawsCard();
        }
    }

    public static Card.Colour chooseColour(Player player) {
        if (player instanceof HumanPlayer) {

            //Card.Colour chosen = null;
            System.out.println(player.getPlayerName() + ", choose a color (" + "\u001B[31m" + "Red" + "\u001B[0m" + ", " +
                    "\u001B[32m" + "Green" + "\u001B[0m" + ", " + "\u001B[34m" + "Blue" + "\u001B[0m" + ", " +
                    "\u001B[33m" + "Yellow" + "\u001B[0m" + "):");
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

    public static boolean isIsClockwise() {
        return isClockwise;
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
        drawFourHandSnapshot = new ArrayList<>(hand); // make a copy
    }

    public static List<Card> getDrawFourHandSnapshot() {
        return drawFourHandSnapshot;
    }

    public static void clearDrawFourHandSnapshot() {
        drawFourHandSnapshot.clear();
    }


}
