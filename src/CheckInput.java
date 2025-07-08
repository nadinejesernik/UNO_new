import java.util.Scanner;

public class CheckInput {

    static boolean splitCheckInput(String input, Player currentPlayer){
        String regex = "\\s+";
        String[] inputArray = input.trim().split(regex);

        if (inputArray.length == 0 || inputArray[0].isEmpty()) {
            System.out.println("Input cannot be empty.");
            return false;
        }

        int index;
        try {
            index = Integer.parseInt(inputArray[0]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input: please start with a number.");
            return false;
        }
        if (index < 1 || index > currentPlayer.hand.size()) {
            System.out.println("Invalid card number. Please enter a number between 1 and " + currentPlayer.hand.size());
            return false;
        }
        if (inputArray.length >= 2) {
            String command = inputArray[1];
            switch (command.toUpperCase()) {
                case "UNO":
                    if (currentPlayer.getHand().size() != 2) {
                        System.out.println("You can only declare UNO when you have exactly 2 cards.");
                        return false;
                    }
                    System.out.println(currentPlayer.playerName +": UNO!");
                    currentPlayer.declareUNO();
                    break;
                default:
                    System.out.println("Unknown command, please try again.");
                    return false;
            }
        }

        return true;
    }
}


