package Controller;

import Launch.LaunchMC;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import util.EffectAnimation;

public class PlayerSettingController {
    @FXML
    public ComboBox<String> selectFunc;
    @FXML
    public TextField playerUser;
    @FXML
    public TextField playerPassword;
    @FXML
    public Button playerVerify;
    @FXML
    public Button playerMicrosoft;
    @FXML
    public AnchorPane outline;
    @FXML
    public AnchorPane online;
    @FXML
    public AnchorPane Microsoft;
    @FXML
    public AnchorPane microsoftVerify;
    @FXML
    TextField playerName;
    @FXML
    Button exit;
    @FXML
    public void initialize(){
        initSelectFunc();
        initInfo();

    }
//    微软认证按钮
    public void microsoftLogin() {
        EffectAnimation effect =  new EffectAnimation();
        microsoftVerify.getChildren().setAll(new Frame().verifyMicrosoft());
        effect.fadeEmergeVanish(0.2,true,microsoftVerify);
    }
//    初始化选择登录方式
    public void initSelectFunc(){
         playerName.setText(LaunchMC.username);
        selectFunc.getItems().addAll("离线登录","正版登录","微软登录");
        selectFunc.setOnAction(event -> {
            if(selectFunc.getValue().equals("离线登录")){
                outline.setVisible(true);
                online.setVisible(false);
                Microsoft.setVisible(false);
                LaunchMC.playerFunc = "offline";
            }
            else if(selectFunc.getValue().equals("正版登录")){
                outline.setVisible(false);
                online.setVisible(true);
                Microsoft.setVisible(false);
                LaunchMC.playerFunc = "online";
            }
            else  if(selectFunc.getValue().equals("微软登录")){
                outline.setVisible(false);
                online.setVisible(false);
                Microsoft.setVisible(true);
                LaunchMC.playerFunc = "microsoft";
            }
//            初始化玩家角色信息[用于正版]
            initInfo();
        });
//        用于切换登录方式的小模块
        if(LaunchMC.playerFunc.equals("microsoft")){
            selectFunc.setValue("微软登录");
            outline.setVisible(false);
            online.setVisible(false);
            Microsoft.setVisible(true);
        }
        else if(LaunchMC.playerFunc.equals("online")){
            selectFunc.setValue("正版登录");
            outline.setVisible(false);
            online.setVisible(true);
            Microsoft.setVisible(false);
        }
        else {
            selectFunc.setValue("离线登录");
            outline.setVisible(true);
            online.setVisible(false);
            Microsoft.setVisible(false);
        }
    }
//    初始化玩家角色信息[用于正版]，由小界面来实现初始化，这个只是用于打开小界面
    public void initInfo() {
        if(LaunchMC.playerFunc.equals("microsoft") && LaunchMC.authInfo != null) {
            microsoftVerify.setVisible(false);
            EffectAnimation effect =  new EffectAnimation();
            microsoftVerify.getChildren().setAll(new Frame().player());
            effect.fadeEmergeVanish(0.2,true,microsoftVerify);
        }
        else{
            microsoftVerify.setVisible(false);
        }
    }
    public void close(){
        LaunchMC.username = playerName.getText();
        EffectAnimation effect = new EffectAnimation();
        effect.fadeEmergeVanish(0.2,false,exit.getParent().getParent().getParent());
        effect.switchPage(exit.getParent().getParent(),0.2,25,425,false).play();
        StartFrameController.playerFlag = false;
    }
}
