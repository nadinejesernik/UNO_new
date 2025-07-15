import java.sql.SQLException;
import java.util.ArrayList;

public class DBManager {
    private static SqliteClient db;
    private static int gameId = -1;


    public static void initialiseDB() {
        try {
            db = new SqliteClient("uno_game.db");

            if (!db.tableExists("Scores")) { //Neue Table erstellen
                db.executeStatement(
                        "CREATE TABLE Scores (" +
                                "GameID INTEGER, " +
                                "RoundNo INTEGER, " +
                                "PlayerName TEXT, " +
                                "Score INTEGER)"
                );
                System.out.println("Database and Scores table created.");
                gameId = 1;  // Das erste Spiel bekommt ID1
            } else { //Wenn Table schon existiert
                System.out.println("Database connected. Scores table exists.");

                // Wähle die höchste GameID (das aktuellste Spiel) und speichere in results
                var results = db.executeQuery("SELECT MAX(GameID) as MaxID FROM Scores;");
                if (!results.isEmpty() && results.get(0).get("MaxID") != null) { //Bedingung, dass Table nicht leer ist
                    gameId = Integer.parseInt(results.get(0).get("MaxID")) + 1; //um GameID eines neuen Spieles immer um 1 zu erhöhen
                } else { //Wenn Table zwar schon besteht aber noch keine Werte gespeichert hat
                    gameId = 1;
                }
            }

        } catch (SQLException e) {
            System.err.println("Database initialization failed: " + e.getMessage());
        }
    }


    public static void saveScoresForRound(int gameId, int roundNo, ArrayList<Player> players) {
        try {
            for (Player player : players) { //loopt durch Player Array
                String sql = String.format(
                        "INSERT INTO Scores (GameID, RoundNo, PlayerName, Score) VALUES (%d, %d, '%s', %d);",
                        gameId, roundNo, player.getPlayerName(), player.points
                );
                db.executeStatement(sql);
            }
            System.out.println("Database Update. Scores saved for round " + roundNo);
        } catch (SQLException e) {
            System.err.println("Error saving scores: " + e.getMessage());
        }
    }


    public static SqliteClient getDb() {
        return db;
    }

    public static int getGameId() {
        return gameId;
    }

}
