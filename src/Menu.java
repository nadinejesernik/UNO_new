import java.util.Scanner;

public class Menu {
    static Player currentPlayer;
    static void mainMenu(Player player) { //momentaner Spieler als Parameter hinugefügt
        currentPlayer = player;
        //GameLoop zum probieren
        boolean playerWin = false;
        int playerCount = 1;//wird durch index vom spieler ersetzt
       // while (!playerWin) {
            //Probieren ob theoretisch new Player Loop passiert
            boolean nextPlayer = false;
           // while (!nextPlayer && playerCount<4) {
                System.out.println("it's " + currentPlayer.playerName + "'s turn");
                System.out.println("Choose an option:");
                System.out.println("1.Play");
                System.out.println("2.Help");
                System.out.println("3.Exit");
                Scanner scanner = new Scanner(System.in);
                String menuAuswahl = scanner.next();
                switch (menuAuswahl) {
                    case "1":
//                        playGame(playerCount);
//                        playerCount++;
//                        if(playerCount==5){
//                            playerCount =1;
//                        }
//                        nextPlayer=true;
                        currentPlayer.playCard();
                        break;
                    case "2":
                        help();
                        break;
                    case "3":
                        exitGame();
                        break;
                    default:
                        System.out.println("Wrong input, try again");
                        mainMenu(currentPlayer);
                        break;
               // }
         //   }
        }
    }

    //momentan player Count später Name des Spielers
    static void playGame(int playerCount) {
        //hier sieht man die oberste Karte vom Stapel
        System.out.println("Player " + currentPlayer.playerName + " these are your cards:");
        //hier kommt showHand
        //hier kommt die CardValidity
        //the game checks if you have any card that you can lay;
        // if not you get the message "you have no cards that you could lay down rn" and get a card added to your hand


        // if you can lay something the game asks "which card would you like to lay?"

//        while(true) {
//            System.out.println("Which card would you like to play? 1-"+currentPlayer.hand.size());//+HandArray.length//
//            if(CheckInput.splitCheckInput(currentPlayer)){
//                break;
//            }
//        }

        // that gets split and checked
        // if everything is good the card gets put on the discard pile
        // and if there is a second command that one also goes
        // if wrong card put down you get a penalty
        // with that your turn concludes

    }
    static void help() {
        System.out.println("=== HOW TO PLAY UNO ===");

        // Allgemeine Regeln
        System.out.println("--- UNO Rules ---");
        System.out.println("On your turn, you must play a card that matches the top card by color or number.");
        System.out.println("If you can't play a valid card, you must draw one.");
        System.out.println("Wild cards can be played at any time, but if you have a matching normal card,");
        System.out.println("playing a wild card instead may be considered cheating.");
        System.out.println("If you have only one card left, you MUST declare 'UNO'.");
        System.out.println("Failing to declare 'UNO' results in an automatic 2-card penalty.");
        System.out.println("For more specifics visit: https://www.uno-kartenspiel.de/wp-content/uploads/2019/08/UNO-Spielregeln-Anleitung.pdf");
        System.out.println(" ");
        // Accuse-Regel
        System.out.println("--- Wild Draw Four Rule ---");
        System.out.println("If the player before you plays a +4 Wild card, you normally must draw 4 cards and skip your turn.");
        System.out.println("However, you can challenge them if you think they could have played a regular card instead.");
        System.out.println("When this happens, you will be shown a special menu with two options:");
        System.out.println("1. Draw cards");
        System.out.println("2. Help");
        System.out.println("Instead of choosing one of these options, you can also type:");
        System.out.println("accuse");
        System.out.println("This will start a challenge. If you're right, they draw 4 cards instead. If you're wrong, you draw 6.");
        System.out.println(" ");

        // Eingabeformat
        System.out.println("--- Input Format ---");
        System.out.println("When it's your turn, your hand will be shown with numbered cards.");
        System.out.println("To play a card, enter the index of the card (starting at 1).");
        System.out.println("You can also include special commands in the same input:");
        System.out.println("To declare UNO:       2 UNO");
        System.out.println("The system accepts commands separated by spaces.");
        System.out.println(" ");

        System.out.println("Your goal is to be the first to play all your cards, which makes you the winner of the round and awards you points.");
        System.out.println("And whoever reaches 500 points first is the WINNER! Good luck!");
        mainMenu(currentPlayer);
    }
    static void exitGame() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Do you wanna Save & Exit(1) or Exit(2)?");
        int endAuswahl = scanner.nextInt();
        switch (endAuswahl){
            case 1:
                System.out.println("You saved your Game Progress!");
                //hier ist das in die Datenbank speichern
                System.out.println("See you next time!");
                System.exit(0);
            case 2:
                System.out.println("See you next time!");
                System.exit(0);
        }
    }
}
