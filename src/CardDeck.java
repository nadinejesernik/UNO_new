import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class CardDeck {

    public ArrayList<Card> createDeck() {
        ArrayList<Card> deck = new ArrayList<>();

        String[] colours = {"Red", "Blue", "Green", "Yellow" };
        String[] values = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
        String[] actions = {"Skip", "Reverse", "Draw Two" };

        for (String colour : colours) {
            deck.add(new Card(colour, "0")); //add 0 card once for each colour

            for (String value : Arrays.copyOfRange(values, 1, values.length)) {
                deck.add(new Card(colour, value));
                deck.add(new Card(colour, value));
            } //add cards 1-9 for each colour twice

            for (String action : actions) {
                deck.add(new Card(colour, action));
                deck.add(new Card(colour, action));
            } //add action cards for each colour twice
        }
        for (int i = 0; i < 4; i++) {
            deck.add(new Card("Wild", "Wild"));
            deck.add(new Card("Wild", "Draw Four"));
        }

        Collections.shuffle(deck);
        return deck;
    }

}
