package Controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class Frame {
    public Frame(){}
    public Scene StartFrame() {
        try{
            AnchorPane pane = FXMLLoader.load(getClass().getResource("../fxml/startFrame.fxml"));
            return new Scene(pane);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public Scene playerSetting(){
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("../fxml/player.fxml"));
            return new Scene(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public Scene downloadFrame(){
        try{
            AnchorPane pane = FXMLLoader.load(getClass().getResource("../fxml/download.fxml"));
            return new Scene(pane);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
