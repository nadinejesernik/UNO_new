import java.util.ArrayList;

public class ActionCard extends Card {
    public enum Action {
        SKIP,
        REVERSE,
        DRAW_TWO,
        WILD,
        DRAW_FOUR
    }

    private Action action;
    public ActionCard(Colour colour, Action action) {
        super(colour);
        this.action = action;
        if (colour == Colour.WILD) {
            super.pointValue = 50;
        } else {
            super.pointValue = 20;
        }
    }

    public void playAction() {
        switch (action) {
            case Action.SKIP:
                ActionManager.skipNextPlayer(); //setzt isSkipped auf true -> Wird in playCard() abgefragt
                break;
            case Action.REVERSE:
                ActionManager.reverseOrder();
                break;
            case Action.DRAW_TWO:
                ActionManager.setDraw(true);
                break;
            case Action.WILD:
                super.wildColor = ActionManager.chooseColour(ActionManager.getCurrentPlayer());
                break;
            case Action.DRAW_FOUR:
                ActionManager.setDrawFourHandSnapshot(new ArrayList<>(ActionManager.getCurrentPlayer().getHand())); //speichert Liste von gültigen Karten bevor DrawFour gelegt wird
                ActionManager.setDraw(true);
                ActionManager.setDrawFourInstigator(ActionManager.getCurrentPlayer());
                super.wildColor = ActionManager.chooseColour(ActionManager.getCurrentPlayer());
                break;

        }
    }


    @Override
    public String toString() {
        switch (super.getColour()) {
            case Colour.GREEN:
                return "\u001B[32m" + super.getColour() + " " + action + "\u001B[0m";
            case Colour.BLUE:
                return "\u001B[34m" + super.getColour() + " " + action + "\u001B[0m";
            case Colour.RED:
                return "\u001B[31m" + super.getColour() + " " + action + "\u001B[0m";
            case Colour.YELLOW:
                return "\u001B[33m" + super.getColour() + " " + action + "\u001B[0m";
            case Colour.WILD:
                if (this.wildColor != null) {
                    switch (this.wildColor) {
                        case Colour.GREEN:
                            return "\u001B[32m" + super.getColour() + " " + action + "\u001B[0m";
                        case Colour.BLUE:
                            return "\u001B[34m" + super.getColour() + " " + action + "\u001B[0m";
                        case Colour.RED:
                            return "\u001B[31m" + super.getColour() + " " + action + "\u001B[0m";
                        case Colour.YELLOW:
                            return "\u001B[33m" + super.getColour() + " " + action + "\u001B[0m";
                    }
                }
            default:
                return super.getColour() + " " + action;

        }
    }

    public Action getAction() {
        return action;
    }
}
