public class CardValidity {
    public static boolean isValidCard(Card card) {
        return isValidCardAgainst(card, DiscardPile.showTopCard());
    }

    public static boolean isValidCardAgainst(Card card, Card topCard) {
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
