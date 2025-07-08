import java.util.ArrayList;

public class DiscardPile {
    private static ArrayList<Card> discardPile = new ArrayList<>();

    public static void cardPlayed(Card card) {
        discardPile.add(card); // -- mit player funktion des kartenspielens verbunden
    }

    public static Card showTopCard() {
        return discardPile.getLast();
    }

    public static ArrayList<Card> returnDiscardPile() {
        ArrayList<Card> emptyList = new ArrayList<>();
        ArrayList<Card> returnPile = discardPile;
        discardPile = emptyList;
        discardPile.add(returnPile.removeLast());
        return returnPile; // --  mit dem CardDeck verbunden
    }

    public static void clearDiscardPile() {
        discardPile.clear();
    }
}
