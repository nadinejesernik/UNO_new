public class CardValidity {
    public static boolean isValidCard(Card card) {
        if (card.getColour().equals(DiscardPile.showTopCard().getColour()) ||
                card.getValue().equals(DiscardPile.showTopCard().getValue()) ||
                card.getColour().equals("Wild")) {
            return true;
        }
        return false;
    }
}
