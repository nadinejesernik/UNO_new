import java.util.ArrayList;

public abstract class Player {
    protected String playerName;
    protected ArrayList<Card> hand;
    protected int scorePoints; //to keep track of points

    public Player(String playerName) {
        this.playerName = playerName;
        hand = new ArrayList<Card>();
        scorePoints = 0;
    }

    public void drawCard(Card card) {
    }


    public abstract Card playCard(Card topCard); //to be implemented by subclasses



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

    public int getScorePoints() {
        return scorePoints;
    }

    public void setScorePoints(int scorePoints) {
        this.scorePoints = scorePoints;
    }
}
