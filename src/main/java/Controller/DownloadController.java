package Controller;

import Launch.LaunchMC;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.to2mbn.jmccc.mcdownloader.download.Downloader;
import org.to2mbn.jmccc.mcdownloader.download.concurrent.CallbackAdapter;
import org.to2mbn.jmccc.mcdownloader.download.concurrent.DownloadCallback;
import org.to2mbn.jmccc.mcdownloader.download.tasks.DownloadTask;
import org.to2mbn.jmccc.version.Version;
import util.DownloadMinecraft;
import util.EffectAnimation;

import java.io.File;
import java.util.ArrayList;

public class DownloadController {
    DownloadMinecraft downloadMinecraft = new DownloadMinecraft();
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
        download.setVisible(false);
        downloadCancel.setVisible(false);
        downloadRetry.setVisible(false);
        if(file != null){
            downloadDir.setText(file.getPath());
        }
        downloadMinecraft.getDownloadList();
        versionChoiceBox.getItems().addAll("1.8","1.9","1.10","1.11","1.12");
        versionChoiceBox.setOnAction(event -> {
            download.setVisible(true);
            downloadInfo.setText(downloadInfo.getText()+"\n选择了"+versionChoiceBox.getValue()+"版本");
        });
    }
    public void downloadStart(){
        if(versionChoiceBox.getValue()==null){
            downloadInfo.setText(downloadInfo.getText()+"\n请选择版本");
        }
        else if(file==null){
            downloadInfo.setText(downloadInfo.getText()+"\n请选择下载目录");
        }
        else{
            downloadInfo.setText(downloadInfo.getText()+"\n正在下载，请稍后...");
            download.setVisible(false);
            downloadCancel.setVisible(true);
            downloadMinecraft.download(file.getPath(),versionChoiceBox.getValue(),new CallbackAdapter<Version>() {
                @Override
                public void done(Version result) {
                    System.out.println("下载完成：Minecraft v"+result.getVersion());
                    downloadInfo.setText(downloadInfo.getText()+"\n下载完成：Minecraft "+result.getVersion());
                    LaunchMC.directory = new File(file.getPath()+"./minecraft").getAbsolutePath();
                    download.setVisible(false);
                    downloadCancel.setVisible(false);
                    downloadRetry.setVisible(false);
                }

                @Override
                public void failed(Throwable e) {
//                    System.out.println("下载失败");
                    downloadInfo.setText(downloadInfo.getText()+"\n下载失败");
                    e.printStackTrace();
                    downloadRetry.setVisible(true);
                }

                @Override
                public void cancelled() {
//                    System.out.println("取消下载");
                    downloadInfo.setText(downloadInfo.getText()+"\n已取消下载");

                }

                @Override
                public void updateProgress(long done, long total) {
                    System.out.println(done);
                    System.out.println(total);
                }

                @Override
                public void retry(Throwable e, int current, int max) {
                    System.out.println("重试");
                }

                @Override
                public <R> DownloadCallback<R> taskStart(DownloadTask<R> task) {
                    System.out.println("download:"+task.getURI());
                    return super.taskStart(task);
                }
            });
        }
    }
    public void selectDir(){
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("选择Minecraft目录");
        file = directoryChooser.showDialog(new Stage());
        if(file != null){
            downloadInfo.setText(downloadInfo.getText()+"\n选择了下载目录目录："+file.getPath());
            downloadDir.setText(file.getPath());
        }
    }
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
