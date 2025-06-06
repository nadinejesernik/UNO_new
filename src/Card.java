public class Card {

    private final String colour;
    private final String value;
    private int pointValue;

    public Card(String colour, String value) {
        this.colour = colour;
        this.value = value;
    }
//katja was here
    public String getColour() {
        return colour;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return colour + " " + value;
    }

}
