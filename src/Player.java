import java.util.ArrayList;
import java.util.Random;

public abstract class Player {
    protected String playerName;
    protected ArrayList<Card> hand;
    protected boolean Cheater = false;
    protected int points = 0;
    protected boolean saidUNO = false;

    public void declareUNO() {
        saidUNO = true;
    }

    public boolean hasDeclaredUNO() {
        return saidUNO;
    }

    public void resetUNODeclaration() {
        saidUNO = false;
    }

    public Player(String playerName) {
        this.playerName = playerName;
    }

    public void PlayerDrawsCard() {
        hand.add(CardDeck.drawCard());
    }

    public abstract void playCard();

    public boolean checkForDraw() {

        if (!ActionManager.isDraw()) {
            return false;
        }

        Card topCard = DiscardPile.showTopCard();
        if (topCard instanceof ActionCard) {
            ActionCard.Action action = ((ActionCard) topCard).getAction();

            if (action == ActionCard.Action.DRAW_TWO) {
                ActionManager.drawTwo(this);
                ActionManager.setDraw(false);
                return true;
            } else if (action == ActionCard.Action.DRAW_FOUR) {
                Player instigator = ActionManager.getDrawFourInstigator();

                // Wenn HumanPlayer dann wird das DrawFour Menü angezeigt
                if (this instanceof HumanPlayer) {
                    DrawFourMenu.drawMenu(this, instigator);
                }

                // Wenn COM nutze Patience meter um festzustellen ob COM beschuldigt oder nicht
                else if (this instanceof CompPlayer comp) {
                    // Niedriger Patience Wert -> Beschuldigt öfter
                    int roll = new Random().nextInt(1, 11);
                    if (Table.debug) {
                        System.out.println(getPlayerName() + " (Patience: " + comp.getPatience() + ", Roll: " + roll + ")");
                    }

                    if (roll >= comp.getPatience()) {
                        // Wenn beschuldigt wird
                        System.out.println(comp.getPlayerName() + " is accusing " + instigator.getPlayerName() + " of cheating!");
                        ActionManager.drawFourCheck(instigator);
                        if (instigator.isCheater()) {
                            PunishmentManager.plusFour(2, this, instigator);
                            instigator.setCheater(false);
                        } else {
                            PunishmentManager.plusFour(3, this, instigator);
                        }
                    } else {
                        // Wenn nicht beschuldigt wird
                        PunishmentManager.plusFour(1, this, instigator);
                    }
                }

                ActionManager.setDraw(false); //Flag zurücksetzen
                return true;
            }

        }
        return false;
    }

    public void showHand() {
        for (Card card : this.hand) {
            System.out.print("|" + (hand.indexOf(card) + 1) + "| " + card + " ");
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
