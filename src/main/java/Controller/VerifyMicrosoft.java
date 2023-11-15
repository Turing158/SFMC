package Controller;

import Launch.LaunchMC;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import jmccc.microsoft.MicrosoftAuthenticator;
import org.to2mbn.jmccc.auth.AuthInfo;
import util.EffectAnimation;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class VerifyMicrosoft {
    @FXML
    public Label message1;
    @FXML
    public Label message2;
    String verificationUri;
    String userCode;
    @FXML
    public Label url;
    @FXML
    public Label code;
    @FXML
    public Label waitTips;

    @FXML
    public void initialize(){
        EffectAnimation effect = new EffectAnimation();
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                MicrosoftAuthenticator microsoftAuthenticator = MicrosoftAuthenticator.login(microsoftVerification -> {
                    verificationUri = microsoftVerification.verificationUri;
                    userCode = microsoftVerification.userCode;
                    openBrowser(microsoftVerification.verificationUri);
                    copyCode();
                    Platform.runLater(() -> {
                        url.setText(verificationUri);
                        code.setText(userCode);
                        waitTips.setVisible(false);
                        effect.fadeEmergeVanish(0.5,true,message1,message2,url,code);
                    });
                });
                LaunchMC.authInfo = microsoftAuthenticator.auth();
                LaunchMC.microsoftAuthenticator = microsoftAuthenticator;
                LaunchMC.authenticator = microsoftAuthenticator;
                Platform.runLater(() -> {
                    AnchorPane parent = (AnchorPane) waitTips.getParent().getParent();
                    parent.setVisible(false);
                    effect.fadeEmergeVanish(0.5,true,parent);
                    parent.getChildren().setAll(new Frame().player());
                });
                return null;
            }
        };
        new Thread(task).start();
    }
    public void openBrowser(String url){
        Desktop desktop = Desktop.getDesktop();
        if (desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                URI uri = new URI(url);
                desktop.browse(uri);
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        }
        else{
            System.out.println("不支持打开浏览器");
        }
    }

    public void copyUrl(){
        Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
        cb.setContents(new StringSelection(verificationUri), null);
    }
    public void copyCode(){
        Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
        cb.setContents(new StringSelection(userCode), null);
    }
}
