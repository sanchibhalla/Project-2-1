package logic.player;

import logic.ML.DQL;
import logic.Move;
import logic.State;
import logic.enums.Side;

public class QLPlayer extends AIPlayer{
    private final int depth;
    DQL ql = new DQL();

    public QLPlayer(int depth, Side color) {
        super(color);
        this.depth = depth;
    }

    @Override
    public Move chooseMove(State state) { // TODO, implement this
       return null;
    }
}
