public class UnoMain {
    public static void main(String[] args) {
        DBManager.initialiseDB(); //initialise database

        Table.GameInitialisation();
        ActionManager.initializePlayers(Table.players); //to sync ActionManager with Table Player ArrayList

        while (Table.playing) {
            Table.GamePlay();
            Table.setupNewRound();
        }
    }
}