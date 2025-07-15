public class CardValidity {
    public static boolean isValidCard(Card card) { //Um zu prüfen ob die gespielte Karte gültig für die momentane TopCard ist
        return isValidCardAgainst(card, DiscardPile.showTopCard());
    }

    public static boolean isValidCardAgainst(Card card, Card topCard) { //Für DrawFour Logik. Um Karten mit der vorhergehenden TopCard abzugleichen
        if (card.getColour() == Card.Colour.WILD) {
            return true;
        }

        if (card.getColour() == topCard.getColour() ||
                card.getColour() == topCard.wildColor) {
            return true;
        }

        if (card instanceof NumbersCard && topCard instanceof NumbersCard) {
            if (((NumbersCard) card).getValue() == ((NumbersCard) topCard).getValue()) {
                return true;
            }
        }

        if (card instanceof ActionCard && topCard instanceof ActionCard) {
            if (((ActionCard) card).getAction() == ((ActionCard) topCard).getAction()) {
                return true;
            }
        }

        return false;
    }
}
