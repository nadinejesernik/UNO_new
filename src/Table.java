import java.util.*;

public class Table {
    static ArrayList<Player> players = new ArrayList<>();
    static int humanCount;
    //    static HashMap<Player, Integer> winnerAndPointsPerRound = new HashMap<>();
    static ArrayList<Player> winnerPerRound = new ArrayList<>();
    static boolean debug = true;
    static boolean playing = true;
    public static int currentRound = 1; //for db use


    public static void GameInitialisation() {
        players = PlayerInitialiser.initializePlayers();
        PlayerInitialiser.showPlayers(players);
        players = PlayerInitialiser.chooseFirstPlayer(players);

        freshDeckAndHands();
    }

    public static void freshDeckAndHands() {
        CardDeck.buildFreshDeck();
        do {
            DiscardPile.cardPlayed(CardDeck.drawCard());
        } while (DiscardPile.showTopCard() instanceof ActionCard);
        CardDeck.returnWrongCardsToDeck(DiscardPile.returnDiscardPile());

        for (Player player : players) {
            player.setHand(CardDeck.createHand());
        }
    }

    public static void setupNewRound() {
        System.out.println("_____ Setting up new Round _____");

        ActionManager.setSkipped(false);
        ActionManager.setDraw(false);
        ActionManager.setIsClockwise(true); //if last card played in last round was action card, effects don't get carried into new round

        CardDeck.clearDeck();
        DiscardPile.clearDiscardPile();
        for (Player player : players) {
            player.hand.clear();
        }

        freshDeckAndHands();
    }

    public static void GamePlay() {
        while (!winnerCheck()) {
            for (Player player : players) {

                if (player != ActionManager.getCurrentPlayer()) {
                    continue; // skip until we reach the current one
                }

                System.out.println("The card on top of the deck is: " + DiscardPile.showTopCard()); //Karte wird hier angezeigt egal ob Mensch oder Bot

                if (player instanceof HumanPlayer) {
                    Menu.mainMenu(player);
                } else {
                    if (humanCount == 0) {
                        player.showHand();
                    }
                    player.playCard();
                }

                if (winnerCheck()) {
                    winnerPerRound.add(player);
                    System.out.println(player.playerName + " is the Winner of this Round!");
                    System.out.println("They earned " + pointsForWinner() + " points!");

                    player.points += pointsForWinner();
                    System.out.println(player.playerName + " has " + player.points + " total points!");

                    int gameId = DBManager.getGameId();
                    DBManager.saveScoresForRound(gameId, currentRound, players);
                    currentRound++;


                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (player.points >= 500) {
                        playing = false;
                        System.out.println(player.playerName + " has reached over 500 Points!");
                        System.out.println("They are the winner of this Game!");

                        System.exit(0);
                    }
                    break;
                }

                ActionManager.advanceTurn();

                break;
            }
        }
    }

    //placeholder Funktion um abzuchecken ob ein Spieler keine Karten mehr hat
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
            sumOfPoints += player.totalPointValueOfHand();
        }
        return sumOfPoints;
    }
}
