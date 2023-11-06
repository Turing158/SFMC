package Controller;

import Launch.LaunchMC;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PlayerSettingController {
    @FXML
    TextField playerName;
    @FXML
    Button exit;
    public void close(){
        LaunchMC.username = playerName.getText();
        exit.getParent().getParent().getParent().setVisible(false);
        StartFrameController.playerFlag = false;
    }
}
