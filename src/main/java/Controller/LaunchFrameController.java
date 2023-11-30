package Controller;

import Launch.LaunchMC;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import util.EffectAnimation;

public class LaunchFrameController {
    @FXML
    public Button cancel;





    public void cancel(){
        LaunchMC.destroy();
        EffectAnimation effect = new EffectAnimation();
        effect.fadeEmergeVanish(0.2,false,cancel.getParent().getParent().getParent());
        effect.switchPage(cancel.getParent().getParent(),0.2,25,425,false).play();
    }
}
