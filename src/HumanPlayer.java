import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HumanPlayer extends Player {
    public HumanPlayer(String name) {
        super(name);
    }

    @Override
    public void playCard() {
        Scanner input = new Scanner(System.in);
        String inputString;
        ActionManager.setCurrentPlayer(this); //to tell ActionManager which Player to reference


        if (checkForDraw()) {
            return;
        }

        if (ActionManager.isSkipped()) {
            System.out.println("Your turn has been skipped.");
            ActionManager.setSkipped(false);
            return;
        }

        showHand(); //only show hand if turn isn't skipped

        while (true) {


            if (Cheater) {
                Cheater = false;
            }

            if (checkAndDrawIfNoValidCards()){
                return;
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
        System.out.println("_____________");

    }

    public boolean checkAndDrawIfNoValidCards() {
        List<Card> hand = getHand();
        List<Card> validCards = new ArrayList<>();

        for (Card card : hand) {
            if (CardValidity.isValidCard(card)) {
                validCards.add(card);
            }
        }

        if (validCards.isEmpty()) {
            System.out.println("No valid cards. Drawing...");

            Card drawn = CardDeck.drawCard();
            System.out.println("You drew: " + drawn);
            hand.add(drawn);

            if (CardValidity.isValidCard(drawn)) {
                Scanner scanner = new Scanner(System.in);
                String input;

                while (true) {
                    System.out.print("The drawn card is valid. Do you want to play it? (Yes/No): ");
                    input = scanner.nextLine().trim().toLowerCase();

                    if (input.equals("yes")) {
                        hand.remove(drawn);
                        DiscardPile.cardPlayed(drawn);
                        System.out.println(getPlayerName() + " played: " + drawn);

                        if (drawn instanceof ActionCard) {
                            ((ActionCard) drawn).playAction();
                        }
                        return true;
                    } else if (input.equals("no")) {
                        System.out.println("Card not played. Turn ends.");
                        return true;
                    } else {
                        System.out.println("Invalid input. Please enter 'yes' or 'no'.");
                    }
                }

            } else {
                System.out.println("The drawn card is not valid. Turn ends.");
                return true; // turn ends
            }
        }

        return false; // valid cards exist, proceed normally
    }



}
