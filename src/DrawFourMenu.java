import java.util.Scanner;

public class DrawFourMenu {

    static Player currentPlayer;
    //Spezielles Menü für den nächsten Spieler, wenn eine +4-Karte gespielt wurde.
    static void drawMenu(Player player, Player previousPlayer) {
        currentPlayer = player;
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("It's " + currentPlayer.playerName + "'s turn.");
            System.out.println("Choose an option:");
            System.out.println("1. Draw 4 cards");
            System.out.println("2. Accuse " + previousPlayer.getPlayerName() + " of cheating");
            System.out.println("3. Help");
            System.out.println("4. Exit");

            String menuAuswahl = scanner.nextLine().trim();

            switch (menuAuswahl) {
                case "1":// Spieler akzeptiert +4 und zieht Karten
                    PunishmentManager.plusFour(1, currentPlayer, previousPlayer);
                    return;
                case "2": // Spieler beschuldigt vorherigen Spieler
                    ActionManager.drawFourCheck(previousPlayer);
                    if (previousPlayer.isCheater()) {
                        // Beschuldigung war korrekt → vorheriger Spieler wird bestraft
                        PunishmentManager.plusFour(2, currentPlayer, previousPlayer);
                        previousPlayer.setCheater(false); // Reset cheat flag
                    } else {
                        // Beschuldigung war falsch → aktueller Spieler wird härter bestraft
                        PunishmentManager.plusFour(3, currentPlayer, previousPlayer);
                    }
                    return;
                case "3":// Hilfe anzeigen
                    Menu.help();
                    break;
                case "4":// Spiel beenden
                    Menu.exitGame();
                    break;
                default: // Ungültige Eingabe
                    System.out.println("Wrong input, try again.");
            }
        }
    }
}

