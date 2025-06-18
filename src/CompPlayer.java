import java.util.ArrayList;

public class CompPlayer extends Player {
    public CompPlayer(String name) {
        super(name);
    }

    @Override
    public void playCard() {
        ArrayList<Card> optionsDeck = null;
        for (Card card : this.hand) {
            if (CardValidity.isValidCard(card)) {
                optionsDeck.add(card);
            }
        }

        if (optionsDeck!=null) {
            //ersetze später mit randomized index
            Card cardToPlay = hand.get(0);
                hand.remove(0);
                DiscardPile.cardPlayed(cardToPlay);
            System.out.println(this.playerName + " has played: " +cardToPlay);
        }
    }
}
