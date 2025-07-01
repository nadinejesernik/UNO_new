import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class CompPlayer extends Player {

    private int attention; //to determine if COM will make a mistake when putting card down
    private int patience; //to determine if COM accuses someone of cheating when putting down a Draw 4
    private final Random rand = new Random();

    public CompPlayer(String name) {
        super(name);
        attention = rand.nextInt(5, 11); //Put attention between 5 (distracted) and 10 (focused) to determine how likely COM is to put a wrong card
        patience = rand.nextInt(1, 11); //Put patience between 1 (impatient) an 10 (patient) to determine how likely COM is to accuse someone of cheating
    }

    @Override
    public void playCard() {
        List<Card> hand = getHand();
        List<Card> validCards = new ArrayList<>();
        ActionManager.setCurrentPlayer(this); //to tell ActionManager which Player to reference

        if (Cheater) {
            Cheater = false;
        }
        if (checkForDraw()) { //if checkForDraw returns true, turn is skipped
            return;
        }

        while (true) {

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
                    if (chosenCard instanceof ActionCard) {
                        ((ActionCard) chosenCard).playAction();
                    }
                    hand.remove(chosenCard);
                    DiscardPile.cardPlayed(chosenCard);
                    System.out.println(getPlayerName() + " played: " + chosenCard + " (Attention: " + attention + ")"); //sout so human Players know what is happening
                    break;
                } else {
                    System.out.println(getPlayerName() + " made a mistake. Played invalid card. Drawing instead.");
                    Card drawn = CardDeck.drawCard();
                    hand.add(drawn);
                    System.out.println(getPlayerName() + " drew: " + drawn); //only to show if it works
                    break;
                }

            } else {
                // No valid cards — must draw a card
                System.out.println(getPlayerName() + " had no valid cards. Drawing...");
                Card drawn = CardDeck.drawCard();
                hand.add(drawn);
                System.out.println(getPlayerName() + " drew: " + drawn); //only to show if it works

                if (CardValidity.isValidCard(drawn)) {
                    if (drawn instanceof ActionCard) {
                        ((ActionCard) drawn).playAction();
                    }
                    hand.remove(drawn);
                    DiscardPile.cardPlayed(drawn);
                    System.out.println(getPlayerName() + " played the drawn card: " + drawn);
                    break;
                }
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
