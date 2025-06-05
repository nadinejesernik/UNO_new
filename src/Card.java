public class Card {
    private final String colour;
    private final String value;
    private final int pointvalue;

    public Card(String colour, String value) {
        this.colour = colour;
        this.value = value;
        this.pointvalue = 0;
    }

    public String getColour() {
        return colour;
    }

    public String getValue() {
        return value;
    }

    public int getPointvalue() {
        return pointvalue;
    }

    @Override
    public String toString() {
        return colour + " " + value;
    }
}

