import java.util.ArrayList;
import java.util.Collections;

public class CardDeck {
    private static ArrayList<Card> currentDeck = new ArrayList<>();

    public static void buildFreshDeck() {
        currentDeck = DeckInitializer.initializeDeck();
        Collections.shuffle(currentDeck);
    }

    public static ArrayList<Card> createHand() {
        final int handSize = 7;
        ArrayList<Card> hand = new ArrayList<>();
        for (int i = 0; i < handSize; i++) {
            hand.add(currentDeck.getFirst());
            currentDeck.removeFirst();
        }
        return hand;
    }

    public static Card drawCard() {
        if (currentDeck.size() == 0) {
            reshuffleDeck(DiscardPile.returnDiscardPile());
        }

        Card drawn = currentDeck.getFirst();
        currentDeck.removeFirst();

        if (drawn.getColour() == Card.Colour.WILD) {
            drawn.wildColor = null;
        }
        return drawn;
    }

    public static void reshuffleDeck(ArrayList<Card> discardPile) {
        Collections.shuffle(discardPile);
        currentDeck = discardPile;
    }
}
