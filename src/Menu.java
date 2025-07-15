import java.sql.SQLException;
import java.util.Scanner;

public class Menu {
    static Player currentPlayer;
    // Hauptmenü für den aktuellen Spieler
    static void mainMenu(Player player) {
        currentPlayer = player;
        System.out.println("it's " + currentPlayer.playerName + "'s turn");
        System.out.println("Choose an option:");
        System.out.println("1. Play");
        System.out.println("2. Help");
        System.out.println("3. View Scores");
        System.out.println("4. Exit");
        Scanner scanner = new Scanner(System.in);
        String menuAuswahl = scanner.next();
        switch (menuAuswahl) {
            case "1":// Karte spielen
                currentPlayer.playCard();
                break;
            case "2":// Hilfe anzeigen
                help();
                break;
            case "3":// Punktestand anzeigen
                viewScores();
                break;
            case "4":// Spiel beenden
                exitGame();
                break;
            default:// Ungültige Eingabe
                System.out.println("Wrong input, try again");
                mainMenu(currentPlayer);
                break;
        }

    }
    // Hilfemenü mit Spielregeln
    static void help() {
        System.out.println("=== HOW TO PLAY UNO ===");

        // Allgemeine Regeln
        System.out.println("--- UNO Rules ---");
        System.out.println("On your turn, you must play a card that matches the top card by color or number.");
        System.out.println("If you can't play a valid card, you automatically draw one.");
        System.out.println("Wild cards can be played at any time, but if you have a matching normal card,");
        System.out.println("playing a wild card instead will be considered cheating.");
        System.out.println("If you have only one card left, you MUST declare 'UNO'.");
        System.out.println("Failing to declare 'UNO' results in an automatic 2-card penalty.");
        System.out.println("For more specifics visit: https://www.uno-kartenspiel.de/wp-content/uploads/2019/08/UNO-Spielregeln-Anleitung.pdf");
        System.out.println(" ");
        // Accuse-Regel
        System.out.println("--- Wild Draw Four Rule ---");
        System.out.println("If the player before you plays a +4 Wild card, you normally must draw 4 cards and skip your turn.");
        System.out.println("However, you can challenge them if you think they could have played a regular card instead.");
        System.out.println("When this happens, you will be shown a special menu with a new option to accuse them.");
        System.out.println("This will start a challenge. If you're right, they draw 4 cards instead. If you're wrong, you draw 6.");
        System.out.println(" ");
        // Eingabeformat
        System.out.println("--- Input Format ---");
        System.out.println("When it's your turn, your hand will be shown with numbered cards.");
        System.out.println("To play a card, enter the index of the card displayed to its left.");
        System.out.println("You can also include a special command in the same input to declare UNO.");
        System.out.println("For example:    2 UNO");
        System.out.println("The system accepts commands separated by spaces.");
        System.out.println(" ");
        // Datenbank-Erklärung
        System.out.println("--- Score Tracking ---");
        System.out.println("Each round's scores are saved to a local SQLite database file.");
        System.out.println("You can view total scores at any time via the main menu.");
        System.out.println("The database keeps track of each player's score per round and shows totals.");
        System.out.println("Scores are grouped by Game ID, which increases automatically when you restart the game.");
        System.out.println();

        System.out.println("Your goal is to be the first to play all your cards, which makes you the winner of the round and awards you points.");
        System.out.println("And whoever reaches 500 points first is the WINNER of the entire Game! Good luck!");
        mainMenu(currentPlayer);
    }
    // Menü zum Spiel beenden mit Sicherheitsabfrage
    static void exitGame() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Are you sure you want to end the game? Yes(1) or No(2)?");
        int endAuswahl = scanner.nextInt();
        switch (endAuswahl){
            case 1:// Spiel wirklich beenden
                System.out.println("See you next time!");
                System.exit(0);
            case 2: // Zurück ins Menü
                System.out.println("Back to the menu!");
                mainMenu(currentPlayer);
        }
    }
    // Zeigt aktuelle Punkte aller Spieler dieser Runde
    public static void viewScores() {
        int gameId = DBManager.getGameId();

        String query = "SELECT PlayerName, SUM(Score) as TotalScore " +
                "FROM Scores WHERE GameID = " + gameId + " " +
                "GROUP BY PlayerName ORDER BY TotalScore DESC";

        try {
            var results = DBManager.getDb().executeQuery(query);

            if (results.isEmpty()) {
                System.out.println("No scores found for Game ID: " + gameId);
                System.out.println(" ");
            } else {
                System.out.println("|| Total Scores for Game " + gameId + " ||");
                for (var row : results) {
                    System.out.println(row.get("PlayerName") + ": " + row.get("TotalScore") + " points");
                }
                System.out.println(" ");
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving scores: " + e.getMessage());
        }

        mainMenu(currentPlayer);
    }
}
