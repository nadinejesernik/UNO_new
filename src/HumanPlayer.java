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

        if (Cheater) {
            Cheater = false;
        }

        if (checkForDraw()) {
            return;
        }

        if (ActionManager.isSkipped()) {
            System.out.println(getPlayerName() + "'s turn has been skipped.");
            System.out.println("_____________");
            ActionManager.setSkipped(false);
            return;
        }

        showHand(); //zeit die Hand nur wenn Spieler nicht übersprungen wurde

        while (true) {
            if (checkAndDrawIfNoValidCards()) {
                return;
            }

            System.out.println("Which card would you like to play? (1-" + hand.size() + ")");
            inputString = input.nextLine();

            if (CheckInput.splitCheckInput(inputString, this)) {
                break; // Wenn Eingabe = richtig, dann endet die unendliche Schleife
            }
            // Sonst fragt er noch einmal nach neuer Eingabe
        }

        String[] inputArray = inputString.trim().split("\\s+");
        int cardIndex = Integer.parseInt(inputArray[0]);

        Card cardToPlay = hand.get(cardIndex - 1);

        if (CardValidity.isValidCard(cardToPlay)) {

            if (cardToPlay instanceof ActionCard ac && ac.getAction() == ActionCard.Action.DRAW_FOUR) {
                List<Card> snapshot = new ArrayList<>(getHand());
                snapshot.remove(ac); // Remove the Draw Four card itself
                ActionManager.setDrawFourHandSnapshot(snapshot);
                ActionManager.setDrawFourTopCard(DiscardPile.showTopCard());
            }


            if (cardToPlay instanceof ActionCard) {
                ((ActionCard) cardToPlay).playAction();
            }
            hand.remove(cardIndex - 1);
            DiscardPile.cardPlayed(cardToPlay);
            if (hand.size() == 1 && !hasDeclaredUNO()) {
                PunishmentManager.noUNOCalled(this);
            }
            resetUNODeclaration();

        } else {
            PunishmentManager.invalidCard(this);

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
                // Merkt sich, ob der Spieler in diesem Moment „UNO“ gerufen hat
                boolean unoCalled = false;

                while (true) {
                    //Eingabe ob Spieler Karte spielen möchte
                    System.out.print("The drawn card is valid. Do you want to play it? (Yes / Yes UNO / No): ");
                    input = scanner.nextLine().trim().toLowerCase();

                    // Eingabe in einzelne Wörter
                    String[] parts = input.split("\\s+");
                    if (parts.length >= 1) {
                        // Spieler möchte die gezogene Karte spielen
                        if (parts[0].equals("yes")) {

                            // Spieler hat zusätzlich "uno" angegeben
                            if (parts.length == 2 && parts[1].equals("uno")) {
                                if (hand.size() == 2) {
                                    // UNO wird akzeptiert
                                    declareUNO();
                                    unoCalled = true;
                                    System.out.println(getPlayerName() + ": UNO!");
                                } else {
                                    // Ungültiger UNO-Ruf (nicht genau 2 Karten)
                                    System.out.println("You can only declare UNO when you have exactly 2 cards.");
                                    //Eingabe wird erneut abgefragt
                                    continue;
                                }
                            }

                            // Die gezogene Karte wird gespielt
                            hand.remove(drawn);
                            DiscardPile.cardPlayed(drawn);
                            System.out.println(getPlayerName() + " played: " + drawn);
                            System.out.println("_____________");

                            // Falls es eine Aktionskarte ist, deren Effekt ausführen
                            if (drawn instanceof ActionCard) {
                                ((ActionCard) drawn).playAction();
                            }

                            // Falls Spieler jetzt nur noch 1 Karte hat, aber UNO nicht gesagt hat → Strafe
                            if (hand.size() == 1 && !unoCalled) {
                                PunishmentManager.noUNOCalled(this);
                            }
                            // UNO-Status zurückgesetzt
                            resetUNODeclaration();
                            // Spielzug ist beendet
                            return true;

                            // Spieler möchte die gezogene Karte nicht spielen
                        } else if (parts[0].equals("no")) {
                            System.out.println("Card not played. Turn ends.");
                            System.out.println("_____________");
                            return true;
                        }
                    }

                    System.out.println("Invalid input. Please enter 'yes', 'yes uno' or 'no'.");
                }


            } else {
                System.out.println("The drawn card is not valid. Turn ends.");
                System.out.println("_____________");
                return true; // turn ends
            }
        }

        return false; // valid cards exist, proceed normally
    }


}
