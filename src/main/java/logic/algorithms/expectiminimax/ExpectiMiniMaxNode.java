package logic.algorithms.expectiminimax;

import logic.Move;
import logic.PieceAndSquareTuple;
import logic.State;
import logic.enums.Piece;

import java.util.ArrayList;
import java.util.List;

public class ExpectiMiniMaxNode {

    private List<Integer> boardEvaluationNumbersForGivenPiece;
    private List<List<PieceAndSquareTuple>> possibleBoardStatesForGivenPiece; //i.e. this list would be size 16 for pawn first turn
    private List<Move> possibleMovesGivenStateForGivenPiece;
    private List<ExpectiMiniMaxNode> children;
    private int chanceDivider; // i.e. number of pieces that can move at a given state List<PieceAndSquareTuple>
    private boolean isMaxPlayer;
    private State state; // to get origin square of piece, which you can get from dice roll in state, best state for non-root nodes
    private int nodeValue;
    private Piece piece;

    // root
    public ExpectiMiniMaxNode(boolean isMaxPlayer, State state) {
        this.isMaxPlayer = isMaxPlayer;
        this.state = state;
        this.children = new ArrayList<>();
        this.chanceDivider = 0;
    }

    // children
    public ExpectiMiniMaxNode(boolean isMaxPlayer, List<List<PieceAndSquareTuple>> possibleBoardStatesForGivenPiece, List<Integer> boardEvaluationNumbersForGivenPiece,
                              int chanceDivider, int nodeValue, State state, List<Move> possibleMovesGivenStateForGivenPiece, Piece piece) {
        this.isMaxPlayer = isMaxPlayer;
        this.possibleBoardStatesForGivenPiece = possibleBoardStatesForGivenPiece;
        this.boardEvaluationNumbersForGivenPiece = boardEvaluationNumbersForGivenPiece;
        this.chanceDivider = chanceDivider;
        this.state = state;
        this.nodeValue = nodeValue;
        this.children = new ArrayList<>();
        this.possibleMovesGivenStateForGivenPiece = possibleMovesGivenStateForGivenPiece;
        this.piece = piece;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setMaxPlayer(boolean maxPlayer) {
        isMaxPlayer = maxPlayer;
    }

    public Piece getPiece() {
        return piece;
    }

    public void addChild(ExpectiMiniMaxNode node) {
        children.add(node);
    }

    public boolean isMaxPlayer() {
        return isMaxPlayer;
    }

    public List<List<PieceAndSquareTuple>> getPossibleBoardStatesForGivenPiece() {
        return possibleBoardStatesForGivenPiece;
    }

    public List<ExpectiMiniMaxNode> getChildren() {
        return children;
    }

    public List<Integer> getBoardEvaluationNumbersForGivenPiece() {
        return boardEvaluationNumbersForGivenPiece;
    }

    public State getState() {
        return state;
    }

    public int getNodeValue() {
        return nodeValue;
    }

}

