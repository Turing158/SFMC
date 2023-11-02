import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class Frame {
    public Scene StartFrame() {
        try{
            AnchorPane pane = FXMLLoader.load(getClass().getResource("./fxml/startFrame.fxml"));
            return new Scene(pane);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
