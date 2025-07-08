import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class PlayerInitialiser {
    public static ArrayList<Player> initializePlayers() {
        Scanner scanner = new Scanner(System.in);

        int playerCount = 4;
        ArrayList<Player> players = new ArrayList<>();

        System.out.println("Welcome! " + playerCount + " players are now playing!");

        int humanCount = -1; //initialise with invalid value to start while-loop

        System.out.print("\nOf these players, how many are human? ");
        while (humanCount < 0 || humanCount > playerCount) {
            if (scanner.hasNextInt()) {
                humanCount = scanner.nextInt();
                if (humanCount < 0 || humanCount > playerCount) {
                    System.out.print("Invalid. Please enter a number between 0 and the amount of players: ");
                }
            } else {
                System.out.print("Invalid input. Please enter a number between 0 and the amount of players: ");
                scanner.nextLine(); // clear buffer
            }
        }

        Table.humanCount = humanCount;
        // Set amount of human players
        scanner.nextLine(); // clear buffer
        for (int i = 0; i < humanCount; i++) {
            System.out.print("Enter a name for Player " + (i + 1) + " ");
            String name = scanner.nextLine();
            players.add(new HumanPlayer(name));
        }

        // fill with comp players
        for (int i = humanCount; i < playerCount; i++) {
            players.add(new CompPlayer("COM" + (i + 1)));
        }

        return players;
    }

    public static void showPlayers(List<Player> players) {
        System.out.println("\nPlayers: ");
        for (Player p : players) {
            System.out.println(p.getClass().getSimpleName() + ": " + p.getPlayerName());
        }
    }

    public static ArrayList<Player> chooseFirstPlayer(List<Player> players) {
        Random rand = new Random();
        int firstPlayer = rand.nextInt(players.size());

        System.out.println("\nPlayer " + players.get(firstPlayer).getPlayerName() + " begins");

        // ✅ Rotate list so first player is at the front
        ArrayList<Player> rotated = new ArrayList<>();
        for (int i = 0; i < players.size(); i++) {
            rotated.add(players.get((firstPlayer + i) % players.size()));
        }

        return rotated;
    }
}
