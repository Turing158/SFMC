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
    @FXML
    public CheckBox versionIsolate;
    @FXML
    public CheckBox autoMemory;
    @FXML
    public TextField memoryInput;
    //    获取内存信息
    OperatingSystemMXBean os = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
    long maxMemory = os.getTotalPhysicalMemorySize()/(1024*1024);
    long freeMemory = os.getFreePhysicalMemorySize()/(1024*1024);

    @FXML
    public void initialize(){
        dir.setText(LaunchMC.directory);
        initWindowSize();
        initMemorySlider();
        updateJreVersion();
        initVersionIsolate();
    }

//    初始化jre版本信息
    public void updateJreVersion(){
        jreVersion.getItems().clear();
        jreVersion.getItems().add("自动选择");
        jreVersion.setValue("自动选择");
//        获取jre版本信息[json缓存]
        Map<String,String> jreVersions = LaunchMC.jreVersions;
        jreVersions.forEach((k,v) -> {
            jreVersion.getItems().add(k);
        });
        jreVersion.setConverter(new StringConverter<String>() {
//            下拉框显示的信息
            @Override
            public String toString(String object) {
                if(object.equals("自动选择")){
                    return "自动选择";
                }
                return jreVersions.get(object)+"\t\t"+object;
            }
//            下拉框的值
            @Override
            public String fromString(String string) {
                if(string.equals("自动选择")){
                    return "自动选择";
                }
                return LaunchMC.jreVersions.get(string);
            }

        });
        if(LaunchMC.jreDir != null){
            jreVersion.setValue(new File(LaunchMC.jreDir.getParent()).getParent());
        }
        jreVersion.setOnAction(event -> {
//            判断下是否自动选择jre版本
//            不加最外层判断会报空指针，因为要刷新页面，所以得判断
            if(jreVersion != null && jreVersion.getValue() != null){
                if(jreVersion.getValue().equals("自动选择")){
                    LaunchMC.jreDir = null;
                }
                else {
                    LaunchMC.jreDir = new File(jreVersion.getValue() + "\\bin\\javaw.exe");
                }
            }
        });
    }
//    添加jre目录
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
            jreVersions.put(jreDir.getAbsolutePath(),jreDir.getName());
            System.out.println(jreVersions);
            LaunchMC.jreVersions = jreVersions;
//            刷新此界面
            initialize();
        }
    }

//    选择游戏目录
    public void selectDir(){
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("选择.minecraft目录");
        String currentDirectory = System.getProperty("user.dir");
        chooser.setInitialDirectory(new File(currentDirectory));
        Stage stage = (Stage) selectDir.getScene().getWindow();
        File file = chooser.showDialog(stage);
//        判断是否选择了目录
        if (file != null){
//            判断是否是.minecraft目录
            if(file.getName().equals(".minecraft")){
//                设置游戏目录
                StartFrameController.settingGamePath = file.getAbsolutePath();
                LaunchMC.selfDir = file;
            }
            else {
//                提示[未做]
                System.out.println("你选择的目录不是.minecraft目录，请选择.minecraft目录");
            }
        }
    }
//    初始化窗口信息
    public void initWindowSize(){
        windowSizeW.setText(String.valueOf(LaunchMC.windowSizeWidth));
        windowSizeH.setText(String.valueOf(LaunchMC.windowSizeHeight));
//        设置只能输入数字
        windowSizeW.setTextFormatter(new TextFormatter<>(new IntegerFilter()));
        windowSizeH.setTextFormatter(new TextFormatter<>(new IntegerFilter()));
    }
//    初始化设置内存信息
    public void initMemorySlider(){
        memoryInput.setTextFormatter(new TextFormatter<>(new IntegerFilter()));
        autoMemory.setSelected(LaunchMC.autoMemory);
        autoMemory.setOnAction(e -> {
            LaunchMC.autoMemory = autoMemory.isSelected();
            initMemorySlider();
        });
        if(autoMemory.isSelected()){
            memorySlider.setDisable(true);
            memoryInput.setDisable(true);
            if(freeMemory/3 > 1024){
                LaunchMC.memory = (int) (freeMemory/3);
            }
            else if(freeMemory < 1024){
                LaunchMC.memory = (int) freeMemory;
            }
            else {
                LaunchMC.memory = 1024;
            }
        }
        else{
            memorySlider.setDisable(false);
            memoryInput.setDisable(false);
        }
        memorySlider.setMax(maxMemory);
        memorySlider.setValue(LaunchMC.memory);
        memoryInput.setText(String.valueOf(LaunchMC.memory));
        memorySlider.setValueChanging(true);
        memoryLabel.setText("已分配内存:"+(int) memorySlider.getValue()+"MB/空闲内存:"+freeMemory+"MB");
        maxPhyMemory.setText(maxMemory+"MB");
        memorySlider.valueProperty().addListener((observable,oldV,newV)->{
            int valueInt = newV.intValue();
            memoryLabel.setText(String.valueOf(valueInt));
            LaunchMC.memory = valueInt;
//            设置不能小于1024MB
            if(valueInt < 1024){
                memorySlider.setValue(1024);
                LaunchMC.memory = 1024;
            }
//            设置不能大于空闲内存
            else if(valueInt > freeMemory){
                memorySlider.setValue(freeMemory);
                LaunchMC.memory = (int) freeMemory;
            }
            memoryInput.setText(String.valueOf(LaunchMC.memory));
            memoryLabel.setText("已分配内存:"+(int) memorySlider.getValue()+"MB/空闲内存:"+freeMemory+"MB");
        });
    }


    public void initVersionIsolate(){
        versionIsolate.setSelected(LaunchMC.versionIsolate);
        versionIsolate.setOnAction(e -> {
            LaunchMC.versionIsolate = versionIsolate.isSelected();
        });
    }
//    关闭设置界面，并保存信息
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
//    强制输入数字类
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

