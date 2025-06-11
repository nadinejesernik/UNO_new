import java.util.Random;
import java.util.Scanner;

public class PlayerInitialiser {
    public static Player[] initializePlayers() {
        Scanner scanner = new Scanner(System.in);
        int playerCount; 

        System.out.println("How many players should play in this game? ");
        playerCount = scanner.nextInt();
        Player[] players = new Player[playerCount];

        System.out.println("Great! " + playerCount + " players are now playing!");

        int humanCount = -1; //initialise with invalid value to start while-loop

        System.out.print("\nOf these players, how many are human?");
        while (humanCount < 0 || humanCount > playerCount) {
            if (scanner.hasNextInt()) {
                humanCount = scanner.nextInt();
                if (humanCount < 0 || humanCount > playerCount) {
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
        for (int i = humanCount; i < playerCount; i++) {
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

        //create new Player array with the starting Player in the beginning
        Player[] rotated = new Player[players.length];
        for (int i = 0; i < players.length; i++) {
            rotated[i] = players[(firstPlayer + i) % players.length]; //i.g. Player at index [3] starts in 4 player game: 3+0 % 4 = 3
        }

        return rotated;
    }
}
