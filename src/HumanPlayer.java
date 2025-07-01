import java.util.ArrayList;
import java.util.Scanner;

public class HumanPlayer extends Player {
    public HumanPlayer(String name) {
        super(name);
    }

    @Override
    public void playCard() { //topcard parameter entfernt, übernimmt static DiscardPile. return value auf void gesetzt.
        Scanner input = new Scanner(System.in);
        String inputString;
        ActionManager.setCurrentPlayer(this); //to tell ActionManager which Player to reference


        if (checkForDraw()) {
            return;
        }

        showHand(); //only show hand if turn isn't skipped

        while (true) {

            if (Cheater) {
                Cheater = false;
            }

                System.out.println("Which card would you like to play? (1-" + hand.size() + ")");
                inputString = input.nextLine();

                if (CheckInput.splitCheckInput(inputString, this)) {
                    break; // Valid input
                }
                // Invalid input -> re-prompt
            }

            String[] inputArray = inputString.trim().split("\\s+");
            int cardIndex = Integer.parseInt(inputArray[0]);

            Card cardToPlay = hand.get(cardIndex - 1);

            if (CardValidity.isValidCard(cardToPlay)) {
                if (cardToPlay instanceof ActionCard) {
                    ((ActionCard) cardToPlay).playAction();
                }
                hand.remove(cardIndex - 1);
                DiscardPile.cardPlayed(cardToPlay);
            } else {
                System.out.println("This card is not valid. You draw 1 card as punishment.");
                PlayerDrawsCard();
                // PunishmentManager.InvalidCard();
            }

    }

}
