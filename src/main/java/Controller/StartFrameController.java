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
import org.to2mbn.jmccc.mcdownloader.MinecraftDownloader;
import org.to2mbn.jmccc.mcdownloader.MinecraftDownloaderBuilder;
import org.to2mbn.jmccc.mcdownloader.RemoteVersionList;
import org.to2mbn.jmccc.mcdownloader.download.concurrent.CallbackAdapter;
import util.EffectAnimation;
import util.OtherUtil;
import util.initAuthenticator;

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
    public void initialize(){
        initAuthenticator();
        initDir();
        initDownloadVersions();
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
        if(LaunchMC.username.isEmpty() && LaunchMC.playerFunc.equals("offline")){
            checkTimeline();
            EffectAnimation effect = new EffectAnimation();
            timeline = effect.tipsEffect(tipsBox,tips,0.2,2,"请输入用户名");
            timeline.play();
        }
        else {
            LaunchMC launchMC = new LaunchMC();
            launchMC.start();
        }
    }
//    初始化正版验证信息
    public void initAuthenticator(){
        if(LaunchMC.playerFunc.equals("microsoft")){
            MicrosoftAuthenticator authenticator = LaunchMC.microsoftAuthenticator;
            LaunchMC.authenticator = new initAuthenticator(authenticator);
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
            if (versionFiles != null) {
                String firstVersion = versionFiles[0].getName();
                for (int i = 0; i < versionFiles.length; i++) {
                    versionChoiceBox.getItems().add(versionFiles[i].getName());
                }
                startBtn.setText("启动\n"+firstVersion);
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
//    初始化下载版本
    public void initDownloadVersions(){
        if(LaunchMC.versions.isEmpty()){
            MinecraftDownloader versionDownloader = MinecraftDownloaderBuilder.buildDefault();
            versionDownloader.fetchRemoteVersionList(new CallbackAdapter<RemoteVersionList>() {
                @Override
                public void done(RemoteVersionList result) {
                    LaunchMC.versions.add(result.getLatestSnapshot());
                    result.getVersions().forEach((k,v) -> {
                        if(k.matches("\\d+\\.\\d+(\\.\\d+)?")){
                            LaunchMC.versions.add(k);
                        }
                    });
                    versionDownloader.shutdown();
                }
            });

        }
    }
//    初始化jre版本
    public void initJreVersion(){
        OtherUtil otherUtil = new OtherUtil();
        LaunchMC.jreVersions =  otherUtil.getJreVersions();
    }
//    防止动画撞车
    public void checkTimeline(){
        if(timeline != null){
            timeline.stop();
        }
    }
}
