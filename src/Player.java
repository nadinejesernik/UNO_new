import java.util.ArrayList;

public abstract class Player {
    protected String playerName;

    public Player(String playerName) {
        this.playerName = playerName;
    }

    public void drawCard(Card card) {
    }

    public String getName() {
        return playerName;
    }


    public abstract Card playCard(Card topCard); //to be implemented by subclasses


    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

}
