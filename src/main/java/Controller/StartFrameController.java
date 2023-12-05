package Controller;

import Launch.LaunchMC;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import jmccc.microsoft.MicrosoftAuthenticator;
import util.EffectAnimation;
import util.OtherUtil;
import entity.InitAuthenticator;

import java.io.File;

public class StartFrameController {
    @FXML
    HBox tipsBox;
    @FXML
    Text tips;
    @FXML
    AnchorPane sonFrame;
    @FXML
    AnchorPane sonFrameSource;
//    用于获取当前jar包的位置
    String rootPath = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath()).getParent();
//    用于防止动画撞车
    Timeline timeline;
//    用于接收游戏目录
    static String settingGamePath = "";
//    判断界面的开关，以免撞车==============
    static boolean downloadFlag = false;
    static boolean playerFlag = false;
    static boolean gameFlag = false;
//====================================
    @FXML
    AnchorPane version;
    @FXML
    ComboBox<String> versionChoiceBox;
    @FXML
    Button startBtn;
    @FXML
    AnchorPane leftMenu;
    @FXML
    Button playerSetting;
    @FXML
    Button gameSetting;
    @FXML
    Button downloadGame;
    @FXML
    Button download;

    @FXML
    public void initialize() {
        initDir();
        getLocalVersions();
        initJreVersion();
    }
//  打开玩家角色设置界面
    public void playerSetting(){
        if(!downloadFlag && !playerFlag && !gameFlag){
            checkTimeline();
            sonFrameSource.getChildren().setAll(new Frame().playerSetting());
            EffectAnimation effect = new EffectAnimation();
            effect.fadeEmergeVanish(0.1,true,sonFrame);
            timeline =  effect.switchPage(sonFrameSource,0.3,425,25,true);
            timeline.play();
        }
    }
//    打开游戏设置界面
    public void gameSetting(){
        if(!downloadFlag && !playerFlag && !gameFlag){
            checkTimeline();
            sonFrameSource.getChildren().setAll(new Frame().gameSetting());
            EffectAnimation effect = new EffectAnimation();
            effect.fadeEmergeVanish(0.1,true,sonFrame);
            timeline =  effect.switchPage(sonFrameSource,0.3,425,25,true);
            timeline.play();
        }
    }
//    打开下载版本界面
    public void downloadMC(){
        if(!downloadFlag && !playerFlag && !gameFlag){
            checkTimeline();
            DownloadController.file = new File(rootPath);
            sonFrameSource.getChildren().setAll(new Frame().downloadFrame());
            EffectAnimation effect = new EffectAnimation();
            effect.fadeEmergeVanish(0.1,true,sonFrame);
            timeline =  effect.switchPage(sonFrameSource,0.3,425,25,true);
            timeline.play();
        }
    }
//    开始游戏按钮
    public void startBtn() {
        if(LaunchMC.authenticator == null){
            checkTimeline();
            EffectAnimation effect = new EffectAnimation();
            timeline = effect.tipsEffect(tipsBox,tips,0.2,2,"请选择角色");
            timeline.play();
        }
        else {
            checkTimeline();
            sonFrameSource.getChildren().setAll(new Frame().launchFrame());
            EffectAnimation effect = new EffectAnimation();
            effect.fadeEmergeVanish(0.1,true,sonFrame);
            timeline =  effect.switchPage(sonFrameSource,0.3,425,25,true);
            timeline.play();
        }
    }
//    初始化游戏目录
    public void initDir(){
        if(LaunchMC.selfDir != null){
            settingGamePath = LaunchMC.selfDir.getPath();
        }
        File MinecraftDir = new File(settingGamePath);
        if(settingGamePath.isEmpty()){
            MinecraftDir = new File(rootPath+"/.minecraft");
        }
        LaunchMC.directory = MinecraftDir.getAbsolutePath();
    }
//    获取jar包当前目录是否有.minecraft和版本
    public void getLocalVersions(){
        download.setText("未发现版本\n下载Minecraft");
        File versionFilePath = new File(LaunchMC.directory+"/versions");
        if(versionFilePath.exists()){
            File[] versionFiles = versionFilePath.listFiles(File::isDirectory);
            if (versionFiles != null && versionFiles.length > 0) {
                String firstVersion = versionFiles[0].getName();
                for (int i = 0; i < versionFiles.length; i++) {
                    versionChoiceBox.getItems().add(versionFiles[i].getName());
                }
                startBtn.setText("启动\n"+firstVersion);
                if(LaunchMC.version.isEmpty()){
                    LaunchMC.version = firstVersion;
                    versionChoiceBox.setValue(firstVersion);
                }
                else{
                    versionChoiceBox.setValue(LaunchMC.version);
                    startBtn.setText("启动\n"+versionChoiceBox.getValue());
                }
                LaunchMC.runtimeDir = new File(LaunchMC.directory+"/versions/"+firstVersion);
                versionChoiceBox.setOnAction(event -> {
                    LaunchMC.runtimeDir = new File(LaunchMC.directory+"/versions/"+versionChoiceBox.getValue());
                    LaunchMC.version = versionChoiceBox.getValue();
                    startBtn.setText("启动\n"+versionChoiceBox.getValue());
                });
                versionChoiceBox.setVisible(true);
                startBtn.setVisible(true);
                download.setVisible(false);
            }
            else {
                versionChoiceBox.setVisible(false);
                startBtn.setVisible(false);
                download.setVisible(true);
            }
        }
        else{
            download.setVisible(true);
            versionChoiceBox.setVisible(false);
            startBtn.setVisible(false);
        }
    }
//    初始化jre版本
    public void initJreVersion(){
        if(LaunchMC.jreVersions.isEmpty()){
            OtherUtil otherUtil = new OtherUtil();
            LaunchMC.jreVersions = otherUtil.getJreVersions();
        }
    }
//    防止动画撞车
    public void checkTimeline(){
        if(timeline != null){
            timeline.stop();
        }
    }
}
