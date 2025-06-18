import java.util.Scanner;

public class CheckInput {

//    static void splitInput(){
//        Scanner scanner = new Scanner(System.in);
//        String input = scanner.nextLine();
//        String regex = "\\s+";
//        String[] inputArray = input.split(regex);
//    }

    static boolean splitCheckInput(String input, Player currentPlayer){
        String regex = "\\s+";
        String[] inputArray = input.trim().split(regex);

        int index = Integer.valueOf(inputArray[0]);

        if(index >0 && index <= currentPlayer.hand.size()){
            System.out.println("you have put that card down");
            //here is the puttingDownCard function
            if(inputArray.length >= 2){
                String command = inputArray[1];
                switch (command.toUpperCase()){
                    case "UNO":
                        //da muss noch überprüft werden, ob man die bedingungen für das UNO erfüllt
                        System.out.println("UNO!");
                        return true;
                    default:
                        System.out.println("Try again");
                        return false;
                }
            }
        }
        return false;
    }
}


