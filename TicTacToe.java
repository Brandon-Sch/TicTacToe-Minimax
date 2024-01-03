import java.util.Scanner;
public class TicTacToe {
    //Instance variables
    private char[][] gameBoard = new char[3][3];
    private boolean gameOver = false;
    private boolean winner = false;
    private boolean tie = false;
    private int row;
    private int column;
    private char input;
    private char letter;
    private boolean freeSpace;
    private int turn = 1;


    //Make constructor. When created, creates a game board which is a 3x3 array full of *. * are used as a place holder.
    public TicTacToe() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                gameBoard[i][j] = '*';
            }
        }
    }

    //Method to set input variable to the players input
    public void setInput(char inp) {
        input = inp;
    }

    //Used in GameRun class so the game doesn't run indefinitely.
    /*public boolean isGameOver() {
        return gameOver;
    }*/

    //Gets the player input and depending on the input assigns the row and column of the array to the respective variables.
    //These variables are used later on to change the array value to plug in the X and O respectively
    public void boardInput() {
        //sets the row and column equal to the value of which the key correlates to
        if (input == 'q') {
            row = 0;
            column = 0;
        } else if (input == 'w') {
            row = 0;
            column = 1;
        } else if (input == 'e') {
            row = 0;
            column = 2;
        } else if (input == 'a') {
            row = 1;
            column = 0;
        } else if (input == 's') {
            row = 1;
            column = 1;
        } else if (input == 'd') {
            row = 1;
            column = 2;
        } else if (input == 'z') {
            row = 2;
            column = 0;
        } else if (input == 'x') {
            row = 2;
            column = 1;
        } else if (input == 'c') {
            row = 2;
            column = 2;
        }
    }

    //Runs player 1 turn. Player 1 is O. based off row and column variable sets the spot to O.
    public void human() {
        //take player input, check if the spot is taken, if not enter in depending on inp
            gameBoard[row][column] = 'O';
    }


    public void ai() {
        int score;
        int bestScore = -999999;
        int bestMoveI = -1;
        int bestMoveJ = -1;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                // Check if the spot is available

                if (gameBoard[i][j] == '*') {
                    gameBoard[i][j] = 'X';
                    score = minimax(gameBoard, 0, false);
                    gameBoard[i][j] = '*';

                    if (score > bestScore) {
                        bestScore = score;
                        bestMoveI = i;
                        bestMoveJ = j;
                    }
                }
            }
        }

        // Set 'X' on the best move
        if (bestMoveI != -1 && bestMoveJ != -1) {
            gameBoard[bestMoveI][bestMoveJ] = 'X';
        }
        System.out.println(bestScore);

    }

    public int minimax(char[][] arr, int depth, boolean isMaximizing){
        //check for a win and if someone did win return score accordingly
        //If X win, +10, if O win, -10, if tie, +0
        checkWin();
        if(winner){
            if(isMaximizing){
                winner = false;
                return 10;
            } else{
                winner = false;
                return -10;
            }
        } else if(tie){
            tie = false;
            return 0;
        }

        int bestScoreAI;
        if(isMaximizing){
            bestScoreAI = -999999;
        } else{
            bestScoreAI = 9999999;
        }

        int scoreAI;

        if(isMaximizing){
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if(gameBoard[i][j] == '*'){
                        gameBoard[i][j] = 'X';
                        scoreAI = minimax(arr, depth + 1, false);
                        gameBoard[i][j] = '*';
                        if(scoreAI > bestScoreAI){
                            bestScoreAI = scoreAI;
                        }
                    }
                }
            }
            winner = false;
            tie = false;
            return bestScoreAI;
        } else{
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if(gameBoard[i][j] == '*'){
                        gameBoard[i][j] = 'X';
                        scoreAI = minimax(arr, depth + 1, true);
                        gameBoard[i][j] = '*';
                        if(scoreAI < bestScoreAI){
                            bestScoreAI = scoreAI;
                        }
                    }
                }
            }
            winner = false;
            tie = false;
            return bestScoreAI;
        }
    }

    public void turnCheck() {
        if(gameBoard[row][column] == '*') {
            freeSpace = true;
        } else {
            freeSpace = false;
        }
    }

    //Checks if a player got 3 in a row. If all the spaces are filled but there is no winner then it is a tie.
    public void checkWin() {
        for (int i = 0; i < 3; i++) {
            if (gameBoard[i][0] == gameBoard[i][1] && gameBoard[i][1] == gameBoard[i][2] && gameBoard[i][0] != '*') {
                winner = true;
                return;
            }
            if (gameBoard[0][i] == gameBoard[1][i] && gameBoard[1][i] == gameBoard[2][i] && gameBoard[0][i] != '*') {
                winner = true;
                return;
            }
        }

        if (gameBoard[0][0] == gameBoard[1][1] && gameBoard[1][1] == gameBoard[2][2] && gameBoard[0][0] != '*') {
            winner = true;
            return;
        }

        if (gameBoard[0][2] == gameBoard[1][1] && gameBoard[1][1] == gameBoard[2][0] && gameBoard[0][2] != '*') {
            winner = true;
            return;
        }

        // Check for a tie
        tie = true;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (gameBoard[i][j] == '*') {
                    tie = false;
                    return;
                }
            }
        }
    }

    public void checkGameOver(){
        if(winner || tie){
            gameOver = true;
        }
    }


    //Runs a round. Based off the turn it knows if it is player 1's turn or player 2's
    public void playRound() {
        Scanner scanner = new Scanner(System.in);
        // get human input

        //turnCheck();

            if (turn % 2 != 0) {
                ai();
            } else if (turn % 2 == 0) {
                setInput(scanner.next().charAt(0));
                boardInput();
                human();
            } else {
                System.out.println("Try again. Invalid space.");
            }
            turn++;

    }

        //Plays the game. Prints the board at the start, and then after every round respectively
        public void playGame(){

            while (!gameOver) {
                playRound();

                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        System.out.print(gameBoard[i][j] + " ");
                    }
                    System.out.println();
                }
                checkWin();
                System.out.println("winner: " + winner + " tie: " + tie);
                checkGameOver();
            }

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    System.out.print(gameBoard[i][j] + " ");
                }
                System.out.println();
            }

            if(tie){
                System.out.println("Tie");
            } else if((turn - 1) % 2 != 0){
                System.out.println("X WON!");
            } else{
                System.out.println("O WON!");
            }
        }
}
