import java.util.Scanner;

public class HumanPlayer extends Player {
    public HumanPlayer(String name) {
        super(name);
    }

    @Override
    public void playCard() { //topcard parameter entfernt, übernimmt static DiscardPile. return value auf void gesetzt.
        Scanner input = new Scanner(System.in);
        String inputSting;
        showHand();

        do {
            System.out.println("Which card would you like to play? 1-"+hand.size());//+HandArray.length//
            inputSting = input.nextLine();
        } while(!CheckInput.splitCheckInput(inputSting,this));

        String regex = "\\s+";
        String[] inputArray = inputSting.trim().split(regex);

        int cardIndex = Integer.valueOf(inputArray[0]);

        if (cardIndex < 1 || cardIndex > hand.size()) { //if input out of bounds run playCard again
            System.out.println("Invalid index. Try again.");
            playCard();
        }

        Card cardToPlay = hand.get(cardIndex-1);
        if(CardValidity.isValidCard(cardToPlay)) {
            hand.remove(cardIndex-1);
            DiscardPile.cardPlayed(cardToPlay);
        } else {
            System.out.println("This Card is not valid. You draw 1 Card as punishment");
            //PunishmentManager.InvalidCard();
        }
    }

    public void showHand() {
        for (Card card : super.hand) {
            System.out.print(card + " || ");
        }
        System.out.println();
    }
}
