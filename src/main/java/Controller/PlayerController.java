package Controller;

import Launch.LaunchMC;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PlayerController {
    @FXML
    public Label username;
    @FXML
    public Label uuid;
    @FXML
    public Label title;

    @FXML
    public void initialize(){
        title.setText("Microsoft认证");
        username.setText(LaunchMC.authInfo.getUsername());
        uuid.setText(String.valueOf(LaunchMC.authInfo.getUUID()));
    }
}
