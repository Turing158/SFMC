package Controller;

import Launch.LaunchMC;
import entity.Player;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.to2mbn.jmccc.auth.AuthenticationException;
import org.to2mbn.jmccc.auth.Authenticator;
import org.to2mbn.jmccc.auth.OfflineAuthenticator;
import util.EffectAnimation;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;

public class NEWPlayerSettingController {

    EffectAnimation effect = new EffectAnimation();

    @FXML
    public Button exit;
    @FXML
    public AnchorPane player;
    @FXML
    public AnchorPane addPlayer;
    @FXML
    public AnchorPane outline;
    @FXML
    public AnchorPane online;
    @FXML
    public TextField outlineUsername;
    @FXML
    public Button addOutline;
    @FXML
    public Label onlineTips;
    @FXML
    public Hyperlink linkMoveAccount;
    @FXML
    public Hyperlink linkHow;
    @FXML
    public Button cancelOutline;
    @FXML
    public Button cancelOnline;

    @FXML
    public void initialize(){
        initPlayerPage();
    }
    public void initPlayerPage(){
        player.getChildren().setAll(new Frame().player());
    }


    public void outlinePage(){
        if(!addPlayer.isVisible()){
            effect.fadeEmergeVanish(0.2,true,addPlayer);
        }
        if(!outline.isVisible()){
            effect.fadeEmergeVanish(0.2,true,outline);
        }
        effect.fadeEmergeVanish(0.2,false,online,player);
        cancelOutline.setOnAction(event -> {
            effect.fadeEmergeVanish(0.2,false,outline,addPlayer);
            effect.fadeEmergeVanish(0.2,true,player);
        });
    }
    public void addOutlinePlayer() throws AuthenticationException {
        if(!outlineUsername.getText().isEmpty()){
            ArrayList<Player> players = LaunchMC.players;
            boolean flag = true;
            for (int i = 0; i < players.size(); i++) {
                if(players.get(i).getAuthenticator().auth().getUsername().equals(outlineUsername.getText())){
                    flag = false;
                    break;
                }
            }
            if(flag){
                OfflineAuthenticator offlineAuthenticator = new OfflineAuthenticator(outlineUsername.getText());
                LaunchMC.players.add(new Player("outline",offlineAuthenticator));
                initPlayerPage();
            }
            else {
                System.out.println("玩家已存在");
            }
            effect.fadeEmergeVanish(0.2,false,outline,addPlayer);
            effect.fadeEmergeVanish(0.2,true,player);
        }
        else {
            System.out.println("请填写玩家名");
        }
    }
    public void onlinePage(){
        if(!addPlayer.isVisible()){
            effect.fadeEmergeVanish(0.2,true,addPlayer);
        }
        if(!online.isVisible()){
            effect.fadeEmergeVanish(0.2,true,online);
        }
        effect.fadeEmergeVanish(0.2,false,outline,player);
        onlineTips.setText("⚠ERROR: \n\n由于Mojang被微软[Microsoft]收入，Mojang账户的登录不再受支持，您必须迁移至微软账户才能登录Minecraft");
        linkMoveAccount.setOnAction(event -> {
            openBrowser("https://www.minecraft.net/login?view=mojang");
        });
        linkHow.setOnAction(event -> {
            openBrowser("https://help.minecraft.net/hc/en-us/articles/4403192913933");
        });
        cancelOnline.setOnAction(event -> {
            effect.fadeEmergeVanish(0.2,false,online,addPlayer);
            effect.fadeEmergeVanish(0.2,true,player);
        });
    }
    public void openBrowser(String url){
        Desktop desktop = Desktop.getDesktop();
        if (desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                URI uri = new URI(url);
                desktop.browse(uri);
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        }
        else{
            System.out.println("不支持打开浏览器");
        }
    }
    public void close(){
        effect.fadeEmergeVanish(0.2,false,exit.getParent().getParent().getParent());
        effect.switchPage(exit.getParent().getParent(),0.2,25,425,false).play();
        StartFrameController.playerFlag = false;
    }
}
