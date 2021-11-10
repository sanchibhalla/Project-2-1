package logic.game;

import logic.Move;
import logic.State;
import logic.enums.Validity;

public class HumanGame extends Game {

    int num=1;
    // called for GUI to moves Tile
    @Override
    public Move makeHumanMove(Move move) {
        if (evaluator.isLegalMove(move, currentState, true)) { //move legal
            System.out.println("a");
            System.out.println(move.getSide());
            System.out.println(num);
            num++;

            State newState = currentState.applyMove(move);

            previousStates.push(currentState);
            currentState = newState;
            move.setStatus(Validity.VALID);

        } else {
            move.setInvalid();
        }

        //send back to GUI with updated validity flag
        return move;
    }

}
