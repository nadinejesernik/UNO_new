import java.util.*;

public class Table {
    static ArrayList<Player> players = new ArrayList<>();
    static int humanCount;
    static boolean debug = false; //für Debugausgaben
    static boolean playing = true; //für Game-Loop
    public static int currentRound = 1; //für Datenbank


    public static void GameInitialisation() {
        players = PlayerInitialiser.initializePlayers();
        PlayerInitialiser.showPlayers(players); //Ausgabe für Spielerübersicht
        players = PlayerInitialiser.chooseFirstPlayer(players);

        freshDeckAndHands();
    }

    public static void freshDeckAndHands() {
        CardDeck.buildFreshDeck();
        do {
            //Diese Funktion wird aufgerufen um die erste Karte niederzulegen
            DiscardPile.cardPlayed(CardDeck.drawCard());
            //es wird solange wiederholt bis es keine Aktionskarte ist
        } while (DiscardPile.showTopCard() instanceof ActionCard);
        //falls eine Aktionskarte gelegt wurde liegen mehrere Karten am DiscardPile als gewünscht; also mischt diese Funktion diese überschüssigen karten wieder ins Deck
        CardDeck.returnWrongCardsToDeck(DiscardPile.returnDiscardPile());

        for (Player player : players) {
            player.setHand(CardDeck.createHand());
        }
    }

    public static void setupNewRound() {
        System.out.println("_____ Setting up new Round _____");

        //Aktionen Ausführen wird im ActionManager zurückgesetzt, damit man eine neue frische Runde starten kann
        ActionManager.setSkipped(false);
        ActionManager.setDraw(false);
        ActionManager.setIsClockwise(true);

        //CardDeck, DiscardPile & Karten auf der Hand der Spieler werden entleert
        CardDeck.clearDeck();
        DiscardPile.clearDiscardPile();
        for (Player player : players) {
            player.hand.clear();
        }

        //Alles wird neu aufgesetzt für neue Runde
        freshDeckAndHands();
    }


    public static void GamePlay() {
        while (!winnerCheck()) {
            for (Player player : players) {

                if (player != ActionManager.getCurrentPlayer()) {
                    continue; // skip until we reach the current one
                }

                System.out.println("The card on top of the deck is: " + DiscardPile.showTopCard()); //Karte wird hier angezeigt egal ob Mensch oder Bot

                if (ActionManager.isDraw() && DiscardPile.showTopCard() instanceof ActionCard &&
                        ((ActionCard) DiscardPile.showTopCard()).getAction() == ActionCard.Action.DRAW_FOUR) {

                    Player instigator = ActionManager.getDrawFourInstigator();

                    if (player instanceof HumanPlayer) {
                        DrawFourMenu.drawMenu(player, instigator);
                    } else {
                        player.playCard();  // COM will handle accuse internally
                    }

                    // Reset flags
                    ActionManager.setDraw(false);
                    ActionManager.clearDrawFourInstigator();
                } else {
                    if (player instanceof HumanPlayer) {
                        Menu.mainMenu(player);
                    } else {
                        if (humanCount == 0) {
                            player.showHand();
                        }
                        player.playCard();
                    }
                }



                if (winnerCheck()) {
                    System.out.println(player.playerName + " is the Winner of this Round!");
                    System.out.println("They earned " + pointsForWinner() + " points!");

                    player.points += pointsForWinner();
                    System.out.println(player.playerName + " has " + player.points + " total points!");

                    int gameId = DBManager.getGameId();
                    DBManager.saveScoresForRound(gameId, currentRound, players);
                    currentRound++;

                    if (player.points >= 500) {
                        playing = false;
                        System.out.println(player.playerName + " has reached over 500 Points!");
                        System.out.println("They are the winner of this Game!");
                        return;
                    }
                    break;
                }

                ActionManager.advanceTurn();

                break;
            }
        }
    }

    //Checkt ab ob ein Spieler keine Karten mehr hat - was Ihn anhand Implementation der Regeln zum Gewinner macht
    public static boolean winnerCheck() {
        for (Player player : players) {
            if (player.hand.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public static int pointsForWinner() {
        int sumOfPoints = 0;
        for (Player player : players) {
            sumOfPoints += player.totalPointValueOfHand(); //zählt alle Kartenwerte in den Spielerhänden zusammen
        }
        return sumOfPoints;
    }
}
