public class PunishmentManager {
    public static void invalidCard(Player player) {
        System.out.println("The card you chose cannot be played on " + DiscardPile.showTopCard() + ".");
        System.out.println(player.getPlayerName() + " must draw 1 card as punishment.");
        player.PlayerDrawsCard();
    }

    public static void noUNOCalled(Player player) {
        System.out.println(player.getPlayerName() + " forgot to call UNO!");
        System.out.println("Drawing 2 cards as punishment...");
        player.PlayerDrawsCard();
        player.PlayerDrawsCard();
    }
    public static void plusFour(int punishment, Player currentPlayer, Player previousPlayer) {
        switch (punishment) {
            case 1:
                System.out.println(currentPlayer.getPlayerName() + " did not challenge. Drawing 4 cards...");
                for (int i = 0; i < 4; i++) {
                    currentPlayer.PlayerDrawsCard();
                }
                break;
            case 2:
                System.out.println(currentPlayer.getPlayerName() + " challenged and was RIGHT!");
                System.out.println(previousPlayer.getPlayerName() + " must draw 4 cards.");
                for (int i = 0; i < 4; i++) {
                    previousPlayer.PlayerDrawsCard();
                }
                break;
            case 3:
                System.out.println(currentPlayer.getPlayerName() + " challenged but was WRONG!");
                System.out.println("Drawing 6 cards...");
                for (int i = 0; i < 6; i++) {
                    currentPlayer.PlayerDrawsCard();
                }
                break;
            default:
                System.out.println("Invalid punishment code.");
                break;
        }
    }
}
