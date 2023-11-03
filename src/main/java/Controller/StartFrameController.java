package Controller;

import Launch.LaunchMC;
import com.sun.management.OperatingSystemMXBean;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import util.DownloadMinecraft;

import java.io.File;
import java.lang.management.ManagementFactory;


public class StartFrameController {
    OperatingSystemMXBean os = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
    long maxMemory = os.getTotalPhysicalMemorySize()/(1024*1024);
    long freeMemory = os.getFreePhysicalMemorySize()/(1024*1024);
    @FXML
    Button startBtn;
    @FXML
    TextField username;
    @FXML
    Button selectDirBtn;
    @FXML
    Slider memorySlider;
    @FXML
    Text memorySliderData;
    @FXML
    TextField dirPath;
    @FXML
    Button download;
    @FXML
    public static Text downloadTips;

    @FXML
    public void initialize(){
        memorySlider.setMax(maxMemory);
        memorySlider.setValue(1024);
        memorySlider.setValueChanging(true);
        memorySliderData.setText("最大启动内存："+(int) memorySlider.getValue()+"MB");
        memorySlider();
    }


    public void startBtn(){
        if(LaunchMC.directory.isEmpty()){
            System.out.println("请选择Minecraft目录");
        }
        else if(username.getText().isEmpty()){
            System.out.println("请输入用户名");
        }
        else {
            LaunchMC.username = username.getText();
            LaunchMC launchMC = new LaunchMC();
            launchMC.start();
        }
    }
    public void selectDirBtn(){
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("选择.minecraft目录");
        String currentDirectory = System.getProperty("user.dir");
        chooser.setInitialDirectory(new File(currentDirectory));
        Stage stage = (Stage) selectDirBtn.getScene().getWindow();
        File file = chooser.showDialog(stage);
        if(file.getName().equals(".minecraft")){
            dirPath.setText(file.getAbsolutePath());
            LaunchMC.directory = file.getAbsolutePath();
        }
        else {
            System.out.println("你选择的目录不是.minecraft目录，请选择.minecraft目录");
        }
    }
    public void memorySlider(){
        memorySlider.valueProperty().addListener((observable,oldV,newV)->{
            int valueInt = newV.intValue();
            memorySliderData.setText(String.valueOf(valueInt));
            if(valueInt < 1024){
                memorySlider.setValue(1024);
            }
            else if(valueInt > freeMemory){
                memorySlider.setValue(freeMemory);
            }
            memorySliderData.setText("最大启动内存："+(int) memorySlider.getValue()+"MB");
            LaunchMC.memory = valueInt;
        });
    }
    public void downloadMC(){
        DownloadMinecraft dmc = new DownloadMinecraft();
        dmc.download("D:/666","1.9");
    }
}
