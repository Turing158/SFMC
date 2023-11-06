package Controller;

import Launch.LaunchMC;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PlayerController {
    @FXML
    TextField playerName;
    @FXML
    Button exit;
    public void close(){
        LaunchMC.username = playerName.getText();
        exit.getParent().getParent().getParent().setVisible(false);
        Stage stage = (Stage) exit.getParent().getParent().getParent().getParent().getScene().getWindow();
        stage.setScene(new Frame().StartFrame());
        StartFrameController.playerFlag = false;
    }
}
