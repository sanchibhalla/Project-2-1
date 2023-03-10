package simulation;

import javafx.scene.control.RadioButton;
import logic.Config;
import logic.player.AIPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SimulationHandler {

    //private final AIPlayer white, black;
    private final SimulatorSingleGame game;
    private final SimulatorState states;
    private final RadioButton simulationOption;
    private final RadioButton singleGameOption;
    //choose which stats/kpis to track
    public List<String> trackedStates = new ArrayList<String>();
    //the actual stats/kpis from the game
    public List<String> actualStats = new ArrayList<String>();
    //string boolean hash map with flags of what we keep track of
    HashMap<String, Boolean> stringBooleanHashMap = new HashMap<String, Boolean>();
    boolean[] booleanList;
    String[] header;

    //boolean switch for kpis
    boolean alg = true;
    boolean algTwo = true;
    boolean winner = true;
    boolean numTurns = true;
    boolean timePerMoveWhite = true;
    boolean timePerMoveBlack = true;
    boolean totalGameTime = true;
    boolean numberOfPiecesWhite = true;
    boolean numberOfPiecesBlack = true;
    boolean valueOfPiecesSummedWhite = true;
    boolean valueOfPiecesSummedBlack = true;


    public SimulationHandler(AIPlayer white, AIPlayer black, String FEN, RadioButton simulationOption, RadioButton singleGameOption) {
        game = new SimulatorSingleGame(white, black, FEN);
        states = new SimulatorState(white, black, FEN);
        this.simulationOption = simulationOption;
        this.singleGameOption = singleGameOption;
    }

    public void startHandler() {
        //add header row for kpis
        addHeaderRow();
        //create HashMap for easier transfer of what to keep track of
        createHashMap();
        //check for SingleSimulation to show GUI or multi Simulation


        if ((Config.SIMULATION_SIZE != 1) && simulationOption.isSelected()) { //change to include if != a single game

            SimulatorMultiGame smg = new SimulatorMultiGame(game, Config.SIMULATION_SIZE, stringBooleanHashMap, trackedStates);
            smg.start();

        } else {
            actualStats = (game.start(winner, numTurns, timePerMoveWhite, timePerMoveBlack, totalGameTime, numberOfPiecesWhite, numberOfPiecesBlack, valueOfPiecesSummedWhite, valueOfPiecesSummedBlack));

            //Writing Single Game to Csv
            OutputToCsv writer = OutputToCsv.getInstance("singleGame.csv");
            writer.setTrackedStates(trackedStates);
            writer.writeToFileGame(actualStats); //concatenated now only has the actual states

            //Writing Each State of Game to Csv
            states.setTimeperMoveWhite(game.getTimeperMoveWhite());
            states.setTimeperMoveBlack(game.getTimeperMoveBlack());
            states.setNumTurns(game.getNumTurns());
            states.setCapturePieceArraysWhite(game.getCapturePieceArrayW(), game.getCapturePieceArrayB());
            states.setPieceArraysWhite(game.getPieceArrayW(), game.getPieceArrayB());
            states.setEvaluationBoards(game.getEvaluationBoardW(), game.getEvaluationBoardB());
            ArrayList<String> statesStats = (states.startStateSimulation());
            OutputToCsv writer1 = new OutputToCsv("singleStates.csv");
            writer1.writeEachState(statesStats);
        }

    }

    public void addHeaderRow() {
        booleanList = new boolean[]{alg, algTwo, winner, numTurns, timePerMoveWhite, timePerMoveBlack, totalGameTime, numberOfPiecesWhite, numberOfPiecesBlack, valueOfPiecesSummedWhite, valueOfPiecesSummedBlack};
        header = new String[]{"Alg", "AlgTwo", "TimePerMoveWhite", "TimePerMoveBlack", "TotalGameTime", "Winner", "Turns", "NumberOfPiecesWhite", "NumberOfPiecesBlack", "ValueOfPiecesSummedWhite", "ValueOfPiecesSummedBlack"};


        for (int i = 0; i < booleanList.length; i++) {
            if (booleanList[i]) {
                trackedStates.add(header[i]);
            }
        }
    }

    public void createHashMap() {
        for (int i = 0; i < header.length; i++) {
            stringBooleanHashMap.put(header[i], booleanList[i]);
        }
    }

    public boolean getWinner() {
        return winner;
    }
}
