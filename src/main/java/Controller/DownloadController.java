package Controller;

import Launch.LaunchMC;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.to2mbn.jmccc.mcdownloader.MinecraftDownloader;
import org.to2mbn.jmccc.mcdownloader.MinecraftDownloaderBuilder;
import org.to2mbn.jmccc.mcdownloader.RemoteVersionList;
import org.to2mbn.jmccc.mcdownloader.download.concurrent.CallbackAdapter;
import org.to2mbn.jmccc.mcdownloader.download.concurrent.DownloadCallback;
import org.to2mbn.jmccc.mcdownloader.download.tasks.DownloadTask;
import org.to2mbn.jmccc.version.Version;
import util.DownloadMinecraft;
import util.EffectAnimation;

import java.io.File;
import java.util.ArrayList;

public class DownloadController {
//    初始化下载类
    DownloadMinecraft downloadMinecraft = null;
//    用来从其他类传递下载目录
    static File file;
    @FXML
    public Button downloadCancel;
    @FXML
    public Button openDir;
    @FXML
    public Button downloadRetry;
    @FXML
    Button download;
    @FXML
    TextField downloadDir;
    @FXML
    TextArea downloadInfo;
    @FXML
    ComboBox<String> versionChoiceBox;
    @FXML
    Button exit;
    @FXML
    Button selectDirBtn;
    @FXML
    public void initialize(){
        initDownloadVersion();
        initDownloadInfo();
    }
//    初始化/更新下载信息
//    private void updateVersions() {
////        获取版本列表[json缓存]
//        ArrayList<String> versions = LaunchMC.versions;
//        for (int i = 0; i < versions.size(); i++) {
//            versionChoiceBox.getItems().add(versions.get(i));
//        }
//    }
//  开始下载按钮事件
    public void downloadStart(){
//        检查下载版本是否为空
        if(versionChoiceBox.getValue()==null){
            downloadInfo.setText(downloadInfo.getText()+"\n请选择版本");
        }
//        检查下载目录是否为空
        else if(file==null){
            downloadInfo.setText(downloadInfo.getText()+"\n请选择下载目录");
        }
        else{
            downloadInfo.setText(downloadInfo.getText()+"\n正在下载，请稍后...");
//            隐藏下载按钮
            download.setVisible(false);
            downloadCancel.setVisible(true);
//            禁用选择按钮
            selectDirBtn.setDisable(true);
            versionChoiceBox.setDisable(true);
            downloadDir.setDisable(true);
//            初始化下载类
            downloadMinecraft = new DownloadMinecraft();
//            开始下载[参数：下载目录，下载版本，下载消息回调]
            downloadMinecraft.download(file.getPath(),versionChoiceBox.getValue(),new CallbackAdapter<Version>() {
                @Override
//                下载完成
                public void done(Version result) {
                    System.out.println("下载完成：Minecraft v"+result.getVersion());
                    downloadInfo.setText(downloadInfo.getText()+"\n下载完成：Minecraft "+result.getVersion());
                    LaunchMC.directory = new File(file.getPath()+"./minecraft").getAbsolutePath();
                    download.setVisible(false);
                    downloadCancel.setVisible(false);
                    downloadRetry.setVisible(false);

                    selectDirBtn.setDisable(false);
                    versionChoiceBox.setDisable(false);
                    downloadDir.setDisable(false);
                    downloadMinecraft.shutdown();
                }

                @Override
//                下载失败
                public void failed(Throwable e) {
                    downloadInfo.setText(downloadInfo.getText()+"\n下载失败");
                    e.printStackTrace();
                    downloadRetry.setVisible(true);

                    selectDirBtn.setDisable(true);
                    versionChoiceBox.setDisable(true);
                    downloadDir.setDisable(true);
                }

                @Override
//                取消下载
                public void cancelled() {
//                    System.out.println("取消下载");
                    downloadInfo.setText(downloadInfo.getText()+"\n已取消下载");
                    selectDirBtn.setDisable(true);
                    versionChoiceBox.setDisable(true);
                    downloadDir.setDisable(true);
                }
                @Override
//                重试[暂不可用]
                public void retry(Throwable e, int current, int max) {
                    System.out.println("重试");
                }

                @Override
//                下载详细信息
                public <R> DownloadCallback<R> taskStart(DownloadTask<R> task) {
                    if(task.isCacheable()){
                        downloadInfo.appendText("\n开始下载："+task.getURI()+"\n...");
                    }
                    return super.taskStart(task);
                }
            });

        }
    }
//    取消下载按钮事件
    public void cancelDownload(){
        downloadMinecraft.cancel();
    }
    public void initDownloadVersion(){
        versionChoiceBox.setValue("正在获取版本列表...");
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() {
                MinecraftDownloader versionDownloader = MinecraftDownloaderBuilder.buildDefault();
                versionDownloader.fetchRemoteVersionList(new CallbackAdapter<RemoteVersionList>() {
                    @Override
                    public void done(RemoteVersionList result) {
                        Platform.runLater(() ->{
                            versionChoiceBox.getItems().clear();
                            versionChoiceBox.setValue(result.getLatestSnapshot());
                            versionChoiceBox.getItems().add(result.getLatestSnapshot());
                            result.getVersions().forEach((k,v) -> {
                                if(k.matches("\\d+\\.\\d+(\\.\\d+)?")){
                                    versionChoiceBox.getItems().add(k);
                                }
                            });
                            versionDownloader.shutdown();
                            versionChoiceBox.setDisable(false);
                        });
                    }
                });
                return null;
            }
        };
        new Thread(task).start();
    }
//    初始化下载信息
    public void initDownloadInfo(){
        download.setVisible(false);
        downloadCancel.setVisible(false);
        downloadRetry.setVisible(false);
        if(file != null){
            downloadDir.setText(file.getPath());
        }
        versionChoiceBox.setOnAction(event -> {
            download.setVisible(true);
            downloadInfo.setText(downloadInfo.getText()+"\n选择了"+versionChoiceBox.getValue()+"版本");
        });
    }
//    选择下载目录事件
    public void selectDir(){
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("选择Minecraft目录");
        file = directoryChooser.showDialog(new Stage());
        if(file != null){
            downloadInfo.setText(downloadInfo.getText()+"\n选择了下载目录目录："+file.getPath());
            downloadDir.setText(file.getPath());
        }
    }
//    关闭下载页面
    public void close(){
        EffectAnimation effect = new EffectAnimation();
        effect.fadeEmergeVanish(0.2,false,exit.getParent().getParent().getParent());
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, event -> {
                    effect.switchPage(exit.getParent().getParent(),0.2,25,425,false).play();
                }),
                new KeyFrame(Duration.seconds(0.2),event -> {
                    Stage stage = (Stage) exit.getScene().getWindow();
                    stage.setScene(new Frame().StartFrame());
                })
        );
        timeline.play();
        StartFrameController.downloadFlag = false;
    }
}
