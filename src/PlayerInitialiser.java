import java.util.Random;
import java.util.Scanner;

public class PlayerInitialiser {
    public static Player[] initializePlayers() {
        Scanner scanner = new Scanner(System.in);
        Player[] players = new Player[4];

        System.out.print("How many people are playing? (0-4) ");
        int humanCount = -1; //initialise with invalid value to start while-loop

        // check if input is valid
        while (humanCount < 0 || humanCount > 4) {
            if (scanner.hasNextInt()) {
                humanCount = scanner.nextInt();
                if (humanCount < 0 || humanCount > 4) {
                    System.out.print("Invalid. Please enter a number between 0 and 4: ");
                }
            } else {
                System.out.print("Invalid input. Please enter a number between 0 and 4: ");
                scanner.next(); // clear buffer
            }
        }

        // Set amount of human players
        scanner.nextLine(); // clear buffer
        for (int i = 0; i < humanCount; i++) {
            System.out.print("Enter a name for Player" + (i + 1) + ": ");
            String name = scanner.nextLine();
            players[i] = new HumanPlayer(name);
        }

        // fill with comp players
        for (int i = humanCount; i < 4; i++) {
            players[i] = new CompPlayer("COM" + (i +1));
        }

        return players;
    }

    public static void showPlayers(Player[] players) {
        System.out.println("Players: ");
        for (Player p : players) {
            System.out.println(p.getClass().getSimpleName() + ": " + p.getPlayerName());
        }
    }

    public static Player[] chooseFirstPlayer(Player[] players) {
        Random rand = new Random();
        int firstPlayer = rand.nextInt(players.length);

        System.out.println("\nPlayer " + players[firstPlayer].getPlayerName() + " begins");
        return players;
    }
}
