package Controller;

import Launch.LaunchMC;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import util.EffectAnimation;

public class PlayerSettingController {
    @FXML
    TextField playerName;
    @FXML
    Button exit;
    @FXML
    public void initialize(){
        playerName.setText(LaunchMC.username);
    }
    public void close(){
        LaunchMC.username = playerName.getText();
        EffectAnimation effect = new EffectAnimation();
        effect.fadeEmergeVanish(0.2,false,exit.getParent().getParent().getParent());
        effect.switchPage(exit.getParent().getParent(),0.2,25,425,false).play();
        StartFrameController.playerFlag = false;
    }
}
