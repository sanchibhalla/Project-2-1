package logic.game;

import logic.enums.Validity;
import logic.Move;
import logic.State;

public class HumanGame extends Game {

    // called for GUI to moves Tile
    public Move makeHumanMove(Move move) {
        if (evaluator.isLegalMove(move, currentState, true)) { //move legal

            State newState = currentState.applyMove(move);

            previousStates.push(currentState);
            currentState = newState;
            move.setStatus(Validity.VALID);

            processCastling();

        } else {
            move.setInvalid();
        }

        //send back to GUI with updated validity flag
        return move;
    }

}
