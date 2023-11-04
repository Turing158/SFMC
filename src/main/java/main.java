
import Controller.Frame;
import javafx.application.Application;
import javafx.stage.Stage;


public class main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setScene(new Frame().StartFrame());
        stage.setHeight(500);
        stage.setWidth(800);
        stage.show();
    }
}
