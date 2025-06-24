import java.util.ArrayList;

public abstract class Player {
    protected String playerName;
    protected ArrayList<Card> hand;
    protected int currentHandValue; //to keep track of point value of cards currently in hand
    //protected boolean Cheater = false;

    public Player(String playerName) {
        this.playerName = playerName;
//        hand = CardDeck.createHand();
        currentHandValue = 0;
    }

    public void PlayerDrawsCard() {
        hand.add(CardDeck.drawCard()); //communicates with Clarissa's card deck drawCard method
    }

    public int addHandValue(Card card) {
        //communicates with Clarissa's Card to calculate value at the end of each player round
        int addValue = 0;
            //dummy method to actually implement later

        return currentHandValue;
    }


    public abstract void playCard(); //to be implemented by subclasses

    public void showHand() {
        for (Card card : this.hand) {
            System.out.print(card + " || ");
        }
        System.out.println();
    }

    public int totalPointValueOfHand() {
        int sum = 0;
        for (Card card : this.hand) {
            sum += card.pointValue;
        }
        return sum;
    }

    //Getter&Setter

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;
    }

    public int getCurrentHandValue() {
        return currentHandValue;
    }

    public void setCurrentHandValue(int currentHandValue) {
        this.currentHandValue = currentHandValue;
    }
}
