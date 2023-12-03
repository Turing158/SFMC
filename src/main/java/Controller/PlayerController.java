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
                RadioButton radioButton = new RadioButton();
                Player player = players.get(i);
                if(player.getState().equals("outline")){
                    radioButton.setText("[离线]\t"+player.getAuthenticator().auth().getUsername());
                }
                else {
                    radioButton.setText("[正版]\t"+player.getAuthenticator().auth().getUsername());
                }
                Button button = new Button("🚮");
                button.setLayoutX(360);
                button.setLayoutY(20.0+55*i);
                button.setPrefHeight(35);
                button.setPrefWidth(35);
                int finalI = i;
                button.setOnAction(e->{
                    Task<Void> task = new Task<Void>() {
                        @Override
                        protected Void call() throws Exception {
                            effect.fadeEmergeVanish(0.2,false,radioButton);
                            Thread.sleep(200);
                            players.remove(players.get(finalI));
                            System.out.println(Arrays.toString(LaunchMC.players.toArray()));
                            initPlayer();
//                            这个线程有问题
                            return null;
                        }
                    };
                    new Thread(task).start();
                });
                int finalI1 = i;
                radioButton.setOnAction(e -> {
                    LaunchMC.authenticator = players.get(finalI1).getAuthenticator();
//                    问题待解决：
//                    json不知道可不可以储存对象，如果可以，那么就可以直接储存authenticator对象，这样就不用每次都要重新登录了
//                   如果选了这个角色，删除了，那么所选的角色应该为空，但是可能选角色的时候会报错，所以删除的时候初始化启动类的authenticator，且解决删除了选角色，但是json文件储存所选角色的问题
//                    其实有一种解决这个的方法，创建一个变量，删除角色将这个变量变成-1，并在这个类里，加一个判断，加载已选player时，如果那个变量变为-1，那么不选择任何角色
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
