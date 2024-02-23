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
        Scanner scanner = new Scanner(System.in);
        //take player input, check if the spot is taken, if not enter in depending on inp
        freeSpace = true; //set to true so it enters the loop whenever human() is called
        while(freeSpace) {
            //set human input and run human turn
            setInput(scanner.next().charAt(0));
            freeSpace = false; //set to false so doesn't remain true and automatically exit loop
            boardInput();
            turnCheck();
            if (freeSpace) {
                gameBoard[row][column] = 'O';
                freeSpace = false; //so it exits loop
            } else {
                System.out.println("Space not available, try again");
                freeSpace = true; //so stays in loop
            }
        }
    }


    //Runs ai's turn
    public void ai() {


        int score;
        //sets best score to rly low number so all future scores are greater than
        int bestScore = -999999;
        //set both to -1 because they are an invalid place on the board
        int bestMoveI = -1;
        int bestMoveJ = -1;
        //loop through each spot in the board
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {

                // Check if the spot is available
                if (gameBoard[i][j] == '*') {
                    //if spot is available replace the star with an X.
                    gameBoard[i][j] = 'X';
                    //Now run minimax on this board with the new X (isMaximizing is false because we just placed an X so next turn is O
                    score = minimax(gameBoard, 0, false);
                    //Undo move on real board
                    gameBoard[i][j] = '*';

                    //If the score we got from minimax> our best score, replace best score as we want the highest score possible (aka the best move)
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

        //For debugging purposes
        System.out.println(bestScore);

    }

    //Minimax
    public int minimax(char[][] arr, int depth, boolean isMaximizing){
        //check for a win and if someone did win return score accordingly
        //If X win, +10, if O win, -10, if tie, +0
        checkWin();

        //I think there is something wrong here are the only scores that are returned and given to best score are 1, -1, and 0.
        if(winner){
            //If there is a winner check who won
            if(isMaximizing){
                winner = false;
                return -10 + depth;
            } else{
                winner = false;
                return 10 - depth;
            }
        } else if(tie){
            tie = false;
            return 0;
        }

        //create two variables for score and best score
        int bestScoreAI;
        int scoreAI;

        //If we are maximizing (trying to find highest score for X)
        if(isMaximizing){
            //Set best score super low
            bestScoreAI = -999999;
            //Loop through board to find empty space
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    //if spot empty place an X
                    if(arr[i][j] == '*'){
                        arr[i][j] = 'X';
                        //Call minimax on new board
                        scoreAI = minimax(arr, depth + 1, false);
                        //undo move
                        arr[i][j] = '*';
                        //If score from minimax > best score, change best score (we are trying to maximize)
                        if(scoreAI > bestScoreAI){
                            bestScoreAI = scoreAI;
                        }
                    }
                }
            }
            /*reset the winner and tie as they were called above so if we run multiple instances of our board eventually the game
            *   will end and there will be a winner/ tie and we don't want that to stay or else everytime we call minimax later on
            *   it will automatically go into the winner condition returning 1/-1 */
            winner = false;
            tie = false;
            //return best score
            return bestScoreAI;

            //this is for minimizing
        } else{
            //since minimizing set bestscore super high because we want a low score
            bestScoreAI = 9999999;
            //again loop through board and change to X and call minimax this time for maximizing
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if(arr[i][j] == '*'){
                        arr[i][j] = 'O';
                        scoreAI = minimax(arr, depth + 1, true);
                        arr[i][j] = '*';
                        //check and replace best score if necessary
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

    //Check if the space is free
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
            //checks horizontal
            if (gameBoard[i][0] == gameBoard[i][1] && gameBoard[i][1] == gameBoard[i][2] && gameBoard[i][0] != '*') {
                winner = true;
                return;
            }
            //checks vertical
            if (gameBoard[0][i] == gameBoard[1][i] && gameBoard[1][i] == gameBoard[2][i] && gameBoard[0][i] != '*') {
                winner = true;
                return;
            }
        }

        //checks diagonal
        if (gameBoard[0][0] == gameBoard[1][1] && gameBoard[1][1] == gameBoard[2][2] && gameBoard[0][0] != '*') {
            winner = true;
            return;
        }
        //checks diagonal
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

    //checks if the game is over
    public void checkGameOver(){
        //if there is a winner or a tie then the game is over
        //I did it this way so it didnt interfere with the minimax since I didn't make a copy of the board
        if(winner || tie){
            gameOver = true;
        }
    }


    //Runs a round. Based off the turn it knows if it is player 1's turn or player 2's
    public void playRound() {
            if (turn % 2 != 0) {
                //runs ai turn first
                ai();
            } else if (turn % 2 == 0) {
                human();
            } else {
                System.out.println("Try again. Invalid space.");
            }
            //incrememnt turn
            turn++;

    }

        //Plays the game. Prints the board at the start, and then after every round respectively
        public void playGame(){

            //runs as long as game isnt over
            while (!gameOver) {
                //play round
                playRound();
                //prints out board
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        System.out.print(gameBoard[i][j] + " ");
                    }
                    System.out.println();
                }
                //checks for win
                checkWin();
                //for debugging purposes
                System.out.println("winner: " + winner + " tie: " + tie);
                //checks if game is over
                checkGameOver();
            }

            //prints out board final time
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    System.out.print(gameBoard[i][j] + " ");
                }
                System.out.println();
            }

            //prints out who won or if there was a tie
            if(tie){
                System.out.println("Tie");
            } else if((turn - 1) % 2 != 0){
                System.out.println("X WON!");
            } else{
                System.out.println("O WON!");
            }
        }
}
