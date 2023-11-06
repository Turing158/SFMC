package Controller;

import Launch.LaunchMC;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

public class gameSettingController {
    @FXML
    public Button selectDir;
    @FXML
    public TextField dir;
    @FXML
    public Button exit;

    @FXML
    public void initialize(){
        dir.setText(LaunchMC.directory);
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

    public void close(){
        exit.getParent().getParent().getParent().setVisible(false);
        Stage stage = (Stage) exit.getParent().getParent().getParent().getParent().getScene().getWindow();
        stage.setScene(new Frame().StartFrame());
        StartFrameController.gameFlag = false;
    }
}
