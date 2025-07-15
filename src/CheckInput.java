import java.util.Scanner;

public class CheckInput {
    //Prüft die Eingabe eines HumanPlayers
    static boolean splitCheckInput(String input, Player currentPlayer){
        String regex = "\\s+";
        String[] inputArray = input.trim().split(regex);
        // Leere Eingabe abfangen
        if (inputArray.length == 0 || inputArray[0].isEmpty()) {
            System.out.println("Input cannot be empty.");
            return false;
        }
        int index;
        try {// Versucht, die erste Eingabe als Kartennummer zu interpretieren
            index = Integer.parseInt(inputArray[0]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input: please start with a number.");
            return false;
        }
        // Prüft, ob die Kartennummer innerhalb des gültigen Bereichs liegt
        if (index < 1 || index > currentPlayer.hand.size()) {
            System.out.println("Invalid card number. Please enter a number between 1 and " + currentPlayer.hand.size());
            return false;
        }
        // Prüft, ob man optional noch UNO gerufen hat
        if (inputArray.length >= 2) {
            String command = inputArray[1];
            switch (command.toUpperCase()) {
                case "UNO":
                    // UNO darf nur bei genau 2 Karten gesagt werden
                    if (currentPlayer.getHand().size() != 2) {
                        System.out.println("You can only declare UNO when you have exactly 2 cards.");
                        return false;
                    }
                    System.out.println(currentPlayer.playerName +": UNO!");
                    currentPlayer.declareUNO();
                    break;
                default://Unbekannte Eingabe
                    System.out.println("Unknown command, please try again.");
                    return false;
            }
        }
        // Eingabe ist gültig
        return true;
    }
}


