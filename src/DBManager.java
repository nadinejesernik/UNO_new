import java.sql.SQLException;
import java.util.ArrayList;

public class DBManager {
    private static SqliteClient db;

    public static void initialiseDB() {
        try {
            db = new SqliteClient("uno_game.db");

            if (!db.tableExists("Scores")) {
                db.executeStatement(
                        "CREATE TABLE Scores (" +
                                "GameID INTEGER, " +
                                "RoundNo INTEGER, " +
                                "PlayerName TEXT, " +
                                "Score INTEGER)"
                );
                System.out.println("Database and Scores table created.");
            } else {
                System.out.println("Database connected. Scores table exists.");
            }

        } catch (SQLException e) {
            System.err.println("Database initialization failed: " + e.getMessage());
        }
    }

    public static void saveScoresForRound(int gameId, int roundNo, ArrayList<Player> players) {
        try {
            for (Player player : players) {
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
}
