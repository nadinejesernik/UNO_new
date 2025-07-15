import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CompPlayer extends Player {

    private int attention; // um festzulegen, wie wahrscheinlich COM einen Fehler macht
    private int patience;  // um festzulegen, wie wahrscheinlich COM jemanden des Schummelns beschuldigt
    private final Random rand = new Random();

    public CompPlayer(String name) {
        super(name);
        attention = rand.nextInt(5, 11); // Attention wird zwischen 5 (unaufmerksam) und 10 (sehr aufmerksam) gesetzt
        int[] weighted = {1, 1, 2, 2, 3, 3, 4, 5, 6, 7, 8, 9, 10};
        patience = weighted[rand.nextInt(weighted.length)]; // niedrigere Zahlen kommen häufiger vor → Bots sind öfter ungeduldig
    }

    @Override
    public void playCard() {
        List<Card> hand = getHand();
        List<Card> validCards = new ArrayList<>();
        ActionManager.setCurrentPlayer(this); // setzt den aktuellen Spieler für den ActionManager

        if (Cheater) {
            Cheater = false;
        }
        if (checkForDraw()) { // wenn checkForDraw true ist, wird der Spieler übersprungen
            return;
        }

        if (ActionManager.isSkipped()) { // passiert, wenn Skip Karte gespielt wurde
            ActionManager.setSkipped(false);
            System.out.println(getPlayerName() + "'s turn has been skipped.");
            System.out.println("_____________");
            return;
        }

        while (true) {

            // sammelt alle gültigen Karten in der Hand
            validCards.clear();
            for (Card c : hand) {
                if (CardValidity.isValidCard(c)) {
                    validCards.add(c);
                }
            }

            boolean makesMistake = rand.nextInt(10) >= attention; // legt fest, ob COM einen Fehler macht
            Card chosenCard;

            if (!validCards.isEmpty()) {
                // wählt eine gültige Karte oder macht evtl. einen Fehler
                chosenCard = (!makesMistake) ?
                        validCards.get(rand.nextInt(validCards.size())) :
                        hand.get(rand.nextInt(hand.size()));

                if (CardValidity.isValidCard(chosenCard)) {

                    // DrawFour-Logik → Snapshot nur, wenn Karte vorher schon in der Hand war
                    if (chosenCard instanceof ActionCard ac && ac.getAction() == ActionCard.Action.DRAW_FOUR) {
                        if (!CardDeck.wasJustDrawn(ac)) {
                            List<Card> snapshot = new ArrayList<>(getHand());
                            snapshot.remove(ac); // DrawFour selbst wird entfernt
                            ActionManager.setDrawFourHandSnapshot(snapshot);
                            ActionManager.setDrawFourTopCard(DiscardPile.showTopCard()); // speichert oberste Karte vor dem Ausspielen
                        }
                    }

                    if (chosenCard instanceof ActionCard) {
                        ((ActionCard) chosenCard).playAction();
                    }

                    hand.remove(chosenCard);
                    DiscardPile.cardPlayed(chosenCard);
                    System.out.println(getPlayerName() + " played: " + chosenCard);
                    if (Table.debug) {
                        System.out.println(" (Attention: " + attention + ")");
                    }

                    // UNO Logik erst nach dem Ausspielen prüfen
                    if (hand.size() == 1) {
                        if (rand.nextInt(10) < attention) {
                            declareUNO();
                            System.out.println(getPlayerName() + " said UNO!");
                        } else {
                            PunishmentManager.noUNOCalled(this); // Bestrafung wenn kein UNO gerufen wurde
                        }
                    } else if (hand.size() > 1) {
                        resetUNODeclaration(); // falls Spieler wieder mehr als 1 Karte hat
                    }

                    break;
                } else {
                    // Fehler gemacht → ungültige Karte gespielt → zieht stattdessen eine
                    System.out.println(getPlayerName() + " made a mistake. Played invalid card. Drawing instead.");
                    Card drawn = CardDeck.drawCard();
                    hand.add(drawn);
                    if (Table.debug) {
                        System.out.println(getPlayerName() + " drew: " + drawn);
                    }
                    break;
                }

            } else {
                // keine gültigen Karten → zieht automatisch eine Karte
                System.out.println(getPlayerName() + " had no valid cards. Drawing...");
                Card drawn = CardDeck.drawCard();
                hand.add(drawn);

                if (Table.debug) {
                    System.out.println(getPlayerName() + " drew: " + drawn);
                }

                if (CardValidity.isValidCard(drawn)) {
                    // gezogene Karte ist gültig → wird sofort gespielt
                    if (drawn instanceof ActionCard) {
                        ((ActionCard) drawn).playAction();
                    }
                    hand.remove(drawn);
                    DiscardPile.cardPlayed(drawn);
                    System.out.println(getPlayerName() + " played the drawn card: " + drawn);

                    //UNO Logik auch nachdem gezogene Karte gespielt wurde
                    if (hand.size() == 1) {
                        if (rand.nextInt(10) < attention) {
                            declareUNO();
                            System.out.println(getPlayerName() + " said UNO!");
                        } else {
                            PunishmentManager.noUNOCalled(this);
                        }
                    } else if (hand.size() > 1) {
                        resetUNODeclaration();
                    }

                    break;
                } else {
                    break;
                }
            }
        }
        System.out.println("_____________");
    }

    public int getPatience() {
        return patience;
    }
}
