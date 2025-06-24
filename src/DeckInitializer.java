import java.util.ArrayList;

public class DeckInitializer {
    public static ArrayList<Card> initializeDeck() {
        ArrayList<Card> deck = new ArrayList<>();

        for (Card.Colour colour : Card.Colour.values()) {
            if (colour != Card.Colour.WILD) {
                deck.add(new NumbersCard(colour, 0)); //add 0 card once for each colour

                for (int i = 1; i <= 9; i++) {
                    deck.add(new NumbersCard(colour, i));
                    deck.add(new NumbersCard(colour, i));
                } //add cards 1-9 for each colour twice

                for (ActionCard.Action action : ActionCard.Action.values()) {
                    if (action != ActionCard.Action.WILD && action != ActionCard.Action.DRAW_FOUR) {
                        deck.add(new ActionCard(colour, action));
                        deck.add(new ActionCard(colour, action));
                    }
                } //add action cards for each colour twice
            }
        }
        for (int i = 0; i < 4; i++) {
            deck.add(new ActionCard(Card.Colour.WILD, ActionCard.Action.WILD));
            deck.add(new ActionCard(Card.Colour.WILD, ActionCard.Action.DRAW_FOUR));
        }
        return deck;
    }
}
