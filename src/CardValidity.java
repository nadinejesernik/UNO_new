public class CardValidity {
    public static boolean isValidCard(Card card) {
        if (card.getColour() == Card.Colour.WILD) {
            return true;
        }

        if (card.getColour() == DiscardPile.showTopCard().getColour() ||
                card.getColour() == DiscardPile.showTopCard().wildColor) {
            return true;
        }

        if (card instanceof NumbersCard && DiscardPile.showTopCard() instanceof NumbersCard) {
            if (((NumbersCard) card).getValue() == ((NumbersCard) DiscardPile.showTopCard()).getValue()) {
                return true;
            }
        }

        if (card instanceof ActionCard && DiscardPile.showTopCard() instanceof ActionCard) {
            if (((ActionCard) card).getAction() == ((ActionCard) DiscardPile.showTopCard()).getAction()) {
                return true;
            }
        }

        return false;
    }
}
