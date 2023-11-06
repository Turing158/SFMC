package Controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

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
    public Node playerSetting(){
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("../fxml/player.fxml"));
            return pane;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public Node downloadFrame(){
        try{
            AnchorPane pane = FXMLLoader.load(getClass().getResource("../fxml/download.fxml"));
            return pane;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public Node versionFrame(){
        try{
            AnchorPane pane = FXMLLoader.load(getClass().getResource("..fxml/version.fxml"));
            return pane;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
