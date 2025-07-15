public class UnoMain {
    public static void main(String[] args) {
        DBManager.initialiseDB(); //initialisiere Datenbank

        Table.GameInitialisation();
        ActionManager.initializePlayers(Table.players); //synchronisiert ActionManager mit Player ArrayList in Table

        while (Table.playing) { //Gameplay Loop
            Table.GamePlay();
            if (Table.playing) { //playing wird durch erreich von 500 Punkten eines Spielers auf false gesetzt
                Table.setupNewRound();
            }
        }
    }
}