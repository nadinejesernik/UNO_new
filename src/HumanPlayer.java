import java.util.Scanner;

public class HumanPlayer extends Player {
    public HumanPlayer(String name) {
        super(name);
    }

    @Override
    public Card playCard(Card topCard) {
        Scanner input = new Scanner(System.in);
        showHand();

        System.out.println("The card on top of the deck is: " + topCard);
        System.out.println("Enter the index number of the Card you would like to play: ");
        int cardIndex = input.nextInt();

        if (cardIndex < 0 || cardIndex > hand.size() - 1) { //if input out of bounds run playCard again
            System.out.println("Invalid index. Try again.");
            return playCard(topCard);
        }

        Card cardToPlay = hand.get(cardIndex);

        if (hand.get(cardIndex).getColour().equals(topCard.getColour()) || hand.get(cardIndex).getValue().equals(topCard.getValue()) || hand.get(cardIndex).getColour().equals("Wild")) {
            hand.remove(cardIndex);
        }
        else {
            System.out.println("Invalid card. Try again.");
            return playCard(topCard);
        }
            return cardToPlay;

    }

    public void showHand() {
        for (Card card : super.hand) {
            System.out.print(card + " ");
        }
        System.out.println();
    }
}
