/* How to play:
    X starts off.
    Press a key that corresponds with the place you want to go and press enter.

    The board corresponds like so:

    q w e
    a s d
    z x c

*/

public class GameRun {
    public static void main(String[] args) {
        //creates new tictactoe game
        TicTacToe g1 = new TicTacToe();
         //plays game
        g1.playGame();
    }
}
