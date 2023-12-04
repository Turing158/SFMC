package Controller;

import Launch.LaunchMC;
import entity.Player;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import org.to2mbn.jmccc.auth.AuthenticationException;
import org.to2mbn.jmccc.auth.Authenticator;
import org.to2mbn.jmccc.auth.OfflineAuthenticator;
import util.EffectAnimation;

import java.util.ArrayList;
import java.util.Arrays;

public class PlayerController {
    EffectAnimation effect = new EffectAnimation();
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
        System.out.println(Arrays.toString(LaunchMC.players.toArray()));
        if(!players.isEmpty()){
            for (int i = 0; i < players.size(); i++) {

                Player player = players.get(i);
                RadioButton radioButton = new RadioButton();
                if(LaunchMC.selectPlayer != -1 && i == LaunchMC.selectPlayer){
                    radioButton.setSelected(true);
                }
                if(player.getOffUsername() != null){
                    radioButton.setText("[离线]\t"+new OfflineAuthenticator(player.getOffUsername()).auth().getUsername());
                }
                else {
                    System.out.println(player.getMicrosoftAuthenticator());
                    radioButton.setText("[正版]\t"+player.getAuthInfo().getUsername());
                }
                Button button = new Button("🚮");
                button.setLayoutX(360);
                button.setLayoutY(20.0+55*i);
                button.setPrefHeight(35);
                button.setPrefWidth(35);
                int finalI = i;
                button.setOnAction(e->{
                    int selectPlayer = LaunchMC.selectPlayer;
                    if(selectPlayer == finalI){
                        LaunchMC.selectPlayer = -1;
                        LaunchMC.authenticator = null;
                    }
                    else if(finalI < selectPlayer){
                        LaunchMC.selectPlayer--;
                        LaunchMC.authenticator = players.get(LaunchMC.selectPlayer).getMicrosoftAuthenticator();
                    }
                    effect.fadeEmergeVanish(0.2,false,radioButton);
                    players.remove(players.get(finalI));
                    try {
                        initPlayer();
                    } catch (AuthenticationException ex) {
                        ex.printStackTrace();
                    }
                });
                int finalI1 = i;
                radioButton.setOnAction(e -> {
                    LaunchMC.selectPlayer = finalI1;
                    Player player1 = players.get(finalI1);
                    if(player1.getOffUsername() != null){
                        LaunchMC.authenticator = new OfflineAuthenticator(player1.getOffUsername());
                    }
                    else{
                        LaunchMC.authenticator = player1.getMicrosoftAuthenticator();
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
