package logic.ML;

import logic.Move;
import logic.State;
import logic.algorithms.BoardStateEvaluator;
import logic.enums.Piece;
import logic.enums.Side;

import java.util.ArrayList;
import java.util.Arrays;

public class DQL {

    Qtable currentQtable;
    int[][] Qvalues;
    State givenInitialState;

    public void algo(State initialState, Side side, int depth) {
        this.givenInitialState = new State(initialState);
        currentQtable = new Qtable(givenInitialState, side, depth);

        int stateSize = currentQtable.stateSpace.size();
        int actionSize = currentQtable.getActionSize(); // 8x8x(8x7+8+9), this is the total possible actions a state can have at most

        Qvalues = new int[stateSize][actionSize]; // save the q-values in a separate table

        for (int[] row : Qvalues) {// fill the table with 0
            Arrays.fill(row, 0);
        }
        double numOfGames = 150;

        double explorationProb = 1; // this defines the prob. that the agent will explore
        double explorationDecay = 1 / numOfGames;
        double minExplorationProb = 0.01; // prob. of exploration can go down until 0.01

        double gamma = 0.99; // discount factor, helps the algo to strive for a long-term high reward (0<gamma<1)
        double learningRate = 0.1;

        State currentState;
        Move action;

        for (int i = 0; i < numOfGames; i++) {
            currentState = givenInitialState;
            int reward;
            boolean finished; // turn to true if king captured

            for (int j = 0; j < depth / 2; j++) {
                // take learned path or explore new actions
                if (Math.random() <= explorationProb) { // at first picking action will be totally random
                    action = currentQtable.randomMoveGenerator(currentState, side);
                } else {

                    currentState = new State(currentState.getBoard(), currentState.diceRoll, side);
                    int index = argmax(Qvalues, currentQtable.accessStateIndex(currentState.getPieceAndSquare()));
                    ArrayList<OriginAndDestSquare> originAndDestSquares = currentQtable.accessStateValue(currentState.getPieceAndSquare());
                    OriginAndDestSquare tempMove = originAndDestSquares.get(index);
                    Piece p = currentState.getBoard().getPieceAt(tempMove.getOrigin());

                    action = new Move(p, tempMove.getOrigin(), tempMove.getDest(), Piece.getDiceFromPiece(p), side);
                }
                //apply chosen action and return the next state, reward and true if the episode is ended
                State newState = new State(currentState.applyMove(action));
                /* what happens here is that we can't get immediate reward, in order to get the actual reward we need to apply a
                move of the opponent so that the reward we get will be useful and valid.
                */
                newState = newState.applyMove(currentQtable.randomMoveGenerator(new State(newState), Side.getOpposite(side)));
                // code line above can be changed with some better algo (example given below) which could give better results but would take more time
                //BasicAIPlayer help = new BasicAIPlayer(Side.getOpposite(side));
                //newState = newState.applyMove(help.chooseMove(newState));

                reward = getReward(newState, side);
                finished = didStateEnd(newState);
                // update value of Qtable
                currentState = new State(currentState.getBoard(), currentState.diceRoll, side);
                int indexOfState = currentQtable.accessStateIndex(currentState.getPieceAndSquare());
                OriginAndDestSquare tempOriginAndDestSquare = new OriginAndDestSquare(action.getOrigin(), action.getDestination());
                int indexOfAction = currentQtable.accessActionIndex(currentState.getPieceAndSquare(), tempOriginAndDestSquare);

                newState = new State(newState.getBoard(), newState.diceRoll, side);
                Qvalues[indexOfState][indexOfAction] = (int) ((1 - learningRate) * Qvalues[indexOfState][indexOfAction] + learningRate * (reward + gamma * maxValue(Qvalues, currentQtable.accessStateIndex(newState.getPieceAndSquare()))));
                currentState = newState;

                if (finished) break;
            }

            if (explorationProb >= minExplorationProb) explorationProb -= explorationDecay;
        }
    }

    public boolean didStateEnd(State state) {
        return !(currentQtable.checkIfKingsExist(state));
    }

    public int getReward(State state, Side side) {
        int reward = 0;
        reward += BoardStateEvaluator.getBoardEvaluationNumber(state, currentQtable.currentSide) / 10;
        reward += currentQtable.addPieceWeights(state, side) * 50;
        return reward;
    }

    public Move getBestMove(State state, Side color) {
        Piece tempP = Piece.getPieceFromDice(givenInitialState.getDiceRoll(), givenInitialState.getColor()); // get piece that the action needs to be equal

        givenInitialState = new State(givenInitialState.getBoard(), givenInitialState.diceRoll, color); // TODO
        int a = currentQtable.accessStateIndex(givenInitialState.getPieceAndSquare());

        state = new State(state.getBoard(), state.diceRoll, color); // TODO
        ArrayList<OriginAndDestSquare> originAndDestSquares = currentQtable.accessStateValue(state.getPieceAndSquare());

        int index = currentQtable.getIndexOfBestMove(Qvalues, originAndDestSquares, tempP, givenInitialState, a);
        OriginAndDestSquare tempMove = originAndDestSquares.get(index);
        return (new Move(tempP, tempMove.getOrigin(), tempMove.getDest(), Piece.getDiceFromPiece(tempP), color));
    }

    public int argmax(int[][] qvalues, int stateIndex) {
        int count = 0;
        int indexOfMaxAction = -1; // if returns this something is wrong
        for (int i = 0; i < qvalues[stateIndex].length; i++) {
            if (qvalues[stateIndex][i] > count) {
                count = qvalues[stateIndex][i];
                indexOfMaxAction = i;
            }
        }
        if (count == 0) return (int) (Math.random() * currentQtable.accessStateValue(currentQtable.stateSpace.get(stateIndex)).size());
        return indexOfMaxAction;
    }

    public int maxValue(int[][] qvalues, int stateIndex) {
        int count = 0;

        for (int i = 0; i < qvalues[stateIndex].length; i++) {
            if (qvalues[stateIndex][i] > count) {
                count = qvalues[stateIndex][i];
            }
        }
        return count;
    }

    public int getAvgValuesOfTable() {
        givenInitialState = new State(givenInitialState.getBoard(), givenInitialState.diceRoll, givenInitialState.getColor());
        int a = currentQtable.accessStateIndex(givenInitialState.getPieceAndSquare());
        int sum = 0;
        int numOfnonZero = 0;

        for (int i = 0; i < Qvalues[a].length; i++) {
            sum += Qvalues[a][i];
            if (Qvalues[a][i] != 0) {
                numOfnonZero++;
            }
        }
        if (numOfnonZero == 0) {
            numOfnonZero = 1;
        }
        return sum/numOfnonZero;
    }

    public void printState(int a) {
        for (int i = 0; i < Qvalues[a].length; i++) {
            System.out.println(Qvalues[a][i]);
        }
    }
}
