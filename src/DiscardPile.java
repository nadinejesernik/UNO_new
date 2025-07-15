import java.util.ArrayList;

public class DiscardPile {
    private static ArrayList<Card> discardPile = new ArrayList<>();

    public static void cardPlayed(Card card) {
        discardPile.add(card); // wird von Playerklassen aufgerufen
    }

    public static Card showTopCard() {
        return discardPile.getLast();
    }

    public static ArrayList<Card> returnDiscardPile() {
        ArrayList<Card> emptyList = new ArrayList<>();
        ArrayList<Card> returnPile = discardPile;
        discardPile = emptyList; // könnte hier auch .clear() benutzen aber funktioniert = wird nicht mehr angegriffen
        discardPile.add(returnPile.removeLast()); //neuer discardPile bekommt die letzte karte des alten discardPile (also die TopCard) wieder hinzugefügt
        return returnPile;
    }

    public static void clearDiscardPile() {
        discardPile.clear();
    }
}
