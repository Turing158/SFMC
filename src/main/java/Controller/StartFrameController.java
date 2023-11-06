package Controller;

import Launch.LaunchMC;
import com.sun.management.OperatingSystemMXBean;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.lang.management.ManagementFactory;

public class StartFrameController {



    @FXML
    AnchorPane sonFrame;
    @FXML
    AnchorPane sonFrameSource;
    String rootPath = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
    static boolean downloadFlag = false;
    static boolean playerFlag = false;
    double offX = 0;
    double offY = 0;
    OperatingSystemMXBean os = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
    long maxMemory = os.getTotalPhysicalMemorySize()/(1024*1024);
    long freeMemory = os.getFreePhysicalMemorySize()/(1024*1024);
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
        File MinecraftDir = new File(rootPath+".minecraft");
        LaunchMC.directory = MinecraftDir.getAbsolutePath();
        getVersion();
//        memorySlider.setMax(maxMemory);
//        memorySlider.setValue(1024);
//        memorySlider.setValueChanging(true);
//        memorySliderData.setText("最大启动内存："+(int) memorySlider.getValue()+"MB");
//        memorySlider();

    }

    public void playerSetting(){
        sonFrame.setVisible(true);
        sonFrameSource.getChildren().setAll(new Frame().playerSetting());
//        if(!downloadFlag && !playerFlag){
//            newStageSon(new Frame().playerSetting(),600,400);
//            playerFlag = true;
//        }
    }
    public void versionSetting(){

    }
    public void startBtn(){
        if(LaunchMC.version.isEmpty()){
            System.out.println("请输入用户名");
        }
        else {
            LaunchMC launchMC = new LaunchMC();
            launchMC.start();
        }
    }
//    public void selectDirBtn(){
//        DirectoryChooser chooser = new DirectoryChooser();
//        chooser.setTitle("选择.minecraft目录");
//        String currentDirectory = System.getProperty("user.dir");
//        chooser.setInitialDirectory(new File(currentDirectory));
//        Stage stage = (Stage) selectDirBtn.getScene().getWindow();
//        File file = chooser.showDialog(stage);
//        if (file != null){
//            if(file.getName().equals(".minecraft")){
//                LaunchMC.directory = file.getAbsolutePath();
//                getVersion(file);
//            }
//            else {
//                System.out.println("你选择的目录不是.minecraft目录，请选择.minecraft目录");
//            }
//        }
//    }
//    public void memorySlider(){
//        memorySlider.valueProperty().addListener((observable,oldV,newV)->{
//            int valueInt = newV.intValue();
//            memorySliderData.setText(String.valueOf(valueInt));
//            if(valueInt < 1024){
//                memorySlider.setValue(1024);
//            }
//            else if(valueInt > freeMemory){
//                memorySlider.setValue(freeMemory);
//            }
//            memorySliderData.setText("最大启动内存："+(int) memorySlider.getValue()+"MB");
//            LaunchMC.memory = valueInt;
//        });
//    }
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
}
