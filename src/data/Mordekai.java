/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingWorker;
import view.Game;
import view.Piece;

/**
 *
 * @author david
 */
public class Mordekai extends Player {

    private Game game;
    private Piece[][] board;
    
    private List<Pair> moves;
    private Pair nextMove;
    private SwingWorker<Pair, Void> worker;
    
    private int evaluationCount;
    private int depth;
    
    public final static int WIN_SCORE = 100000000;
    
    public Mordekai(Game game, int depth) {
        super("Mordekai", 1);
        
        this.game = game;
        this.depth = depth;
        evaluationCount = 0;
    }
    
    public SwingWorker<Pair, Void> getWorker() {
        return worker;
    }
    
    public List<Pair> getMoves() {
        return moves;
    }
        
    public void setBoard(Piece[][] newBoard) {
        board = newBoard;
    }
    
    public Piece[][] copyBoard(Piece[][] target) {
        int length = target.length;
        Piece[][] copy = new Piece[target.length][target[0].length];
        try {
            for(int i = 0; i< length; i++) {
                for(int j = 0; j < length; j++) {
                    copy[i][j] = (Piece) target[i][j].clone();
                }
            }
            return copy;
        } catch(CloneNotSupportedException ex) {
            return null;
        }
    }
    
    public List<Pair> generateMoves(Piece[][] board) {
        moves = new ArrayList<>();
        // Look for cells that has at least one stone in an adjacent cell.
        for(int i = 0; i < Game.BOARD_SIZE; i++) {
            for(int j = 0; j < Game.BOARD_SIZE; j++) {
                if(!board[i][j].isEmpty()) {
                    continue;
                }
                if(i > 0) {
                    if(j > 0) {
                        if(!board[i-1][j-1].isEmpty() || !board[i][j-1].isEmpty()) {
                            moves.add(new Pair(i, j));
                            continue;
                        }
                    }
                    if(j < Game.BOARD_SIZE-1) {
                        if(!board[i-1][j+1].isEmpty() || !board[i][j+1].isEmpty()) {
                            moves.add(new Pair(i, j));
                            continue;
                        }
                    }
                    if(!board[i-1][j].isEmpty()) {
                        moves.add(new Pair(i, j));
                        continue;
                    }
                }
                if(i < Game.BOARD_SIZE-1) {
                    if(j > 0) {
                        if(!board[i+1][j-1].isEmpty() || !board[i][j-1].isEmpty()) {
                            moves.add(new Pair(i, j));
                            continue;
                        }
                    }
                    if(j < Game.BOARD_SIZE-1) {
                        if(!board[i+1][j+1].isEmpty() || !board[i][j+1].isEmpty()) {
                            moves.add(new Pair(i, j));
                            continue;
                        }
                    }
                    if(!board[i+1][j].isEmpty()) {
                        moves.add(new Pair(i, j));
                    }
                }
            }
        }
        return moves;
    }
    
    public void computeNextMove() {
        worker = new SwingWorker<Pair, Void>() {
            @Override
            public Pair doInBackground() {
                return calculateNextMove();
            }
            
            @Override
            public void done() {
                try {
                    nextMove = get();
                    if(nextMove == null) {
                        game.finishGame(true);
                    } else {
                        game.setPieceOwnerAtPosition(nextMove.getI(), nextMove.getJ());
                    }
                } catch (InterruptedException | ExecutionException ex) {
                    Logger.getLogger(Mordekai.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
    }
    
    private Pair calculateNextMove() {
        board = game.getBoard();
        game.setAiComputing(true);
        int x, y;
        long startTime = System.currentTimeMillis();
        // Check if any available move can finish the game
        Object[] bestMove = searchWinningMove(); 
        if(bestMove != null ) {
            game.sendMessage("Finisher!");
            x = (Integer)(bestMove[2]);
            y = (Integer)(bestMove[1]);

        } else {
            // If there is no such move, search the minimax tree with suggested depth.
            bestMove = minimaxSearchAB(depth, board, true, -1.0, WIN_SCORE);
            if(bestMove[1] == null) {
                    game.setAiComputing(false);
                    evaluationCount=0;
                    return null;
            } else {
                    x = (Integer)(bestMove[2]);
                    y = (Integer)(bestMove[1]);
            }
        }
        game.sendMessage("Cases calculated: " + evaluationCount + " Calculation time: " + (System.currentTimeMillis() - startTime) + " ms");
        game.setAiComputing(false);
        evaluationCount = 0;
        
        return new Pair(x, y);
    }
    
    private Object[] searchWinningMove() {
        List<Pair> allPossibleMoves = generateMoves(board);
        Object[] winningMove = new Object[3];

        // Iterate for all possible moves
        for(Pair move : allPossibleMoves) {
            evaluationCount++;
            // Create a temporary board that is equivalent to the current board
            Piece[][] dummyBoard = copyBoard(board);
            // Play the move to that temporary board without drawing anything
            dummyBoard[move.getI()][move.getJ()].setOwner(this);
            // If the white player has a winning score in that temporary board, return the move.
            if(getScore(dummyBoard, false, false) >= WIN_SCORE) {
                winningMove[1] = move.getI();
                winningMove[2] = move.getJ();
                return winningMove;
            }
        }
        return null;
    }
    
    /*
     * alpha : Best AI Move (Max)
     * beta : Best Player Move (Min)
     * returns: {score, move[0], move[1]}
     * */
    private Object[] minimaxSearchAB(int depth, Piece[][] board, boolean max, double alpha, double beta) {
        if(depth == 0) {
            Object[] x = {
                evaluateBoard(board, !max), null, null
            };
            return x;
        }
        List<Pair> allPossibleMoves = generateMoves(board);
        if(allPossibleMoves.isEmpty()) {
            Object[] x = {
                evaluateBoard(board, !max), null, null
            };
            return x;
        }
        Object[] bestMove = new Object[3];
        if(max) {
            bestMove[0] = -1.0;
            // Iterate for all possible moves that can be made.
            for(Pair move : allPossibleMoves) {
                // Create a temporary board that is equivalent to the current board
                Piece[][] dummyBoard = copyBoard(board);
                // Play the move to that temporary board without drawing anything
                dummyBoard[move.getI()][move.getJ()].setOwner(this);

                // Call the minimax function for the next depth, to look for a minimum score.
                Object[] tempMove = minimaxSearchAB(depth-1, dummyBoard, !max, alpha, beta);

                // Updating alpha
                if((Double)(tempMove[0]) > alpha) {
                    alpha = (Double)(tempMove[0]);
                }
                // Pruning with beta
                if((Double)(tempMove[0]) >= beta) {
                    return tempMove;
                }
                if((Double)tempMove[0] > (Double)bestMove[0]) {
                    bestMove = tempMove;
                    bestMove[1] = move.getI();
                    bestMove[2] = move.getJ();
                }
            }
        } else {
            bestMove[0] = 100000000.0;
            bestMove[1] = allPossibleMoves.get(0).getI();
            bestMove[2] = allPossibleMoves.get(0).getJ();
            for(Pair move : allPossibleMoves) {
                Piece[][] dummyBoard = copyBoard(board);
                dummyBoard[move.getI()][move.getJ()].setOwner(game.getBlack());
                Object[] tempMove = minimaxSearchAB(depth-1, dummyBoard, !max, alpha, beta);
                // Updating beta
                if(((Double)tempMove[0]) < beta) {
                    beta = (Double)(tempMove[0]);
                }
                // Pruning with alpha
                if((Double)(tempMove[0]) <= alpha) {
                    return tempMove;
                }
                if((Double)tempMove[0] < (Double)bestMove[0]) {
                    bestMove = tempMove;
                    bestMove[1] = move.getI();
                    bestMove[2] = move.getJ();
                }
            }
        }
        return bestMove;
    }
    
    private double evaluateBoard(Piece[][] boardToEvaluate, boolean myTurn) {
        double opponentScore = getScore(boardToEvaluate, true, myTurn), myScore = getScore(boardToEvaluate, false, myTurn);
        evaluationCount++;
        if(opponentScore == 0) {
            opponentScore = 1.0;
        }
        return myScore/opponentScore;
    }
    
    private int getScore(Piece[][] testBoard, boolean opponent, boolean myTurn) {
        int h = evaluateHorizontal(testBoard, opponent, myTurn), v = evaluateVertical(testBoard, opponent, myTurn), d = evaluateDiagonal(testBoard, opponent, myTurn);
        return h + v + d;
    }
    
    private int evaluateHorizontal(Piece[][] testBoard, boolean opponent, boolean myTurn) {
        int consecutive = 0, blocks = 2, score = 0;
        
        for(int i = 0; i < testBoard.length; i++) {
            for(int j=0; j< testBoard[0].length; j++) {
                if(testBoard[i][j].getOwner() == (opponent ? game.getBlack() : this)) {
                    consecutive++;
                } else if(testBoard[i][j].isEmpty()) {
                    if(consecutive > 0) {
                        blocks--;
                        score += getConsecutiveSetScore(consecutive, blocks, opponent == myTurn);
                        consecutive = 0;
                        blocks = 1;
                    } else {
                        blocks = 1;
                    }
                } else if(consecutive > 0) {
                    score += getConsecutiveSetScore(consecutive, blocks, opponent == myTurn);
                    consecutive = 0;
                    blocks = 2;
                } else {
                    blocks = 2;
                }
            }
            if(consecutive > 0) {
                score += getConsecutiveSetScore(consecutive, blocks, opponent == myTurn);
            }
            consecutive = 0;
            blocks = 2;
        }
        return score;
    }
    
    private int evaluateVertical(Piece[][] testBoard, boolean opponent, boolean myTurn) {
        int consecutive = 0, blocks = 2, score = 0;
        
        for(int j =0; j < testBoard[0].length; j++) {
            for(int i = 0; i < testBoard.length; i++) {
                if(testBoard[i][j].getOwner() == (opponent ? game.getBlack() : this)) {
                    consecutive++;
                } else if(testBoard[i][j].isEmpty()) {
                    if(consecutive > 0) {
                        blocks--;
                        score += getConsecutiveSetScore(consecutive, blocks, opponent == myTurn);
                        consecutive = 0;
                        blocks = 1;
                    } else {
                        blocks = 1;
                    }
                } else if(consecutive > 0) {
                    score += getConsecutiveSetScore(consecutive, blocks, opponent == myTurn);
                    consecutive = 0;
                    blocks = 2;
                } else {
                    blocks = 2;
                }
            }
            if(consecutive > 0) {
                score += getConsecutiveSetScore(consecutive, blocks, opponent == myTurn);
            }
            consecutive = 0;
            blocks = 2;
        }
        return score;
    }
    
    private int evaluateDiagonal(Piece[][] testBoard, boolean opponent, boolean myTurn) {
        int consecutive = 0, blocks = 2, score = 0;
        int j, iStart, iEnd;
        // From bottom-left to top-right diagonally
        for (int k = 0; k <= 2 * (testBoard.length - 1); k++) {
            iStart = Math.max(0, k - testBoard.length + 1);
            iEnd = Math.min(testBoard.length - 1, k);
            for (int i = iStart; i <= iEnd; ++i) {
                j = k - i;
                if(testBoard[i][j].getOwner() == (opponent ? game.getBlack() : this)) {
                    consecutive++;
                } else if(testBoard[i][j].isEmpty()) {
                    if(consecutive > 0) {
                        blocks--;
                        score += getConsecutiveSetScore(consecutive, blocks, opponent == myTurn);
                        consecutive = 0;
                        blocks = 1;
                    } else {
                        blocks = 1;
                    }
                } else if(consecutive > 0) {
                    score += getConsecutiveSetScore(consecutive, blocks, opponent == myTurn);
                    consecutive = 0;
                    blocks = 2;
                } else {
                    blocks = 2;
                }
            }
            if(consecutive > 0) {
                score += getConsecutiveSetScore(consecutive, blocks, opponent == myTurn);
            }
            consecutive = 0;
            blocks = 2;
        }
        // From top-left to bottom-right diagonally
        for (int k = 1-testBoard.length; k < testBoard.length; k++) {
            iStart = Math.max(0, k);
            iEnd = Math.min(testBoard.length + k - 1, testBoard.length-1);
            for (int i = iStart; i <= iEnd; ++i) {
                j = i - k;
                if(testBoard[i][j].getOwner() == (opponent ? game.getBlack() : this)) {
                    consecutive++;
                } else if(testBoard[i][j].isEmpty()) {
                    if(consecutive > 0) {
                        blocks--;
                        score += getConsecutiveSetScore(consecutive, blocks, opponent == myTurn);
                        consecutive = 0;
                        blocks = 1;
                    } else {
                        blocks = 1;
                    }
                } else if(consecutive > 0) {
                    score += getConsecutiveSetScore(consecutive, blocks, opponent == myTurn);
                    consecutive = 0;
                    blocks = 2;
                } else {
                    blocks = 2;
                }
            }
            if(consecutive > 0) {
            score += getConsecutiveSetScore(consecutive, blocks, opponent == myTurn);
            }
            consecutive = 0;
            blocks = 2;
        }
        
        return score;
    }
    
    private int getConsecutiveSetScore(int count, int blocks, boolean currentTurn) {
        final int winGuarantee = 1000000;
        if(blocks == 2 && count < 5) {
            return 0;
        }
        switch(count) {
            case 5: 
                return WIN_SCORE;
            case 4:
                if(currentTurn) {
                    return winGuarantee;
                } else {
                    return (blocks == 0)? winGuarantee/4 : 200; 
                }
            case 3:
                if(blocks == 0) {
                    return (currentTurn)? 50000 : 200;
                } else {
                    return (currentTurn)? 10 : 5;
                }
            case 2:
                if(blocks == 0) {
                    return (currentTurn)? 7 : 5;
                } else {
                    return 3;
                }
            case 1:
                return 1;
            default:
                return WIN_SCORE*2;
        }
    }
}
