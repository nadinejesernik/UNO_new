public class UnoMain {
    public static void main(String[] args) {
        Table.GameInitialisation();
        while (Table.playing) {
            Table.GamePlay();
            Table.setupNewRound();
        }
    }
}