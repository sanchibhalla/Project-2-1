package logic.game;

import gui.controllers.MainContainerController;
import logic.Move;
import logic.PieceAndSquareTuple;
import logic.State;
import logic.enums.Validity;

import java.util.List;

public class HumanGame extends Game {

    public HumanGame(String FEN) {
        super(FEN);
    }

    // called for GUI to moves Tile
    public Move makeHumanMove(Move move) {
        if (evaluator.isLegalMove(move, currentState, true,false)) { //move legal

            State newState = currentState.applyMove(move);
            previousStates.push(currentState);

            // sets winner and sets gameover to true if game over
            checkGameOver(move);
            // update pieceAndSquare in state
//            updatePieceAndSquareState(newState,move);
            // newState.printPieceAndSquare();

            currentState = newState;
            move.setStatus(Validity.VALID);

            // could also update pieceAndSquare here idk if it would make a difference

            processCastling();
            MainContainerController.getInstance().updateTurn(currentState.getColor());
        } else {
            move.setInvalid();
        }

        //send back to GUI with updated validity flag
        return move;
    }

    private void printArray(List<PieceAndSquareTuple> pieceAndSquare){
        // System.out.println("printArray: ");
        for (PieceAndSquareTuple t : pieceAndSquare) {
            // System.out.print(t.toString() + " | ");
        }
        // System.out.println("Size: " + pieceAndSquare.size());
    }


}
