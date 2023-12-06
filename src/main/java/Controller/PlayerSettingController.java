package Controller;

import Launch.LaunchMC;
import entity.Player;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import util.EffectAnimation;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class PlayerSettingController {
    @FXML
    public AnchorPane microsoftVerify;
    @FXML
    public ScrollBar playerScrollBar;
    EffectAnimation effect = new EffectAnimation();

    @FXML
    public Button exit;
    @FXML
    public Pane player;
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
        Rectangle clip = new Rectangle(player.getPrefWidth(),player.getPrefHeight());
        player.setClip(clip);
        player.getChildren().setAll(new Frame().player());
        AnchorPane content = (AnchorPane) player.getChildren().get(0);
        if(LaunchMC.players.size() > 6){
            playerScrollBar.setVisible(true);
        }
        else {
            playerScrollBar.setVisible(false);
        }
        Platform.runLater(()->{
            playerScrollBar.setVisibleAmount(30);
        });
        playerScrollBar.valueProperty().addListener((observable, oldValue, newValue) -> {
            double scrollValue = newValue.doubleValue();
            double contentHeight = content.getHeight();
            double scrollHeight = playerScrollBar.getHeight();
            double translateY = -scrollValue * (contentHeight-scrollHeight)/100;
            content.setTranslateY(translateY);
        });
        playerScrollBar.getParent().addEventFilter(ScrollEvent.SCROLL, event -> {
            double deltaY = event.getDeltaY(); // 获取滚轮滚动的垂直方向增量
            double value = playerScrollBar.getValue();
            if(value >= 0 && value <= 100){
                double setValue = playerScrollBar.getValue()-deltaY/5;
                if(setValue >= 0 && setValue <= 100){
                    playerScrollBar.setValue(setValue);
                }
                else if(setValue > 100){
                    playerScrollBar.setValue(100);
                }
                else {
                    playerScrollBar.setValue(0);
                }

            }
            else if(value > 100){
                playerScrollBar.setValue(100);
            }
            else{
                playerScrollBar.setValue(0);
            }
        });
    }


    public void outlinePage(){
        if(!addPlayer.isVisible()){
            effect.fadeEmergeVanish(0.2,true,addPlayer);
        }
        if(!outline.isVisible()){
            effect.fadeEmergeVanish(0.2,true,outline);
        }
        effect.fadeEmergeVanish(0.2,false,online,player,microsoftVerify);
        cancelOutline.setOnAction(event -> {
            effect.fadeEmergeVanish(0.2,false,outline,addPlayer);
            effect.fadeEmergeVanish(0.2,true,player);
        });
    }
    public void addOutlinePlayer() {
        if(!outlineUsername.getText().isEmpty()){
            ArrayList<Player> players = LaunchMC.players;
            boolean flag = true;
            for (int i = 0; i < players.size(); i++) {
                if(players.get(i).getOffUsername() != null && players.get(i).getOffUsername().equals(outlineUsername.getText())){
                    flag = false;
                    break;
                }
            }
            if(flag){
                Player player = new Player();
                player.setOffUsername(outlineUsername.getText());
                LaunchMC.selectPlayer = players.size();
                LaunchMC.players.add(player);
                initPlayerPage();

            }
            else {
                System.out.println("玩家已存在");
            }
            effect.fadeEmergeVanish(0.2,false,outline,addPlayer,microsoftVerify);
            effect.fadeEmergeVanish(0.2,true,player);
            outlineUsername.clear();
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
        effect.fadeEmergeVanish(0.2,false,outline,player,microsoftVerify);
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
    public void addOnlinePlayer(){
        microsoftVerify.getChildren().setAll(new Frame().verifyMicrosoft());
        effect.fadeEmergeVanish(0.2,true,microsoftVerify);
        effect.fadeEmergeVanish(0.2,false,online,addPlayer,outline,player);
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
