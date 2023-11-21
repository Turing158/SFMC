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
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.to2mbn.jmccc.mcdownloader.MinecraftDownloader;
import org.to2mbn.jmccc.mcdownloader.MinecraftDownloaderBuilder;
import org.to2mbn.jmccc.mcdownloader.RemoteVersionList;
import org.to2mbn.jmccc.mcdownloader.download.concurrent.CallbackAdapter;
import org.to2mbn.jmccc.mcdownloader.download.concurrent.DownloadCallback;
import org.to2mbn.jmccc.mcdownloader.download.tasks.DownloadTask;
import org.to2mbn.jmccc.mcdownloader.provider.fabric.FabricVersionList;
import org.to2mbn.jmccc.mcdownloader.provider.forge.ForgeVersion;
import org.to2mbn.jmccc.mcdownloader.provider.forge.ForgeVersionList;
import org.to2mbn.jmccc.version.Version;
import util.DownloadMinecraft;
import util.EffectAnimation;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DownloadController {
    String downloadModel = "Minecraft";
    @FXML
    public AnchorPane forgeDownload;
    @FXML
    public AnchorPane fabricDownload;
    @FXML
    public ComboBox<String> versionChoiceInForge;
    @FXML
    public ComboBox<String> forgeChoice;
    @FXML
    public ComboBox<String> versionChoiceInFabric;
    @FXML
    public ComboBox<String> fabricChoice;
    public ComboBox<String> downloadModelChoice;

    //    初始化下载类
    DownloadMinecraft downloader = null;
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
    ComboBox<String> versionDownloadChoice;
    @FXML
    Button exit;
    @FXML
    Button selectDirBtn;
    @FXML
    public void initialize(){
        initPage();
        initDownloadVersion();
        initDownloadInfo();
    }

    private void initPage() {
        downloadModelChoice.setValue("Minecraft");
        downloadModelChoice.getItems().addAll("Minecraft","Forge","Fabric");
        downloadModelChoice.setOnAction(e -> {
            downloadModel =  downloadModelChoice.getValue();
            getLocalVersions();
            switch (downloadModelChoice.getValue()){
                case "Minecraft":
                    versionDownloadChoice.setVisible(true);
                    forgeDownload.setVisible(false);
                    fabricDownload.setVisible(false);
                    break;
                case "Forge":
                    versionDownloadChoice.setVisible(false);
                    forgeDownload.setVisible(true);
                    fabricDownload.setVisible(false);
                    initForge();
                    break;
                case "Fabric":
                    versionDownloadChoice.setVisible(false);
                    forgeDownload.setVisible(false);
                    fabricDownload.setVisible(true);
                    initFabric();
                    break;
            }
        });
        versionChoiceInForge.setOnAction(e -> {
            if(!versionChoiceInForge.getItems().isEmpty()){
                initForge();
            }
        });
        versionChoiceInFabric.setOnAction(e -> {
            if(!versionChoiceInFabric.getItems().isEmpty()){
                initFabric();
            }
        });
        forgeChoice.setOnAction(e -> {
            downloadInfo.setText("Forge版本：" + forgeChoice.getValue());
        });
        fabricChoice.setOnAction(e -> {
            downloadInfo.setText("Fabric版本：" + fabricChoice.getValue());
        });
    }

    //  开始下载按钮事件
    public void downloadStart(){
        if(downloadModel.equals("Forge")){
            ForgeDownload();
        }
        else if(downloadModel.equals("Fabric")){
            FabricDownload();
        }
        else {
            MinecraftDownload();
        }


    }
//    取消下载按钮事件
    public void cancelDownload(){
        downloader.cancel();
    }
    public void initDownloadVersion(){
        versionDownloadChoice.setDisable(true);
        versionDownloadChoice.setValue("正在获取版本列表...");
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() {
                downloader = new DownloadMinecraft();
                MinecraftDownloader minecraftDownloader = MinecraftDownloaderBuilder.create().build();
                minecraftDownloader.fetchRemoteVersionList(new CallbackAdapter<RemoteVersionList>() {
                    @Override
                    public void done(RemoteVersionList result) {
                        Platform.runLater(() ->{
                            versionDownloadChoice.getItems().clear();
                            versionDownloadChoice.setValue(result.getLatestSnapshot());
                            versionDownloadChoice.getItems().add(result.getLatestSnapshot());
                            result.getVersions().forEach((k,v) -> {
                                if(k.matches("\\d+\\.\\d+(\\.\\d+)?")){
                                    versionDownloadChoice.getItems().add(k);
                                }
                            });
                            versionDownloadChoice.setDisable(false);
                        });
                    }
                });

                return null;
            }
        };
        new Thread(task).start();
    }


//    获取所选版本的forge版本
    public void initForge(){
        download.setVisible(false);
//        获取forge版本列表                                         |这个地方比较特殊，由于initForge()这个方法会运行多次，为了不报空，先加上 downloader != null
        if(!versionChoiceInForge.getValue().equals("未发现本地版本") && downloader != null){
            downloader.downloadForgeVersion(new CallbackAdapter<ForgeVersionList>() {
                @Override
                public void done(ForgeVersionList result) {
                    List<ForgeVersion> list = result.getVersions(versionChoiceInForge.getValue());
                    if(list != null && !list.isEmpty()){
                        Platform.runLater(() -> {
                            forgeChoice.getItems().clear();
                            forgeChoice.setValue(list.get(list.size()-1).getVersionName());
                            for (int i = list.size()-1; i > 0; i--) {
                                forgeChoice.getItems().add(list.get(i).getVersionName());
                            }
                            forgeChoice.setDisable(false);
                            download.setVisible(true);
                        });
                    }
                    else{
                        Platform.runLater(() -> {
                            forgeChoice.setDisable(true);
                            forgeChoice.setValue("未发现此版本Forge");
                        });
                    }

                }
            });
        }
    }
    public void initFabric(){
        download.setVisible(false);
        fabricChoice.setDisable(true);
        if(!versionChoiceInFabric.getValue().equals("未发现本地版本")){
            downloader.downloadFabricVersion(new CallbackAdapter<FabricVersionList>() {
                @Override
                public void done(FabricVersionList result) {
                    if (result != null){
                        Platform.runLater(() -> {
                            fabricChoice.getItems().clear();
                            fabricChoice.setValue(result.getLatest(versionChoiceInFabric.getValue()).getVersionName());
                            fabricChoice.getItems().add(result.getLatest(versionChoiceInFabric.getValue()).getVersionName());
                            fabricChoice.setDisable(false);
                            download.setVisible(true);
                        });
                    }
                    else {
                        Platform.runLater(() -> {
                            fabricChoice.setDisable(true);
                            fabricChoice.setValue("未发现此版本Fabric");
                        });
                    }

                }
            });
        }
    }
//    初始化下载信息
    public void initDownloadInfo(){
        download.setVisible(false);
        downloadCancel.setVisible(false);
        downloadRetry.setVisible(false);
        if(file != null){
            downloadDir.setText(file.getPath());
        }
        versionDownloadChoice.setOnAction(event -> {
            download.setVisible(true);
            downloadInfo.setText(downloadInfo.getText()+"\n选择了"+versionDownloadChoice.getValue()+"版本");
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
        downloader.shutdown();
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



    public void MinecraftDownload(){
        //        检查下载版本是否为空
        if(versionDownloadChoice.getValue()==null){
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
            versionDownloadChoice.setDisable(true);
            downloadModelChoice.setDisable(true);
            downloadDir.setDisable(true);
//            初始化下载类

//            开始下载[参数：下载目录，下载版本，下载消息回调]
            downloader.download(file.getPath(),versionDownloadChoice.getValue(),new CallbackAdapter<Version>() {
                @Override
//                下载完成
                public void done(Version result) {
                    System.out.println("下载完成：Minecraft v"+result.getVersion());
                    downloadInfo.setText(downloadInfo.getText()+"\n下载完成：Minecraft "+result.getVersion());
                    LaunchMC.directory = new File(file.getPath()+"/.minecraft").getAbsolutePath();
                    download.setVisible(false);
                    downloadCancel.setVisible(false);
                    downloadRetry.setVisible(false);

                    downloadModelChoice.setDisable(false);
                    selectDirBtn.setDisable(false);
                    versionDownloadChoice.setDisable(false);
                    downloadDir.setDisable(false);
                }

                @Override
//                下载失败
                public void failed(Throwable e) {
                    downloadInfo.setText(downloadInfo.getText()+"\n下载失败");
                    downloadRetry.setVisible(true);

                    downloadModelChoice.setDisable(false);
                    selectDirBtn.setDisable(false);
                    versionDownloadChoice.setDisable(false);
                    downloadDir.setDisable(false);
                }

                @Override
//                取消下载
                public void cancelled() {
//                    System.out.println("取消下载");
                    downloadInfo.setText(downloadInfo.getText()+"\n已取消下载");
                    downloadModelChoice.setDisable(false);
                    selectDirBtn.setDisable(false);
                    versionDownloadChoice.setDisable(false);
                    downloadDir.setDisable(false);
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
//    Forge的下载方法，与原版下载方法差不多
    public void ForgeDownload(){
        //        检查下载版本是否为空
        if(forgeChoice.getValue()==null || forgeChoice.getValue().equals("未发现本地版本")){
            downloadInfo.setText(downloadInfo.getText()+"\n请选择版本");
        }
//        检查下载目录是否为空
        else if(file==null){
            downloadInfo.setText(downloadInfo.getText()+"\n请选择下载目录");
        }
        else {
            downloadInfo.setText(downloadInfo.getText()+"\n正在下载，请稍后...");
//            隐藏下载按钮
            download.setVisible(false);
            downloadCancel.setVisible(true);
//            禁用选择按钮
            downloadModelChoice.setDisable(true);
            versionChoiceInForge.setDisable(true);
            forgeChoice.setDisable(true);
            selectDirBtn.setDisable(true);
            downloadDir.setDisable(true);
            downloader.download(file.getPath(), forgeChoice.getValue(), new CallbackAdapter<Version>() {
                @Override
                public void done(Version result) {
                    downloadInfo.setText(downloadInfo.getText()+"\n下载完成：Forge "+result.getVersion());
                    download.setVisible(false);
                    downloadCancel.setVisible(false);
                    downloadRetry.setVisible(false);
//                      启用这些禁用的Node
                    downloadModelChoice.setDisable(false);
                    selectDirBtn.setDisable(false);
                    versionChoiceInForge.setDisable(false);
                    forgeChoice.setDisable(false);
                    downloadDir.setDisable(false);
                }

                @Override
                public void failed(Throwable e) {
                    downloadInfo.setText(downloadInfo.getText()+"\n下载失败");
                    downloadRetry.setVisible(true);
                    e.printStackTrace();
                    downloadModelChoice.setDisable(false);
                    selectDirBtn.setDisable(false);
                    versionChoiceInForge.setDisable(false);
                    forgeChoice.setDisable(false);
                    downloadDir.setDisable(false);
                }

                @Override
                public void cancelled() {
                    downloadInfo.setText(downloadInfo.getText()+"\n已取消下载");
                    downloadModelChoice.setDisable(false);
                    selectDirBtn.setDisable(false);
                    versionChoiceInForge.setDisable(false);
                    forgeChoice.setDisable(false);
                    downloadDir.setDisable(false);
                }

                @Override
                public void retry(Throwable e, int current, int max) {
                    super.retry(e, current, max);
                }

                @Override
                public <R> DownloadCallback<R> taskStart(DownloadTask<R> task) {
                    System.out.println(task.getURI());
                    if(task.isCacheable()){
                        downloadInfo.appendText("\n开始下载："+task.getURI()+"\n...");
                    }
                    return super.taskStart(task);
                }
            });
        }
    }
    public void FabricDownload(){
        //        检查下载版本是否为空
        if(fabricChoice.getValue()==null || fabricChoice.getValue().equals("未发现本地版本")){
            downloadInfo.setText(downloadInfo.getText()+"\n请选择版本");
        }
//        检查下载目录是否为空
        else if(file==null){
            downloadInfo.setText(downloadInfo.getText()+"\n请选择下载目录");
        }
        else {
            downloadInfo.setText(downloadInfo.getText()+"\n正在下载，请稍后...");
//            隐藏下载按钮
            download.setVisible(false);
            downloadCancel.setVisible(true);
//            禁用选择按钮
            downloadModelChoice.setDisable(true);
            versionChoiceInFabric.setDisable(true);
            fabricChoice.setDisable(true);
            selectDirBtn.setDisable(true);
            downloadDir.setDisable(true);
            downloader.download(file.getPath(), fabricChoice.getValue(), new CallbackAdapter<Version>() {
                @Override
                public void done(Version result) {
                    downloadInfo.setText(downloadInfo.getText()+"\n下载完成：Forge "+result.getVersion());
                    download.setVisible(false);
                    downloadCancel.setVisible(false);
                    downloadRetry.setVisible(false);
//                      启用这些禁用的Node
                    downloadModelChoice.setDisable(false);
                    selectDirBtn.setDisable(false);
                    versionChoiceInFabric.setDisable(false);
                    fabricChoice.setDisable(false);
                    downloadDir.setDisable(false);
                }

                @Override
                public void failed(Throwable e) {
                    downloadInfo.setText(downloadInfo.getText()+"\n下载失败");
                    downloadRetry.setVisible(true);
                    e.printStackTrace();
                    downloadModelChoice.setDisable(false);
                    selectDirBtn.setDisable(false);
                    versionChoiceInFabric.setDisable(false);
                    fabricChoice.setDisable(false);
                    downloadDir.setDisable(false);
                }

                @Override
                public void cancelled() {
                    downloadInfo.setText(downloadInfo.getText()+"\n已取消下载");
                    downloadModelChoice.setDisable(false);
                    selectDirBtn.setDisable(false);
                    versionChoiceInFabric.setDisable(false);
                    fabricChoice.setDisable(false);
                    downloadDir.setDisable(false);
                }

                @Override
                public void retry(Throwable e, int current, int max) {
                    super.retry(e, current, max);
                }

                @Override
                public <R> DownloadCallback<R> taskStart(DownloadTask<R> task) {
                    System.out.println(task.getURI());
                    if(task.isCacheable()){
                        downloadInfo.appendText("\n开始下载："+task.getURI()+"\n...");
                    }
                    return super.taskStart(task);
                }
            });
        }
    }
//    已解决，终究是自己的错误
//    tmd，MinecraftDownload()里下载完会将minecraft的游戏路径给改成下载路径，但是我  这个地方      写成了"./minecraft"    导致路径错误，无法获取版本
//                              LaunchMC.directory = new File(file.getPath()+"/.minecraft").getAbsolutePath();
    public void getLocalVersions(){
        versionChoiceInForge.getItems().clear();
        versionChoiceInFabric.getItems().clear();
        versionChoiceInForge.setValue("未发现本地版本");
        versionChoiceInFabric.setValue("未发现本地版本");
        versionChoiceInForge.setDisable(true);
        versionChoiceInFabric.setDisable(true);
        File versionFilePath = new File(LaunchMC.directory+"/versions");
        File[] versionFiles = versionFilePath.listFiles(File::isDirectory);
        if (versionFiles != null && versionFiles.length >0) {
            String firstVersion = versionFiles[0].getName();
            for (int i = 0; i < versionFiles.length; i++) {
                versionChoiceInForge.getItems().add(versionFiles[i].getName());
                versionChoiceInFabric.getItems().add(versionFiles[i].getName());
            }
            versionChoiceInForge.setValue(firstVersion);
            versionChoiceInFabric.setValue(firstVersion);
            versionChoiceInForge.setDisable(false);
            versionChoiceInFabric.setDisable(false);
        }
    }
}
