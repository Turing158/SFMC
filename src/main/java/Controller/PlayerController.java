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
    public void save(){
        if(playerName.getText().isEmpty()){
            System.out.println("请输入用户名");
        }
        else{
            LaunchMC.username = playerName.getText();
        }
    }
    public void close(){
        Stage stage = (Stage) exit.getScene().getWindow();
        StartFrameController.playerFlag = false;
        stage.close();
    }
}
