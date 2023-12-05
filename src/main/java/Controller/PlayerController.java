package Controller;

import Launch.LaunchMC;
import entity.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import jmccc.microsoft.MicrosoftAuthenticator;
import org.to2mbn.jmccc.auth.AuthenticationException;
import org.to2mbn.jmccc.auth.OfflineAuthenticator;
import util.EffectAnimation;
import entity.InitAuthenticator;

import java.util.ArrayList;
import java.util.Arrays;

public class PlayerController {
    EffectAnimation effect = new EffectAnimation();
    public Label playerPage;



    @FXML
    public void initialize() throws AuthenticationException {
        initPlayer();
    }
//    初始化角色
    public void initPlayer() throws AuthenticationException {
        AnchorPane pane = (AnchorPane) playerPage.getParent();
//        防止一个角色都没有会报错
        if(pane.getChildren().size() != 1){
            pane.getChildren().remove(1,pane.getChildren().size());
        }
//        创建切换组
        ToggleGroup group = new ToggleGroup();
//        获取角色列表
        ArrayList<Player> players = LaunchMC.players;
//        如果角色列表不为空
        if(!players.isEmpty()){
            for (int i = 0; i < players.size(); i++) {
                Player player = players.get(i);
//                创建选择角色按钮
                RadioButton radioButton = new RadioButton();
//                初始化指示器[选了哪个角色]
                if(LaunchMC.selectPlayer != -1 && i == LaunchMC.selectPlayer){
                    radioButton.setSelected(true);
                }
//                判断角色是否为离线角色,并设置radioButton的文本
                if(player.getOffUsername() != null){
                    radioButton.setText("[离线]\t"+new OfflineAuthenticator(player.getOffUsername()).auth().getUsername());
                }
                else {
                    radioButton.setText("[正版]\t"+player.getAuthInfo().getUsername());
                }
//                创建删除角色按钮
                Button button = new Button("🚮");
                button.setLayoutX(360);
                button.setLayoutY(20.0+55*i);
                button.setPrefHeight(35);
                button.setPrefWidth(35);
//                int一个final的i
                int finalI = i;
                button.setOnAction(e->{
//                    获取指示器
                    int selectPlayer = LaunchMC.selectPlayer;
//                    如果删除的角色是当前选中的角色
                    if(selectPlayer == finalI){
                        LaunchMC.selectPlayer = -1;
                        LaunchMC.authenticator = null;
                    }
//                    如果删除的角色在选中角色的前面
                    else if(finalI < selectPlayer){
//                        指示器前移并切换新的authenticator
                        LaunchMC.selectPlayer--;
                        LaunchMC.authenticator = players.get(LaunchMC.selectPlayer).getMicrosoftAuthenticator();
                    }
                    effect.fadeEmergeVanish(0.2,false,radioButton);
//                    移除角色
                    players.remove(players.get(finalI));
                    try {
//                        重新初始化角色
                        initPlayer();
                    } catch (AuthenticationException ex) {
                        ex.printStackTrace();
                    }
                });
//                int一个final的i
                int finalI1 = i;
//                为radioButton添加事件
                radioButton.setOnAction(e -> {
//                    指示器指向当前角色
                    LaunchMC.selectPlayer = finalI1;
//                    判断角色是否为离线角色,并设置authenticator
                    Player player1 = players.get(finalI1);
                    if(player1.getOffUsername() != null){
                        LaunchMC.authenticator = new OfflineAuthenticator(player1.getOffUsername());
                    }
                    else{
//                        这里比较复杂,因为authenticator是不能被序列化的,所以只能在这里重新初始化
//                        先获取MicrosoftAuthenticator，然后再通过InitAuthenticator初始化
                        MicrosoftAuthenticator microsoftAuthenticator = player1.getMicrosoftAuthenticator();
                        InitAuthenticator initAuthenticator = new InitAuthenticator(microsoftAuthenticator);
//                        因为MicrosoftAuthenticator里的authInfo是空的,所以要重新设置
                        initAuthenticator.customAuth(player1.getAuthInfo());
                        LaunchMC.authenticator = initAuthenticator;
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
