import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class CompPlayer extends Player {

    private int attention;
    private final Random rand = new Random();

    public CompPlayer(String name) {
        super(name);
        attention = rand.nextInt(5,11); //Put attention between 5 (distracted) and 10 (focused) to determine how likely COM is to put a wrong card
    }

    @Override
    public void playCard() {
        List<Card> hand = getHand();
        List<Card> validCards = new ArrayList<>();

        // Identify valid cards based on global DiscardPile
        for (Card c : hand) {
            if (CardValidity.isValidCard(c)) { //check if card is valid through CardValidity
                validCards.add(c);
            }
        }

        boolean makesMistake = rand.nextInt(10) >= attention; //to establish whether COM will make a mistake or not
        Card chosenCard;

        if (!validCards.isEmpty()) {
            if (!makesMistake) {// if COM is not making a mistake, choose a valid card randomly
                chosenCard = validCards.get(rand.nextInt(validCards.size()));
            } else {// if COM makes a mistake — may pick a random card (could be invalid)
                chosenCard = hand.get(rand.nextInt(hand.size()));
            }

            if (CardValidity.isValidCard(chosenCard)) {
                hand.remove(chosenCard);
                DiscardPile.cardPlayed(chosenCard);
                System.out.println(getPlayerName() + " played: " + chosenCard + " (Attention: " + attention + ")"); //sout so human Players know what is happening
            } else {
                System.out.println(getPlayerName() + " made a mistake. Played invalid card. Drawing instead.");
                Card drawn = CardDeck.drawCard();
                hand.add(drawn);
                System.out.println(getPlayerName() + " drew: " + drawn);
            }

        }

        else {
            // No valid cards — must draw a card
            System.out.println(getPlayerName() + " had no valid cards. Drawing...");
            Card drawn = CardDeck.drawCard();
            hand.add(drawn);
            System.out.println(getPlayerName() + " drew: " + drawn);

            if (CardValidity.isValidCard(drawn)) {
                hand.remove(drawn);
                DiscardPile.cardPlayed(drawn);
                System.out.println(getPlayerName() + " played the drawn card: " + drawn);
            }
        }


    }


    public int getAttention() {
        return attention;
    }

    public void setAttention(int attention) {
        this.attention = attention;

        if (attention < 5 || attention > 10) {
            throw new IllegalArgumentException("Attention must be between 5 and 10"); //to make sure value can't be mistakenly set to below 5 or above 10
        }
    }
}
