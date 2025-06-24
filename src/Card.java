public abstract class Card {
    public enum Colour {
        RED,
        GREEN,
        BLUE,
        YELLOW,
        WILD
    }
    private final Colour colour;
    protected Colour wildColor;
    protected int pointValue;

    public Card(Colour colour) {
        this.colour = colour;
    }

    public Colour getColour() {
        return colour;
    }

    public int getPointValue() {
        return pointValue;
    }
}
