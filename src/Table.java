public class Table {
    static Player[] players;
    static int humanCount;
    public static void GameInitialisation() {
        players = PlayerInitialiser.initializePlayers();
        PlayerInitialiser.showPlayers(players);
        players = PlayerInitialiser.chooseFirstPlayer(players);

        CardDeck.buildFreshDeck();
        DiscardPile.cardPlayed(CardDeck.drawCard());
        for (Player player : players) {
            player.setHand(CardDeck.createHand());
        }
    }

    public static void GamePlay() {
        while(!winnerCheck()) {
            for (Player player : players) {
                System.out.println("The card on top of the deck is: " + DiscardPile.showTopCard()); //Karte wird hier angezeigt egal ob Mensch oder Bot

                if (player instanceof HumanPlayer) {
                    Menu.mainMenu(player);
                } else {
                    if (humanCount == 0) {
                        player.showHand();
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    player.playCard();
                }
            }
        }
    }

    //placeholder Funktion um abzuchecken ob ein Spieler keine Karten mehr hat
    public static boolean winnerCheck() {
        for (Player player : players) {
            if (player.hand.size() == 0) {
                System.out.println(player.playerName + " is the Winner of this Round!");
                return true;
            }
        }
        return false;
    }
}
