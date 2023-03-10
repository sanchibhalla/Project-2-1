import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import logic.Dice;

import java.io.IOException;

public class DiceChess extends Application {

    public static void main(String[] args) {launch();}

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/altMenu.fxml"));

        Scene scene = new Scene(root);
        stage.getIcons().add(new Image("/images/DiceChessIcon.png"));

        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();

    }

}