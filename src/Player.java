import java.util.ArrayList;

public abstract class Player {
    protected String playerName;
    protected ArrayList<Card> hand;
    protected boolean Cheater = false;
    protected int points = 0;

    public Player(String playerName) {
        this.playerName = playerName;
//        hand = CardDeck.createHand();
    }

    public void PlayerDrawsCard() {
        hand.add(CardDeck.drawCard()); //communicates with Clarissa's card deck drawCard method
    }

    public abstract void playCard(); //to be implemented by subclasses

    public boolean checkForDraw(){

        if(!ActionManager.isDraw()){
            return false;
        }

        Card topCard = DiscardPile.showTopCard();
        if(topCard instanceof ActionCard){
            ActionCard.Action action = ((ActionCard) topCard).getAction();

            if (action == ActionCard.Action.DRAW_TWO){
                ActionManager.drawTwo(this);
                ActionManager.setDraw(false);
                return true;
            }

            else if(action == ActionCard.Action.DRAW_FOUR){
                ActionManager.drawFour(this);
                ActionManager.setDraw(false);
                return true;
            }
        }
        return false;
    }

    public void showHand() {
        for (Card card : this.hand) {
            System.out.print("|"+ (hand.indexOf(card)+1) +"| " + card + " ");
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

    public ArrayList<Card> getHand() {
        return hand;
    }

    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;
    }

    public boolean isCheater() {
        return Cheater;
    }

    public void setCheater(boolean cheater) {
        Cheater = cheater;
    }
}
