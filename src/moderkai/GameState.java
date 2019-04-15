/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moderkai;

import java.io.*;
import data.Player;
import view.*;

public class GameState {
    public static final double FIVE_IN_A_ROW = Double.POSITIVE_INFINITY;
    public static final double STRAIGHT_FOUR_POINTS = 1000;
    public static final double FOURS_POINTS = 500;
    public static final double THREES_POINTS = 100;
    public static final double TWOS_POINTS = 5;
    public static final double ONES_POINTS = 1;

    private String status;
    private static Game game;
    private static Piece[][] board;
    private static boolean DEBUG = false;
    private static int moveCount;

    /**
     * Instantiate a real GameState
     */
    public GameState(Game game) {
        this.game = game;
        board = game.getBoard();
    }

    /**
     * Parse Game state from input
     * @param input reader for the gomoku socket
     */
//    private void parseGameState(BufferedReader input) //get data from server
//    {
//        int i = 0, k = 0, count = 0;
//        char[] temp;
//
//        try {
//            if(input.ready()) {
//                if(DEBUG)System.out.println("start reading");
//                do{
//                    String serverData = input.readLine();
//                    if(DEBUG)System.out.println("["+i+"] "+ serverData);
//                    if (i == 0) //reading the first line for game status 'continuing, 'win, 'lose, 'draw, 'forfeit-time, 'forfeit-move'
//                    {
//                        status = serverData;
//                        reEvaluate(serverData);
//                    } else if (i == 1 || i <= boardSize) { //series of lines of characters representing each row of the current SQUARE-board; characters one of: "x", "o", or " " (space)
//                        if(i == 1){
//                            boardSize = serverData.length();
//                            board = new char[boardSize][boardSize];
//                        }
//                        temp = serverData.toCharArray();
//                       if (DEBUG) {for (int b = 0; b < temp.length; b++) {System.out.println("[" + temp[b] + "](" + b + ")");}}
//                        for (int j = 0; j < boardSize; j++) {
//                            board[k][j] = temp[j];
//                            if(temp[j] != ' ') count++;
//                        }
//                        k++;
//
//                    } else if (i == (boardSize + 1)) {
//                        moveCount = count; //set move count after going through all spaces.
//                        player = serverData.charAt(0); //save player as x (black) or o (white)
//                        if(DEBUG) System.out.println("Player: " + player);
//                    }
//
//                    i++;
//
//                }while(input.ready());
//            }
//        } catch (IOException e) {
//            System.err.println("Couldn't get I/O!");
//            e.printStackTrace();
//            System.exit(1);
//        }
//        if(DEBUG)System.out.println("finish reading");
//    }

//    private void reEvaluate(String s) //game-status as one of 'continuing, 'win, 'lose, 'draw, 'forfeit-time, 'forfeit-move
//    {
//        boolean reEval;
//        if (status.equals("continuing")) {
//            reEval = true;
//        } //need to read board placement again
//        else if (status.equals("forfeit-time")) {
//            reEval = false;
//        } //does not need to read board placement, can use last saved
//        else if (status.equals("forfeit-time")) {
//            reEval = false;
//        } //does not need to read board placement, can use last saved
//    }

    /**
     * If our player is 'o', we will move on odd turns,
     * if it is 'x', we will move on even turns.
     *
     * @return whether it is the AI's turn.
     */

    /**
     *
     * @return the current game status
     */
    public String getStatus() {
        return this.status;
    }

    private static boolean isStraightFour(String in, char player) {
        String straightFour = " " + player + player + player + player + " ";
        if (DEBUG) System.out.println("IsStraightFour? " + in.replaceAll(" ", "-"));
        return in.equals(straightFour);
    }


    /**
     *
     * @param val the number of pieces in a row
     * @return the value to add
     */
    private static double getPointsToAdd(int val) {
        switch (val) {
            case 1:
                return ONES_POINTS;
            case 2:
                return TWOS_POINTS;
            case 3:
                return THREES_POINTS;
            case 5:
                return FIVE_IN_A_ROW;
            default:
                return 0;
        }
    }

    /**
     * Gets the utility for a specified player in a specified state.
     *
     * @param board The game board to analyze
     * @param player The player's state to analyze
     * @return The utility of the gamestate.
     */
    public static double getStateUtility() {
        double[][] maxUtility = new double[game.BOARD_SIZE][game.BOARD_SIZE];

        int enemy = 0;
        int AI = 1;
        
        double evaluation = 0.0;
        int boardLength = game.BOARD_SIZE;
        int count;
        int lastEnemyEncounteredCol, lastEnemyEncounteredRow;
        int encounteredEnemy, encounteredEnemyY;

        for (int row = 0; row < boardLength; row++) {
            lastEnemyEncounteredCol = -1;
            lastEnemyEncounteredRow = -1;
            for (int col = 0; col < boardLength; col++) {

                if (board[row][col].isAiPiece() == enemy) {
                    lastEnemyEncounteredCol = col;//keep track of the last encountered enemy
                    lastEnemyEncounteredRow = row;
                }


                //If we find the string contains the player
                if (board[row][col].isAiPiece() == AI) {

                    encounteredEnemy = -1;
                    //====================CHECK TO THE RIGHT====================
                    if (col <= boardLength - 5) {//to be sure there can actually be a 5-in-a-row to this direction

                        count = 1; //Sum of how many of our players we encounter in the next 4 spaces
                        for (int x = col + 1; x < col + 5; x++) {
                            if (board[row][x].isAiPiece() == AI) {
                                count++;
                            } else if (board[row][x].isAiPiece() == enemy) {
                                encounteredEnemy = x;
                                break;
                            }
                        }

                        if (count < 3 || count == 5) {
                            evaluation += getPointsToAdd(count);
                            if (DEBUG)
                                System.out.println("[horiz]BOARD[" + row + "][" + col + "]: ADDED UTILITY VALUE OF: " + getPointsToAdd(count));
                        } else if (count == 3) {
                            if (encounteredEnemy == -1) {
                                evaluation += THREES_POINTS;
                                if (DEBUG)
                                    System.out.println("[horiz(1)BOARD[" + row + "][" + col + "]: ADDED UTILITY VALUE OF: " + THREES_POINTS);
                            } else if (lastEnemyEncounteredCol > -1) {//we encountered an enemy before seeing our player
                                if (col - 1 >= 0 && encounteredEnemy == col + 4) {//we have enough room to make a 4, check to the left one to see if we can make a 5 (-O-X-XXO--)
                                    if (board[row][col - 1].isAiPiece() != enemy) {
                                        evaluation += THREES_POINTS;
                                        if (DEBUG)
                                            System.out.println("[horiz](2)BOARD[" + row + "][" + col + "]: ADDED UTILITY VALUE OF: " + THREES_POINTS);
                                    }
                                } else if (col - 2 >= 0 && encounteredEnemy == col + 3) {//we are stuck on 3, check to the left 2 to see if we can make it a 5
                                    evaluation += THREES_POINTS;
                                    if (DEBUG)
                                        System.out.println("[horiz](3)BOARD[" + row + "][" + col + "]: ADDED UTILITY VALUE OF: " + THREES_POINTS);
                                }
                            }
                        } else if (count == 4 && col - 1 < 0 && encounteredEnemy == -1) {//havent encountered an enemy before seeing our player
                            if (DEBUG)
                                System.out.println("[horiz](1)BOARD[" + row + "][" + col + "]: ADDED UTILITY VALUE OF: " + FOURS_POINTS);
                            evaluation += FOURS_POINTS;
                        } else if (encounteredEnemy > -1 && (col + 5 >= boardLength || col - 1 < 0)) {
                            //enemy is blocking us at the edge of the board (OXXXX)
                            if (DEBUG) System.out.println("[horiz]BLOCKING ON EDGE!!!!!!");
                        } else { //check for the straight four
                            char[] newChars = new char[6];

                            for (int b = col - 1, i = 0; b < boardLength && b < col + 5; b++, i++) {
                                newChars[i] = board[row][b].pieceToChar();
                            }
                            String rowString = new String(newChars);
                            
//                            String rowString = "";
//                            int leftBound = 0, rightBound = 0;
//                            
//                            if (col == 0 || col == 14) {
//                                rowString = " ";
//                            } else {
//                                if (col-4 < 0) {
//                                    leftBound = 0;
//                                } else {
//                                    leftBound = col-4;
//                                }        
//                                
//                                if (col+4 > 15) {
//                                    rightBound = 15;
//                                } else {
//                                    rightBound = col+4;
//                                }
//                            }
//                            
//                            for (int i = leftBound; i < rightBound; ++i) {
//                                rowString += board[row][i].toString();
//                            }
                            
                            if (isStraightFour(rowString, 'X')) {//If it is a straight 4
                                if (DEBUG)
                                    System.out.println("[horiz](1)BOARD[" + row + "][" + col + "]: ADDED UTILITY VALUE OF: " + STRAIGHT_FOUR_POINTS);
                                evaluation += STRAIGHT_FOUR_POINTS;
                            } else if (encounteredEnemy == -1) {//If it is possible to have a straight 4, and we have not encountered an enemy while searching
                                evaluation += FOURS_POINTS;
                                if (DEBUG)
                                    System.out.println("[horiz](2)BOARD[" + row + "][" + col + "]: ADDED UTILITY VALUE OF: " + FOURS_POINTS);
                            } else { //If it is possible to have a straight 4, but we have encountered an enemy while searching, check if there is room on left
                                if (board[row][col - 1].isAiPiece() != enemy) {
                                    evaluation += FOURS_POINTS;
                                    if (DEBUG)
                                        System.out.println("[horiz](3)BOARD[" + row + "][" + col + "]: ADDED UTILITY VALUE OF: " + FOURS_POINTS);
                                }
                            }
                        }

                    }//FINISH CHECKING TO THE RIGHT
                    maxUtility[row][col] = evaluation;
                    evaluation = 0;


                    encounteredEnemy = -1;
                    //====================CHECK BELOW====================
                    if (row <= boardLength - 5) {//to be sure there can actually be a 5-in-a-row to this direction

                        count = 1; //Sum of how many of our players we encounter in the next 4 spaces
                        for (int x = row + 1; x < row + 5; x++) {
                            if (board[x][col].isAiPiece() == AI) {
                                count++;
                            } else if (board[x][col].isAiPiece() == enemy) {
                                encounteredEnemy = x;
                                break;
                            }
                        }

                        if (count < 3 || count == 5) {
                            evaluation += getPointsToAdd(count);
                            if (DEBUG)
                                System.out.println("[down]BOARD[" + row + "][" + col + "]: ADDED UTILITY VALUE OF: " + getPointsToAdd(count));
                        } else if (count == 3) {
                            if (encounteredEnemy == -1) {
                                evaluation += THREES_POINTS;
                                if (DEBUG)
                                    System.out.println("[down](1)BOARD[" + row + "][" + col + "]: ADDED UTILITY VALUE OF: " + THREES_POINTS);
                            } else if (lastEnemyEncounteredRow > -1) {//we encountered an enemy before seeing our player
                                if (row - 1 >= 0 && encounteredEnemy == row + 4) {//we have enough room to make a 4, check above to see if we can make a 5 (-O-X-XXO--)
                                    if (board[row - 1][col].isAiPiece() != enemy) {
                                        evaluation += THREES_POINTS;
                                        if (DEBUG)
                                            System.out.println("[down](2)BOARD[" + row + "][" + col + "]: ADDED UTILITY VALUE OF: " + THREES_POINTS);
                                    }
                                } else if (row - 2 >= 0 && encounteredEnemy == row + 3) {//we are stuck on 3, check to the left 2 to see if we can make it a 5
                                    evaluation += THREES_POINTS;
                                    if (DEBUG)
                                        System.out.println("[down](3)BOARD[" + row + "][" + col + "]: ADDED UTILITY VALUE OF: " + THREES_POINTS);
                                }
                            }
                        } else if (count == 4 && row - 1 < 0 && encounteredEnemy == -1) {//havent encountered an enemy before seeing our player
                            if (DEBUG)
                                System.out.println("[down](0)BOARD[" + row + "][" + col + "]: ADDED UTILITY VALUE OF: " + FOURS_POINTS);
                            evaluation += FOURS_POINTS;
                        } else if (encounteredEnemy > -1 && (row + 5 >= boardLength || row - 1 < 0)) {
                            //enemy is blocking us at the edge of the board (OXXXX)
                            if (DEBUG) System.out.println("[down]BLOCKING ON EDGE!!!!!!");
                        } else { //check for the straight four
                            char[] newChars = new char[6];

                            for (int b = row - 1, i = 0; b < boardLength && b < row + 5; b++, i++) {
                                newChars[i] = board[b][col].pieceToChar();
                            }
                            String rowString = new String(newChars);

                            if (isStraightFour(rowString, 'X')) {//If it is a straight 4
                                if (DEBUG)
                                    System.out.println("[down](1)BOARD[" + row + "][" + col + "]: ADDED UTILITY VALUE OF: " + STRAIGHT_FOUR_POINTS);
                                evaluation += STRAIGHT_FOUR_POINTS;
                            } else if (encounteredEnemy == -1) {//If it is possible to have a straight 4, and we have not encountered an enemy while searching
                                evaluation += FOURS_POINTS;
                                if (DEBUG)
                                    System.out.println("[down](2)BOARD[" + row + "][" + col + "]: ADDED UTILITY VALUE OF: " + FOURS_POINTS);
                            } else { //If it is possible to have a straight 4, but we have encountered an enemy while searching, check if there is room on left
                                if (board[row - 1][col].isAiPiece() != enemy) {
                                    evaluation += FOURS_POINTS;
                                    if (DEBUG)
                                        System.out.println("[down](3)BOARD[" + row + "][" + col + "]: ADDED UTILITY VALUE OF: " + FOURS_POINTS);
                                }
                            }
                        }

                    }//FINISH CHECKING BELOW

                    if(evaluation > maxUtility[row][col]){
                        maxUtility[row][col] = evaluation;
                    }
                    evaluation = 0;

                    encounteredEnemy = -1;
                    //====================CHECK ABOVE====================
                    if (row >= 4) {//to be sure there can actually be a 5-in-a-row to this direction

                        count = 1; //Sum of how many of our players we encounter in the next 4 spaces
                        for (int x = row - 1; x > row - 5; x--) {
                            if (board[x][col].isAiPiece() == AI) {

                                count++;
                            } else if (board[x][col].isAiPiece() == enemy) {
                                encounteredEnemy = x;
                                break;
                            }
                        }

                        if (count < 3 || count == 5) {
                            evaluation += getPointsToAdd(count);
                            if (DEBUG)
                                System.out.println("[up]BOARD[" + row + "][" + col + "]: ADDED UTILITY VALUE OF: " + getPointsToAdd(count));
                        } else if (count == 3) {
                            if (encounteredEnemy == -1) {
                                evaluation += THREES_POINTS;
                                if (DEBUG)
                                    System.out.println("[up](1)BOARD[" + row + "][" + col + "]: ADDED UTILITY VALUE OF: " + THREES_POINTS);
                            } else if (lastEnemyEncounteredRow > -1) {//we encountered an enemy before seeing our player
                                if (row + 1 < boardLength && encounteredEnemy == row - 4) {//we have enough room to make a 4, check upwards to see if we can make a 5 (-O-X-XXO--)
                                    if (board[row + 1][col].isAiPiece() != enemy) {
                                        evaluation += THREES_POINTS;
                                        if (DEBUG)
                                            System.out.println("[up](2)BOARD[" + row + "][" + col + "]: ADDED UTILITY VALUE OF: " + THREES_POINTS);
                                    }
                                } else if (row + 2 < boardLength && encounteredEnemy == row - 3) {//we are stuck on 3, check upwards 2 to see if we can make it a 5
                                    evaluation += THREES_POINTS;
                                    if (DEBUG)
                                        System.out.println("[up](3)BOARD[" + row + "][" + col + "]: ADDED UTILITY VALUE OF: " + THREES_POINTS);
                                }
                            }
                        } else if (count == 4 && row + 1 >= boardLength && encounteredEnemy == -1) {//havent encountered an enemy before seeing our player
                            if (DEBUG)
                                System.out.println("[up](0)BOARD[" + row + "][" + col + "]: ADDED UTILITY VALUE OF: " + FOURS_POINTS);
                            evaluation += FOURS_POINTS;
                        } else if (encounteredEnemy > -1 && (row - 5 < 0 || row + 1 >= boardLength)) {
                            //enemy is blocking us at the edge of the board (OXXXX)
                            if (DEBUG) System.out.println("[up]BLOCKING ON EDGE!!!!!!");
                        } else { //check for the straight four
                            char[] newChars = new char[6];

                            for (int b = row + 1, i = 0; b >= 0 && b > row - 5; b--, i++) {
                                newChars[i] = board[b][col].pieceToChar();
                            }
                            String rowString = new String(newChars);

                            if (isStraightFour(rowString, 'X')) {//If it is a straight 4
                                if (DEBUG)
                                    System.out.println("[up](1)BOARD[" + row + "][" + col + "]: ADDED UTILITY VALUE OF: " + STRAIGHT_FOUR_POINTS);
                                evaluation += STRAIGHT_FOUR_POINTS;
                            } else if (encounteredEnemy == -1) {//If it is possible to have a straight 4, and we have not encountered an enemy while searching
                                evaluation += FOURS_POINTS;
                                if (DEBUG)
                                    System.out.println("[up](2)BOARD[" + row + "][" + col + "]: ADDED UTILITY VALUE OF: " + FOURS_POINTS);
                            } else { //If it is possible to have a straight 4, but we have encountered an enemy while searching, check if there is room on left
                                if (board[row + 1][col].isAiPiece() != enemy) {
                                    evaluation += FOURS_POINTS;
                                    if (DEBUG)
                                        System.out.println("[up](3)BOARD[" + row + "][" + col + "]: ADDED UTILITY VALUE OF: " + FOURS_POINTS);
                                }
                            }
                        }

                    }//FINISH CHECKING ABOVE

                    if(evaluation > maxUtility[row][col]){
                        maxUtility[row][col] = evaluation;
                    }
                    evaluation = 0;

                    encounteredEnemy = -1;
                    encounteredEnemyY = -1;
                    //====================CHECK BOTTOM-RIGHT DIAGONALLY====================
                    if (col + 4 < boardLength && row + 4 < boardLength) {//to be sure there can actually be a 5-in-a-row to this direction

                        count = 1; //Sum of how many of our players we encounter in the next 4 spaces
                        for (int x = row + 1, y = col + 1; x < row + 5 && y < col + 5; x++, y++) {
                            if (board[x][y].isAiPiece() == AI) {
                                count++;
                            } else if (board[x][y].isAiPiece() == enemy) {
                                encounteredEnemy = x;
                                encounteredEnemyY = y;
                                break;
                            }
                        }

                        if (count < 3 || count == 5) {
                            evaluation += getPointsToAdd(count);
                            if (DEBUG)
                                System.out.println("[BR-D]BOARD[" + row + "][" + col + "]: ADDED UTILITY VALUE OF: " + getPointsToAdd(count));
                        } else if (count == 3) {
                            if (encounteredEnemy == -1) {
                                evaluation += THREES_POINTS;
                                if (DEBUG)
                                    System.out.println("[BR-D](1)BOARD[" + row + "][" + col + "]: ADDED UTILITY VALUE OF: " + THREES_POINTS);
                            } else if (lastEnemyEncounteredRow > -1) {//we encountered an enemy before seeing our player
                                if ((row + 1 < boardLength && col - 1 >= 0) && (encounteredEnemy == row + 4 && encounteredEnemyY == col + 4)) {//we have enough room to make a 4, check upwards to see if we can make a 5 (-O-X-XXO--)
                                    if (board[row + 1][col - 1].isAiPiece() != enemy) {
                                        evaluation += THREES_POINTS;
                                        if (DEBUG)
                                            System.out.println("[BR-D](2)BOARD[" + row + "][" + col + "]: ADDED UTILITY VALUE OF: " + THREES_POINTS);
                                    }
                                } else if (row - 2 >= 0 && col - 2 >= 0 && (encounteredEnemy == row + 3 && encounteredEnemyY == col + 3)) {//we are stuck on 3, check upwards 2 to see if we can make it a 5
                                    evaluation += THREES_POINTS;
                                    if (DEBUG)
                                        System.out.println("[BR-D](3)BOARD[" + row + "][" + col + "]: ADDED UTILITY VALUE OF: " + THREES_POINTS);
                                }
                            }
                        } else if (count == 4 && (col - 1 < 0 && row - 1 < 0) && encounteredEnemy == -1) {//havent encountered an enemy before seeing our player
                            if (DEBUG)
                                System.out.println("[BR-D](0)BOARD[" + row + "][" + col + "]: ADDED UTILITY VALUE OF: " + FOURS_POINTS);
                            evaluation += FOURS_POINTS;
                        } else if (encounteredEnemy > -1 && (row + 5 >= boardLength || row - 1 < 0) && (col + 5 >= boardLength || col - 1 < 0)) {
                            //enemy is blocking us at the edge of the board (OXXXX)
                            if (DEBUG) System.out.println("[BR-D]BLOCKING ON EDGE!!!!!!");
                        } else { //check for the straight four
                            char[] newChars = new char[6];

                            for (int b = row - 1, c = col - 1, i = 0; b < boardLength && b < row + 5 && b >= 0 && c >= 0; b++, c++, i++) {
                                if (DEBUG) System.out.println(b + " " + c);
                                newChars[i] = board[b][c].pieceToChar();
                            }
                            String rowString = new String(newChars);

                            if (isStraightFour(rowString, 'X')) {//If it is a straight 4
                                if (DEBUG)
                                    System.out.println("[BR-D](1)BOARD[" + row + "][" + col + "]: ADDED UTILITY VALUE OF: " + STRAIGHT_FOUR_POINTS);
                                evaluation += STRAIGHT_FOUR_POINTS;
                            } else if (encounteredEnemy == -1) {//If it is possible to have a straight 4, and we have not encountered an enemy while searching
                                evaluation += FOURS_POINTS;
                                if (DEBUG)
                                    System.out.println("[BR-D](2)BOARD[" + row + "][" + col + "]: ADDED UTILITY VALUE OF: " + FOURS_POINTS);
                            } else { //If it is possible to have a straight 4, but we have encountered an enemy while searching, check if there is room on left
                                if (row-1 >= 0 && col-1 >= 0 && board[row - 1][col - 1].isAiPiece() != enemy) {
                                    evaluation += FOURS_POINTS;
                                    if (DEBUG)
                                        System.out.println("[BR-D](3)BOARD[" + row + "][" + col + "]: ADDED UTILITY VALUE OF: " + FOURS_POINTS);
                                }
                            }
                        }

                    }//FINISH BOTTOM RIGHT DIAGONAL

                    if(evaluation > maxUtility[row][col]){
                        maxUtility[row][col] = evaluation;
                    }
                    evaluation = 0;

                    encounteredEnemy = -1;
                    encounteredEnemyY = -1;
                    //====================CHECK TOP-RIGHT DIAGONALLY====================
                    if (col + 4 < boardLength && row - 4 >= 0) {//to be sure there can actually be a 5-in-a-row to this direction

                        count = 1; //Sum of how many of our players we encounter in the next 4 spaces
                        for (int x = row - 1, y = col + 1; x > row - 5 && y < col + 5; x--, y++) {
                            if (board[x][y] == player) {
                                count++;
                            } else if (board[x][y] == enemy) {
                                encounteredEnemy = x;
                                encounteredEnemyY = y;
                                break;
                            }
                        }

                        if (count < 3 || count == 5) {
                            evaluation += getPointsToAdd(count);
                            if (DEBUG)
                                System.out.println("[TR-D]BOARD[" + row + "][" + col + "]: ADDED UTILITY VALUE OF: " + getPointsToAdd(count));
                        } else if (count == 3) {
                            if (encounteredEnemy == -1) {
                                evaluation += THREES_POINTS;
                                if (DEBUG)
                                    System.out.println("[TR-D](1)BOARD[" + row + "][" + col + "]: ADDED UTILITY VALUE OF: " + THREES_POINTS);
                            } else if (lastEnemyEncounteredRow > -1) {//we encountered an enemy before seeing our player
                                if ((row - 1 >= 0 && col - 1 >= 0) && (encounteredEnemy == row - 4 && encounteredEnemyY == col + 4)) {//we have enough room to make a 4, check upwards to see if we can make a 5 (-O-X-XXO--)
                                    if (board[row - 1][col - 1] != enemy) {
                                        evaluation += THREES_POINTS;
                                        if (DEBUG)
                                            System.out.println("[TR-D](2)BOARD[" + row + "][" + col + "]: ADDED UTILITY VALUE OF: " + THREES_POINTS);
                                    }
                                } else if (row + 2 < boardLength && col - 2 >= 0 && (encounteredEnemy == row - 3 && encounteredEnemyY == col + 3)) {//we are stuck on 3, check upwards 2 to see if we can make it a 5
                                    evaluation += THREES_POINTS;
                                    if (DEBUG)
                                        System.out.println("[TR-D](3)BOARD[" + row + "][" + col + "]: ADDED UTILITY VALUE OF: " + THREES_POINTS);
                                }
                            }
                        } else if (count == 4 && (col - 1 < 0 && row + 1 >= boardLength) && encounteredEnemy == -1) {//havent encountered an enemy before seeing our player
                            if (DEBUG)
                                System.out.println("[TR-D](0)BOARD[" + row + "][" + col + "]: ADDED UTILITY VALUE OF: " + FOURS_POINTS);
                            evaluation += FOURS_POINTS;
                        } else if (encounteredEnemy > -1 && (row - 5 < 0 || row + 1 >= boardLength) && (col + 5 >= boardLength || col - 1 < 0)) {
                            //enemy is blocking us at the edge of the board (OXXXX)
                            if(DEBUG)System.out.println("[TR-D]BLOCKING ON EDGE!!!!!!");
                        } else { //check for the straight four
                            char[] newChars = new char[6];

                            for (int b = row + 1, c = col - 1, i = 0; b < boardLength && b > row - 5 && c >= 0; b--, c++, i++) {
                                newChars[i] = board[b][c];
                            }
                            String rowString = new String(newChars);

                            if (isStraightFour(rowString, player)) {//If it is a straight 4
                                if (DEBUG)
                                    System.out.println("[TR-D](1)BOARD[" + row + "][" + col + "]: ADDED UTILITY VALUE OF: " + STRAIGHT_FOUR_POINTS);
                                evaluation += STRAIGHT_FOUR_POINTS;
                            } else if (encounteredEnemy == -1) {//If it is possible to have a straight 4, and we have not encountered an enemy while searching
                                evaluation += FOURS_POINTS;
                                if (DEBUG)
                                    System.out.println("[TR-D](2)BOARD[" + row + "][" + col + "]: ADDED UTILITY VALUE OF: " + FOURS_POINTS);
                            } else { //If it is possible to have a straight 4, but we have encountered an enemy while searching, check if there is room on left

                                if (board[row + 1][col - 1] != enemy) {
                                    evaluation += FOURS_POINTS;
                                    if (DEBUG)
                                        System.out.println("[TR-D](3)BOARD[" + row + "][" + col + "]: ADDED UTILITY VALUE OF: " + FOURS_POINTS);
                                }

                            }
                        }
                    }//FINISH TOP-RIGHT DIAGONAL SEARCH

                    if(evaluation > maxUtility[row][col]){
                        maxUtility[row][col] = evaluation;
                    }

                }
            }//inner (column) loop
        }//outer (row) loop

        for(int i=0; i<board.length; i++){
            for(int j =0; j<board.length; j++){
                evaluation += maxUtility[i][j];
            }
        }

        return evaluation;
    }

}
