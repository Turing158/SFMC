package Controller;

import Launch.LaunchMC;
import com.sun.management.OperatingSystemMXBean;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import util.EffectAnimation;

import java.io.File;
import java.lang.management.ManagementFactory;

public class gameSettingController {

    @FXML
    public Button selectDir;
    @FXML
    public TextField dir;
    @FXML
    public Label memoryLabel;
    @FXML
    public Slider memorySlider;
    @FXML
    public Button exit;
    @FXML
    public Label maxPhyMemory;
    OperatingSystemMXBean os = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
    long maxMemory = os.getTotalPhysicalMemorySize()/(1024*1024);
    long freeMemory = os.getFreePhysicalMemorySize()/(1024*1024);

    @FXML
    public void initialize(){
        dir.setText(LaunchMC.directory);
        memorySlider.setMax(maxMemory);
        memorySlider.setValue(LaunchMC.memory);
        memorySlider.setValueChanging(true);
        memoryLabel.setText("已分配内存:"+(int) memorySlider.getValue()+"MB/空闲内存:"+freeMemory+"MB");
        maxPhyMemory.setText(maxMemory+"MB");
        memorySlider();
    }

    public void selectDir(){
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("选择.minecraft目录");
        String currentDirectory = System.getProperty("user.dir");
        chooser.setInitialDirectory(new File(currentDirectory));
        Stage stage = (Stage) selectDir.getScene().getWindow();
        File file = chooser.showDialog(stage);
        if (file != null){
            if(file.getName().equals(".minecraft")){
                StartFrameController.settingGamePath = file.getAbsolutePath();
            }
            else {
                System.out.println("你选择的目录不是.minecraft目录，请选择.minecraft目录");
            }
        }
    }
    public void memorySlider(){
        memorySlider.valueProperty().addListener((observable,oldV,newV)->{
            int valueInt = newV.intValue();
            memoryLabel.setText(String.valueOf(valueInt));
            LaunchMC.memory = valueInt;
            if(valueInt < 1024){
                memorySlider.setValue(1024);
                LaunchMC.memory = 1024;
            }
            else if(valueInt > freeMemory){
                memorySlider.setValue(freeMemory);
                LaunchMC.memory = (int) freeMemory;
            }
            memoryLabel.setText("已分配内存:"+(int) memorySlider.getValue()+"MB/空闲内存:"+freeMemory+"MB");

        });
    }

    public void close(){
        EffectAnimation effect = new EffectAnimation();
        effect.fadeEmergeVanish(0.2,false,exit.getParent().getParent().getParent());
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO,event -> {
                    effect.switchPage(exit.getParent().getParent(),0.2,25,425,false).play();
                }),
                new KeyFrame(Duration.seconds(0.2),event -> {
                    Stage stage = (Stage) exit.getScene().getWindow();
                    stage.setScene(new Frame().StartFrame());
                })
        );
        timeline.play();
        StartFrameController.gameFlag = false;
    }
}
