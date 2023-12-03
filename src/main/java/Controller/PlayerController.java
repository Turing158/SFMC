package Controller;

import Launch.LaunchMC;
import entity.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import org.to2mbn.jmccc.auth.AuthenticationException;

import java.util.ArrayList;
import java.util.Arrays;

public class PlayerController {
    public Label playerPage;



    @FXML
    public void initialize() throws AuthenticationException {
        initPlayer();
    }
    public void initPlayer() throws AuthenticationException {
        AnchorPane pane = (AnchorPane) playerPage.getParent();
        if(pane.getChildren().size() != 1){
            pane.getChildren().remove(1,pane.getChildren().size());
        }
        ToggleGroup group = new ToggleGroup();
        ArrayList<Player> players = LaunchMC.players;
        if(!players.isEmpty()){
            for (int i = 0; i < players.size(); i++) {
                RadioButton radioButton = new RadioButton();
                radioButton.setText(players.get(i).getAuthenticator().auth().getUsername());
                Button button = new Button("🚮");
                button.setLayoutX(360);
                button.setLayoutY(20.0+55*i);
                button.setPrefHeight(35);
                button.setPrefWidth(35);
                int finalI = i;
                button.setOnAction(e->{
                    players.remove(players.get(finalI));
                    try {
                        initPlayer();
                    } catch (AuthenticationException ex) {
                        ex.printStackTrace();
                    }
                });
                radioButton.setToggleGroup(group);
                radioButton.setLayoutX(10);
                radioButton.setLayoutY(15.0+55*i);
                radioButton.setPrefHeight(45);
                radioButton.setPrefWidth(390);
                radioButton.getStyleClass().add("playerSelect");
                pane.getChildren().add(radioButton);
                pane.getChildren().add(button);
            }
        }
    }
}
