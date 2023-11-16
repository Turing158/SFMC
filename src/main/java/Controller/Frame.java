package Controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import util.JsonOperate;

public class Frame extends Application {
    public Frame(){}

    @Override
    public void start(Stage stage) {
        JsonOperate json = new JsonOperate();
//        存在则读取
        if(json.exist()){
            json.load();
        }
        stage.setScene(new Frame().StartFrame());
        stage.getIcons().add(new Image("img/ico.png"));
        stage.setHeight(500);
        stage.setWidth(800);
        stage.setTitle("SFMC 1.0");
        stage.setResizable(false);
        stage.setFullScreen(false);
        stage.setOnCloseRequest(event -> {
//            退出时保存
            json.save();
            System.gc();
        });
        stage.show();
    }

    public Scene StartFrame() {
        try{
            FXMLLoader fxml = new FXMLLoader();
            fxml.setLocation(getClass().getClassLoader().getResource("fxml/startFrame.fxml"));
            AnchorPane pane = fxml.load();
            return new Scene(pane);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public Node playerSetting(){
        try {
            FXMLLoader fxml = new FXMLLoader();
            fxml.setLocation(getClass().getClassLoader().getResource("fxml/playerSetting.fxml"));
            AnchorPane pane = fxml.load();
            return pane;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public Node downloadFrame(){
        try{
            FXMLLoader fxml = new FXMLLoader();
            fxml.setLocation(getClass().getClassLoader().getResource("fxml/downloadPage.fxml"));
            AnchorPane pane = fxml.load();
            return pane;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public Node gameSetting(){
        try{
            FXMLLoader fxml = new FXMLLoader();
            fxml.setLocation(getClass().getClassLoader().getResource("fxml/gameSetting.fxml"));
            AnchorPane pane = fxml.load();
            return pane;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public Node verifyMicrosoft(){
        try{
            FXMLLoader fxml = new FXMLLoader();
            fxml.setLocation(getClass().getClassLoader().getResource("fxml/verifyMicrosoft.fxml"));
            AnchorPane pane = fxml.load();
            return pane;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public Node player(){
        try{
            FXMLLoader fxml = new FXMLLoader();
            fxml.setLocation(getClass().getClassLoader().getResource("fxml/player.fxml"));
            AnchorPane pane = fxml.load();
            return pane;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
