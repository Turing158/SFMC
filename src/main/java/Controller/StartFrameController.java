package Controller;

import Launch.LaunchMC;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;


public class StartFrameController {
    @FXML
    Button startBtn;
    @FXML
    TextField username;
    @FXML
    Button selectDirBtn;
    @FXML
    TextField dirPath;

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
}
