public class NumbersCard extends Card {

    private final int value;
    public NumbersCard(Colour colour, int value) {
        super(colour);
        this.value = value;
        super.pointValue = value;
    }

    @Override
    public String toString() {
        switch (super.getColour()) {
            case Colour.GREEN:
                return "\u001B[32m" + super.getColour() + " " + value + "\u001B[0m";
            case Colour.BLUE:
                return "\u001B[34m" + super.getColour() + " " + value + "\u001B[0m";
            case Colour.RED:
                return "\u001B[31m" + super.getColour() + " " + value + "\u001B[0m";
            case Colour.YELLOW:
                return "\u001B[33m" + super.getColour() + " " + value + "\u001B[0m";
            default: return super.getColour() + " " + value;
        }
    }

    public int getValue() {
        return value;
    }
}
