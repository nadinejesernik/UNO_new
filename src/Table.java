public class Table {
    static Player[] players;
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
                if (player instanceof HumanPlayer) {
                    Menu.mainMenu(player);
                }
            }
        }
    }

    //placeholder Funktion um abzuchecken ob ein Spieler keine Karten mehr hat
    public static boolean winnerCheck() {
        for (Player player : players) {
            if (player.hand.size() == 0) {
                return true;
            }
        }
        return false;
    }
}
