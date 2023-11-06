
import Controller.Frame;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setScene(new Frame().StartFrame());
        stage.getIcons().add(new Image("img/ico.png"));
        stage.setHeight(500);
        stage.setWidth(800);
        stage.setResizable(false);
        stage.setFullScreen(false);
        stage.show();
    }
}
