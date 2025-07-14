import java.util.Scanner;

public class DrawFourMenu {

    static Player currentPlayer;

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
                case "1":
                    PunishmentManager.plusFour(1, currentPlayer, previousPlayer);
                    return;

                case "2":
                    ActionManager.drawFourCheck(previousPlayer);

                    if (previousPlayer.isCheater()) {
                        PunishmentManager.plusFour(2, currentPlayer, previousPlayer);
                        previousPlayer.setCheater(false); // Reset cheat flag
                    } else {
                        PunishmentManager.plusFour(3, currentPlayer, previousPlayer);
                    }
                    return;

                case "3":
                    Menu.help();
                    break;

                case "4":
                    Menu.exitGame();
                    break;

                default:
                    System.out.println("Wrong input, try again.");
            }
        }
    }
}

