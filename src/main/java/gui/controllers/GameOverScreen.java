package gui.controllers;

import javafx.scene.Node;
import javafx.stage.Modality;
import logic.enums.Side;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

import static logic.enums.Side.WHITE;

public class GameOverScreen extends AnchorPane {

    Side winner;

    public GameOverScreen(Side winner) {
        this.winner = winner;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/gameOverScreen.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (Exception e) {
            e.printStackTrace();;
        }
    }

    @FXML
    void initialize() {
        resultLabel.setText(winner == WHITE ? "White wins!" : "Black wins!");
    }

    @FXML
    private Label resultLabel;


    @FXML
    private Button quitButton;

    @FXML
    void quit(ActionEvent event) {
        Platform.exit();
    }



}
