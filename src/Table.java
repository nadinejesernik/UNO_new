import java.util.HashMap;

public class Table {
    static Player[] players;
    static int humanCount;
    static HashMap<Player, Integer> winnerAndPointsPerRound = new HashMap<>();

    public static void GameInitialisation() {
        players = PlayerInitialiser.initializePlayers();
        PlayerInitialiser.showPlayers(players);
        players = PlayerInitialiser.chooseFirstPlayer(players);

        CardDeck.buildFreshDeck();
        do {
            DiscardPile.cardPlayed(CardDeck.drawCard());
        } while (DiscardPile.showTopCard() instanceof ActionCard );
        CardDeck.returnWrongCardsToDeck(DiscardPile.returnDiscardPile());

        for (Player player : players) {
            player.setHand(CardDeck.createHand());
        }
    }

    public static void GamePlay() {
        while(!winnerCheck()) {
            for (Player player : players) {
                if (winnerCheck()) {
                    break;
                }
                System.out.println("The card on top of the deck is: " + DiscardPile.showTopCard()); //Karte wird hier angezeigt egal ob Mensch oder Bot

                if (player instanceof HumanPlayer) {
                    Menu.mainMenu(player);
                } else {
                    if (humanCount == 0) {
                        player.showHand();
//                        try {
//                            Thread.sleep(1000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
                    }
                    player.playCard();
                }
            }
        }
    }

    //placeholder Funktion um abzuchecken ob ein Spieler keine Karten mehr hat
    public static boolean winnerCheck() {
        for (Player player : players) {
            if (player.hand.isEmpty()) {
                winnerAndPointsPerRound.put(player,pointsForWinner());
                System.out.println(player.playerName + " is the Winner of this Round!");
                System.out.println("They earned " + pointsForWinner() + " points!");
                return true;
            }
        }
        return false;
    }

    public static int pointsForWinner() {
        int sumOfPoints = 0;
        for (Player player : players) {
            sumOfPoints += player.totalPointValueOfHand();
        }
        return sumOfPoints;
    }
}
