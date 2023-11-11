package Controller;

import Launch.LaunchMC;
import com.sun.management.OperatingSystemMXBean;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.StringConverter;
import util.EffectAnimation;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.util.Map;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

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
    @FXML
    public TextField windowSizeW;
    @FXML
    public TextField windowSizeH;
    @FXML
    public ComboBox<String> jreVersion;
    @FXML
    public Button selectJreDir;
    OperatingSystemMXBean os = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
    long maxMemory = os.getTotalPhysicalMemorySize()/(1024*1024);
    long freeMemory = os.getFreePhysicalMemorySize()/(1024*1024);

    @FXML
    public void initialize(){
        windowSizeW.setText(String.valueOf(LaunchMC.windowSizeWidth));
        windowSizeH.setText(String.valueOf(LaunchMC.windowSizeHeight));
        windowSizeW.setTextFormatter(new TextFormatter<>(new IntegerFilter()));
        windowSizeH.setTextFormatter(new TextFormatter<>(new IntegerFilter()));
        dir.setText(LaunchMC.directory);
        memorySlider.setMax(maxMemory);
        memorySlider.setValue(LaunchMC.memory);
        memorySlider.setValueChanging(true);
        memoryLabel.setText("已分配内存:"+(int) memorySlider.getValue()+"MB/空闲内存:"+freeMemory+"MB");
        maxPhyMemory.setText(maxMemory+"MB");
        memorySlider();
        jreVersion.getItems().clear();
        Map<String,String> jreVersions = LaunchMC.jreVersions;
        jreVersions.forEach((k,v) -> {
            jreVersion.getItems().add(v);
            System.out.println(k+" "+v);
        });
        jreVersion.setConverter(new StringConverter<String>() {
            @Override
            public String toString(String object) {
                for(String key : jreVersions.keySet()){
                    if(jreVersions.get(key).equals(object)){
                        return key;
                    }
                }
                return null;
            }
            @Override
            public String fromString(String string) {
                return LaunchMC.jreVersions.get(string);
            }
        });
        jreVersion.setOnAction(event -> {
            LaunchMC.jreDir = new File(jreVersion.getValue()+"\\bin\\javaw.exe");
        });
    }
    public void addJreVersion(){
        FileChooser chooser = new FileChooser();
        chooser.setTitle("选择.minecraft目录");
        String currentDirectory = System.getProperty("user.dir");
        chooser.setInitialDirectory(new File(currentDirectory));
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("javaw.exe","javaw.exe"));
        Stage stage = (Stage) selectDir.getScene().getWindow();
        File jreBinJavaWDir = chooser.showOpenDialog(stage);
        if(jreBinJavaWDir != null){
            File jreBinDir = jreBinJavaWDir.getParentFile();
            File jreDir = jreBinDir.getParentFile();
            Map<String,String> jreVersions = LaunchMC.jreVersions;
            jreVersions.put(jreDir.getName(),jreDir.getAbsolutePath());
            LaunchMC.jreVersions = jreVersions;
            initialize();
        }

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
        if(!windowSizeW.getText().isEmpty()){
            LaunchMC.windowSizeWidth = Integer.parseInt(windowSizeW.getText());
        }
        if(!windowSizeH.getText().isEmpty()){
            LaunchMC.windowSizeHeight = Integer.parseInt(windowSizeH.getText());
        }
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
    private static class IntegerFilter implements UnaryOperator<TextFormatter.Change> {
        private static final Pattern DIGIT_PATTERN = Pattern.compile("\\d*");

        @Override
        public TextFormatter.Change apply(TextFormatter.Change change) {
            if (DIGIT_PATTERN.matcher(change.getControlNewText()).matches()) {
                return change;
            }
            return null;
        }
    }
}

