package Controller;

import Launch.LaunchMC;
import javafx.animation.FillTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import sun.java2d.cmm.ColorTransform;
import util.EffectAnimation;

import javax.rmi.CORBA.Tie;
import java.io.File;

public class StartFrameController {



    @FXML
    AnchorPane sonFrame;
    @FXML
    AnchorPane sonFrameSource;
    String rootPath = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
    Timeline timeline;
    static String settingGamePath = "";
    static boolean downloadFlag = false;
    static boolean playerFlag = false;
    static boolean gameFlag = false;

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
    Button download;

    @FXML
    public void initialize(){
        System.out.println("LaunchMC.directory : " + LaunchMC.directory);
        System.out.println("LaunchMC.username : " + LaunchMC.username);
        System.out.println("LaunchMC.version : " + LaunchMC.version);
        System.out.println("LaunchMC.memory : " + LaunchMC.memory);
        File MinecraftDir = new File(settingGamePath);
        if(settingGamePath.isEmpty()){
            MinecraftDir = new File(rootPath+".minecraft");
        }
        LaunchMC.directory = MinecraftDir.getAbsolutePath();
        getVersion();

    }

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
    public void startBtn(){
        if(LaunchMC.username.isEmpty()){
            System.out.println("请输入用户名");
        }
        else {
            LaunchMC launchMC = new LaunchMC();
            launchMC.start();
        }
    }

    public void downloadMC(){
        if(!downloadFlag && !playerFlag){
            DownloadController.file = new File(rootPath);
            sonFrame.setVisible(true);
            sonFrameSource.getChildren().setAll(new Frame().downloadFrame());
            downloadFlag = true;

        }
    }
    public void getVersion(){
        download.setText("未发现版本\n下载Minecraft");
        File versionFilePath = new File(LaunchMC.directory+"/versions");
        if(versionFilePath.exists()){
            File[] versionFiles = versionFilePath.listFiles(File::isDirectory);
            if (versionFiles != null) {
                String firstVersion = versionFiles[0].getName();
                for (int i = 0; i < versionFiles.length; i++) {
                    versionChoiceBox.getItems().add(versionFiles[i].getName());
                }
                LaunchMC.version = firstVersion;
                versionChoiceBox.setValue(firstVersion);
                versionChoiceBox.setOnAction(event -> {
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
    public void checkTimeline(){
        if(timeline != null){
            timeline.stop();
        }
    }
}
