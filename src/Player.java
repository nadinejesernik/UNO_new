import java.util.ArrayList;

public abstract class Player {
    protected String playerName;
    protected ArrayList<Card> hand;

    public Player(String playerName) {
        this.playerName = playerName;
        this.hand = new ArrayList<>();
    }

    public void drawCard(Card card) {
        hand.add(card);
    }

    public String getName() {
        return playerName;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public void showHand() {
        System.out.println("Your hand:");
        for (int i = 0; i < hand.size(); i++) {
            System.out.println(i + ": " + hand.get(i));
        }
    }


    public abstract Card playCard(Card topCard); //to be implemented by subclasses

}
