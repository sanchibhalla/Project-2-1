package dataCollection;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import logic.enums.GameType;

import java.util.ArrayList;

public class GameInfo {


    public SimpleStringProperty gameType = new SimpleStringProperty();
    public SimpleStringProperty algUsed = new SimpleStringProperty();
    public SimpleStringProperty algSide = new SimpleStringProperty();
    public SimpleStringProperty algTwo = new SimpleStringProperty();
    public SimpleStringProperty gameWinner = new SimpleStringProperty();
    public SimpleIntegerProperty numTurnsToWin = new SimpleIntegerProperty();

    public ArrayList<Integer> numTurnsToWinList = new ArrayList<>();
    public SimpleIntegerProperty avgToWin = new SimpleIntegerProperty();

    //public ArrayList<Integer> numTurnsToWinList = new ArrayList<>();
    //public SimpleIntegerProperty avgToWin = new SimpleIntegerProperty();

    public GameInfo(){}

    public GameInfo(SimpleStringProperty gameType, SimpleStringProperty algUsed, SimpleStringProperty algSide, SimpleStringProperty gameWinner, SimpleIntegerProperty numTurnsToWin) {
        this.gameType = gameType;
        this.algUsed = algUsed;
        this.algSide = algSide;
        this.gameWinner = gameWinner;
        this.numTurnsToWin = numTurnsToWin;
    }

    public GameInfo(SimpleStringProperty alg, SimpleStringProperty algTwo, SimpleStringProperty winner, SimpleIntegerProperty turns){
        this.algUsed = alg;
        this.algTwo = algTwo;
        this.gameWinner = winner;
        this.numTurnsToWin = turns;
    }


    public String getGameType(){return this.gameType.get();}
    public void setGameType(String type){this.gameType.set(type);}

    public String getAlgUsed(){return this.algUsed.get();}
    public void setAlgUsed(String alg){this.algUsed.set(alg);}

    public String getAlgSide(){return this.algSide.get();}
    public void setAlgSide(String algSide){this.algSide.set(algSide);}

    public String getAlgTwo(){return this.algTwo.get();}
    public void setAlgTwo(String algTwo){this.algTwo.set(algTwo);}

    public String getGameWinner(){return this.gameWinner.get();}
    public void setGameWinner(String winner){this.gameWinner.set(winner);}

    public int getNumTurnsToWin(){return this.numTurnsToWin.get();}
    public void setNumTurnsToWin(int num){this.numTurnsToWin.set(num);}



}
