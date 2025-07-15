import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class CompPlayer extends Player {

    private int attention; //um festzulegen wie wahrscheinlich COM einen Fehler macht
    private int patience; //um festzulegen wie wahrscheinlich COM des Schummelns beschuldigt
    private final Random rand = new Random();

    public CompPlayer(String name) {
        super(name);
        attention = rand.nextInt(5, 11); //Attention wird zwischen 5 und 10 gesetzt
        int[] weighted = {1, 1, 2, 2, 3, 3, 4, 5, 6, 7, 8, 9, 10}; // Zahlen von 1-4 kommen öfter vor...
        patience = weighted[rand.nextInt(weighted.length)]; //...um Bots etwas weniger patient zu machen (damit beschuldigen sie häufiger)

    }

    @Override
    public void playCard() {
        List<Card> hand = getHand();
        List<Card> validCards = new ArrayList<>();
        ActionManager.setCurrentPlayer(this); //zum Player referenzieren

        if (Cheater) {
            Cheater = false;
        }
        if (checkForDraw()) { //wenn checkForDraw auf true gesetzt ist wird der Spieler übersprungen
            return;
        }

        if(ActionManager.isSkipped()) { //passiert wenn Skip Karte gespielt wurde
            ActionManager.setSkipped(false);
            System.out.println(getPlayerName() + "'s turn has been skipped.");
            System.out.println("_____________");
            return;
        }

        //call UNO Logik
        if (hand.size() == 2) {
            if (rand.nextBoolean()) {
                System.out.println(playerName +" said UNO!");
                declareUNO();
            }
        }

        if (hand.size() == 1 && !hasDeclaredUNO()) {
            PunishmentManager.noUNOCalled(this);
        }

        if (hand.size() > 2) {
            resetUNODeclaration();
        }


        while (true) {

            for (Card c : hand) {
                if (CardValidity.isValidCard(c)) {
                    validCards.add(c);
                }
            }

            boolean makesMistake = rand.nextInt(10) >= attention; //um festzustellen ob COM einen Fehler macht
            Card chosenCard;

            if (!validCards.isEmpty()) {
                if (!makesMistake) {// wenn kein Fehler gemacht wird spiel COM eine seiner gültigen Karten
                    chosenCard = validCards.get(rand.nextInt(validCards.size()));
                } else {// wenn COM unaufmerksam ist wird irgendeine Karte aus der Hand gewählt, auch wenn sie ungültig ist
                    chosenCard = hand.get(rand.nextInt(hand.size()));
                }

                if (CardValidity.isValidCard(chosenCard)) {

                    //Für DrawFour Logik
                    if (chosenCard instanceof ActionCard ac && ac.getAction() == ActionCard.Action.DRAW_FOUR) {
                        if (!CardDeck.wasJustDrawn(ac)) { //solange DrawFour nicht gerade erst gezogen wurde
                            List<Card> snapshot = new ArrayList<>(getHand());
                            snapshot.remove(ac); // Entferne die DrawFour Karte
                            ActionManager.setDrawFourHandSnapshot(snapshot);
                            ActionManager.setDrawFourTopCard(DiscardPile.showTopCard());
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
                    break;
                } else {
                    System.out.println(getPlayerName() + " made a mistake. Played invalid card. Drawing instead.");
                    Card drawn = CardDeck.drawCard();
                    hand.add(drawn);
                    if (Table.debug) {
                        System.out.println(getPlayerName() + " drew: " + drawn);
                    }
                    break;
                }

            } else {
                // Wenn COM keine gültigen Karten hat wird automatisch eine Karte gezogen
                System.out.println(getPlayerName() + " had no valid cards. Drawing...");
                Card drawn = CardDeck.drawCard();
                hand.add(drawn);

                if (Table.debug) {
                    System.out.println(getPlayerName() + " drew: " + drawn);
                }

                if (CardValidity.isValidCard(drawn)) { //wenn gezogene Karte gültig ist wird sie sofort gespielt
                    if (drawn instanceof ActionCard) {
                        ((ActionCard) drawn).playAction();
                    }
                    hand.remove(drawn);
                    DiscardPile.cardPlayed(drawn);
                    System.out.println(getPlayerName() + " played the drawn card: " + drawn);

                    // COM ruft evtl. automatisch UNO, wenn er nur noch 1 Karte hat
                    if (hand.size() == 1) {
                        if (rand.nextInt(10) < attention) { // Random Chance, UNO zu sagen
                            declareUNO();
                            System.out.println(getPlayerName() + " said UNO!");
                        } else {
                            // Kein UNO gesagt → Strafe
                            PunishmentManager.noUNOCalled(this);
                        }
                    }

                    // Sicherheitshalber UNO zurücksetzen
                    resetUNODeclaration();
                    break;
                }

                else {
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
