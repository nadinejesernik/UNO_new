import java.sql.SQLException;

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

    public static SqliteClient getDb() {
        return db;
    }
}
