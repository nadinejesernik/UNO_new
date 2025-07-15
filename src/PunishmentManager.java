public class PunishmentManager {
    //automatisch 1 Karte gezogen bei ungültigem Spielzug
    public static void invalidCard(Player player) {
        System.out.println("This card is not valid. "+player.playerName+" draws 1 card as punishment");
        player.PlayerDrawsCard();
    }
    // 2 Karten werden gezogen, wenn kein UNO gerüfen wird bei 2 Karten in der Hand
    public static void noUNOCalled(Player player) {
        System.out.println(player.getPlayerName() + " forgot to call UNO!");
        System.out.println("Drawing 2 cards as punishment...");
        player.PlayerDrawsCard();
        player.PlayerDrawsCard();
    }
    //Bestrafung für DrawFour
    public static void plusFour(int punishment, Player currentPlayer, Player previousPlayer) {
        switch (punishment) {
            case 1://Spieler zieht 4 Karten (kein Beschuldigen)
                System.out.println(currentPlayer.getPlayerName() + " did not challenge. Drawing 4 cards...");
                for (int i = 0; i < 4; i++) {
                    currentPlayer.PlayerDrawsCard();
                }
                break;
            case 2://Vorheriger Spieler kriegt 4 Karten (korrekt beschuldigt)
                System.out.println(currentPlayer.getPlayerName() + " challenged and was RIGHT!");
                System.out.println(previousPlayer.getPlayerName() + " must draw 4 cards.");
                for (int i = 0; i < 4; i++) {
                    previousPlayer.PlayerDrawsCard();
                }
                break;
            case 3://Spieler kriegt 6 Karten (beschuldigung war falsch)
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
